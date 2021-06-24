#!/usr/bin/env python
# coding: utf-8

# In[1]:


# %load demo.py
import face_recognition
import os
from pathlib import Path
import cv2
import dlib
import numpy as np
from PIL import Image
import easydict
from tensorflow.keras.utils import get_file
from contextlib import contextmanager
from omegaconf import OmegaConf
from src.factory import get_model
import sys
import time
import torch
import utils_sp as utils
from models import gazenet
from mtcnn import FaceDetector
import pymysql
import tensorflow as tf
import requests
import random

pretrained_model = "https://github.com/yu4u/age-gender-estimation/releases/download/v0.6/EfficientNetB3_224_weights.11-3.44.hdf5"
modhash = '6d7f7b7ced093a8b3ef6399163da6ece'
url1 = "http://172.30.1.53:3001/Ads_img1"
url2 = "http://172.30.1.53:3001/check1"
conn = pymysql.connect(host='14.42.246.57', user='root2', password='1q2w3e4r!', db='swp', charset='utf8')

def draw_label(image, point, label, font=cv2.FONT_HERSHEY_SIMPLEX,
               font_scale=0.8, thickness=1):
    size = cv2.getTextSize(label, font, font_scale, thickness)[0]
    x, y = point
    cv2.rectangle(image, (x, y - size[1]), (x + size[0], y), (255, 0, 0), cv2.FILLED)
    cv2.putText(image, label, point, font, font_scale, (255, 255, 255), thickness, lineType=cv2.LINE_AA)

@contextmanager
def video_capture(*args, **kwargs):
    cap = cv2.VideoCapture(*args, **kwargs)
    try:
        yield cap
    finally:
        cap.release()

def yield_images():
    # capture video
    with video_capture(0) as cap:
        cap.set(cv2.CAP_PROP_FRAME_WIDTH, 640)
        cap.set(cv2.CAP_PROP_FRAME_HEIGHT, 480)
        
        while True:
            # get video frame
            ret, img = cap.read()

            if not ret:
                raise RuntimeError("Failed to capture image")

            yield img
            
def db_insert_clear(ad_num, dic, known_face_encodings, known_face_names):
    curs = conn.cursor()
    sql = "INSERT INTO user_info VALUES(NOW(), %s, %s, %s, %s, %s)"
    for k in dic:
        if dic[k]['seetime'] != 0.0:
            data = ('AD_'+str(ad_num), k, int(dic[k]['age']/10)*10, dic[k]['gender'], dic[k]['seetime'])
            curs.execute(sql, data)
    conn.commit()
    dic.clear()
    known_face_encodings.clear()
    known_face_names.clear()
    files = os.listdir('knowns')
    for filename in files:
        os.remove('knowns/'+filename)

def main():
    # 시선 추적 모델 로드
    args_gaze = easydict.EasyDict({
        "cpu" : None,
        "weights" : 'models/weights/gazenet.pth'
    })
    print('Loading MobileFaceGaze model...')
    device = torch.device("cuda:0" if (torch.cuda.is_available() and not args_gaze.cpu) else "cpu")
    gaze_model = gazenet.GazeNet(device)

    if(not torch.cuda.is_available() and not args_gaze.cpu):
        print('Tried to load GPU but found none. Please check your environment')

    state_dict = torch.load(args_gaze.weights, map_location=device)
    gaze_model.load_state_dict(state_dict)
    print('Model loaded using {} as device'.format(device))
    
    gaze_model.eval()
    
    timecount = 0.0
    margin = 0.4
    known_face_encodings = []
    known_face_names = []
    person_num = 0
    dirname = 'knowns'
    ad_num = 1
    dic = {}
    frame_count = 0

    face_detector = FaceDetector(device=device)
    
    weight_file = get_file("EfficientNetB3_224_weights.11-3.44.hdf5", pretrained_model, cache_subdir="pretrained_models",
                               file_hash=modhash, cache_dir=str(Path("__file__").resolve().parent))

    # for face detection
    detector = dlib.get_frontal_face_detector()

    # load model and weights
    model_name, img_size = Path(weight_file).stem.split("_")[:2]
    img_size = int(img_size)
    cfg = OmegaConf.from_dotlist([f"model.model_name={model_name}", f"model.img_size={img_size}"])
    model = get_model(cfg)
    model.load_weights(weight_file)
    
    image_generator = yield_images()
    
    for img in image_generator:
        timer = time.time()
        input_img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
        img_h, img_w, _ = np.shape(input_img)
        
        # detect faces using dlib detector
        detected = detector(input_img, 1)
        gaze_detected, landmarks = face_detector.detect(Image.fromarray(input_img))
        
        faces = np.empty((len(detected), img_size, img_size, 3))
        count_age_gender = [
                0, 0, 0, 0, 0, 0, #남자 10 ~ 60
                0, 0, 0, 0, 0, 0 #여자 10 ~ 60
            ]
        
        if len(detected) > 0:
            face_names = []
            z_length = []
            for i, (d, lm) in enumerate(zip(detected, landmarks)):
                x1, y1, x2, y2, w, h = d.left(), d.top(), d.right() + 1, d.bottom() + 1, d.width(), d.height()
                xw1 = max(int(x1 - margin * w), 0)
                yw1 = max(int(y1 - margin * h), 0)
                xw2 = min(int(x2 + margin * w), img_w - 1)
                yw2 = min(int(y2 + margin * h), img_h - 1)
                cv2.rectangle(img, (x1, y1), (x2, y2), (255, 0, 0), 2)
                faces[i] = cv2.resize(img[yw1:yw2 + 1, xw1:xw2 + 1], (img_size, img_size))

                #얼굴 인식
                face_encoding = face_recognition.face_encodings(input_img, [(y1, x1, y2, x2)])[0]
                if len(known_face_encodings) == 0:
                    min_value = 1.0
                else:
                    distances = face_recognition.face_distance(known_face_encodings, face_encoding)
                    min_value = min(distances)

                name = "Unknown"
                if min_value < 0.30:
                    index = np.argmin(distances)
                    name = known_face_names[index]
                    
                else: # unkowns 일때 사진 저장
                    name = str(person_num)
                    person_num += 1
                    
                    # 얼굴 검출 저장
                    detected_face_img = Image.fromarray(input_img[yw1:yw2, xw1:xw2])
                    detected_face_img.save('knowns/'+name+'.jpg')
                    known_face_names.append(name)
                    known_face_encodings.append(face_encoding)
                    
                # Crop and normalize face Face
                gaze_face, gaze_origin, M  = utils.normalize_face(lm, input_img)

                # Predict gaze
                with torch.no_grad():
                    gaze = gaze_model.get_gaze(gaze_face)
                    gaze = gaze[0].data.cpu()                              

                # Draw results
                img = cv2.circle(img, gaze_origin, 3, (0, 255, 0), -1)
                img = utils.draw_gaze(img, gaze_origin, gaze, color=(255,0,0), thickness=2)

                z_length.append(utils.zlength(gaze))
                face_names.append(name)
                if not name in dic.keys():
                    dic[name] = {}
                    dic[name]['seetime'] = 0.0
            
            # predict ages and genders of the detected faces
            results = model.predict(faces)
            predicted_genders = results[0]
            ages = np.arange(0, 101).reshape(101, 1)
            predicted_ages = results[1].dot(ages).flatten()
            
            for (i, d), name in zip(enumerate(detected), face_names):
                if not 'age' in dic[name].keys():
                    age = int(predicted_ages[i])
                    gender =  "M" if predicted_genders[i][0] < 0.5 else "F"
                    if 10 <= age and age <= 69:
                        if gender == 'M': count_age_gender[0 + (int(age/10)-1)] += 1
                        else: count_age_gender[6 + (int(age/10)-1)] += 1
                            
                    dic[name]['age'], dic[name]['gender'] = age, gender
                label = "{}, {}, {}".format(name, dic[name]['age'], dic[name]['gender'])
                draw_label(img, (d.left(), d.top()), label)
           if frame_count == 0:
                m = max(count_age_gender)
                m_list = [i for i, j in enumerate(count_age_gender) if j == m]
                choice = random.choice(m_list)
             ad   x, y = choice%6, int(choice/6)
                data2 = { 'check_req': True }
                data1 = {
                    'age': 0 if not count_age_gender else (x+1)*10, 
                    'gender': '0' if not count_age_gender else ("M" if y == 0 else "F")
                }
                requests.post(url2, json=data2)
                requests.post(url1, json=data1)

            addtime =  time.time() - timer
            for i in range(len(z_length)):
                if z_length[i] <= 30:
                    dic[face_names[i]]['seetime'] += addtime
                img = cv2.putText(img, '{} : {:.2f}'.format(face_names[i], dic[face_names[i]]['seetime']), (0, 40+20*i), cv2.FONT_HERSHEY_PLAIN, 1, (0, 255, 255), 1, cv2.LINE_AA)    
        else:
            if frame_count == 0:
                data2 = { 'check_req': True }
                data1 = {
                    'age': 0, 
                    'gender': '0'
                }
                requests.post(url2, json=data2)
                requests.post(url1, json=data1)
            addtime =  time.time() - timer
        timecount += addtime;
        img = cv2.putText(img, 'TIME: {:.2f}'.format(timecount), (0, 20), cv2.FONT_HERSHEY_PLAIN, 1, (0, 255, 255), 1, cv2.LINE_AA) 
        frame_count += 1
        if int(timecount) > 20:
            timecount = 0.0aldk
            person_num = 0
            db_insert_clear(ad_num, dic, known_face_encodings, known_face_names)
            ad_num += 1
            frame_count = 0
            
        cv2.imshow("result", img)
        key = cv2.waitKey(30)
            
        if key == 27:  # ESC
            print("ESC", dic)
            break
            
    #conn.close()
    cv2.destroyAllWindows()
        
if __name__ == '__main__':
    main()
