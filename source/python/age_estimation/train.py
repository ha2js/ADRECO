import argparse
from pathlib import Path
import numpy as np
from keras.callbacks import LearningRateScheduler, ModelCheckpoint
from keras.optimizers import SGD, Adam
from generator import FaceGenerator, ValGenerator
from model import get_model, age_mae


def get_args():
    parser = argparse.ArgumentParser(description="This script trains the CNN model for age estimation.",
                                     formatter_class=argparse.ArgumentDefaultsHelpFormatter)
    parser.add_argument("--appa_dir", type=str, required=True,
                        help="path to the APPA-REAL dataset")
    parser.add_argument("--utk_dir", type=str, default=None,
                        help="path to the UTK face dataset")
    parser.add_argument("--output_dir", type=str, default="checkpoints",
                        help="checkpoint dir")
    parser.add_argument("--batch_size", type=int, default=32,
                        help="batch size")
    parser.add_argument("--nb_epochs", type=int, default=30,
                        help="number of epochs")
    parser.add_argument("--lr", type=float, default=0.1,
                        help="learning rate")
    parser.add_argument("--opt", type=str, default="sgd",
                        help="optimizer name; 'sgd' or 'adam'")
    parser.add_argument("--model_name", type=str, default="ResNet50",
                        help="model name: 'ResNet50' or 'InceptionResNetV2'")
    args = parser.parse_args()
    return args


class Schedule:
    def __init__(self, nb_epochs, initial_lr):
        self.epochs = nb_epochs
        self.initial_lr = initial_lr

    def __call__(self, epoch_idx):
        if epoch_idx < self.epochs * 0.25:
            return self.initial_lr
        elif epoch_idx < self.epochs * 0.50:
            return self.initial_lr * 0.2
        elif epoch_idx < self.epochs * 0.75:
            return self.initial_lr * 0.04
        return self.initial_lr * 0.008


def get_optimizer(opt_name, lr):
    if opt_name == "sgd":
        return SGD(lr=lr, momentum=0.9, nesterov=True)
    elif opt_name == "adam":
        return Adam(lr=lr)
    else:
        raise ValueError("optimizer name should be 'sgd' or 'adam'")


def main():
    args = get_args()
    appa_dir = args.appa_dir # 데이터셋 경로 
    utk_dir = args.utk_dir # UTK 얼굴 데이터셋 경로 
    model_name = args.model_name # 'ResNet50' 또는 'InceptionResNetV2' 반환. default는 ResNet50 
    batch_size = args.batch_size # default size는 32
    nb_epochs = args.nb_epochs # default epoch는 30
    lr = args.lr # 학습률
    opt_name = args.opt # 최적화 default 'sgd' or 'adam'

    
    #epoch : 전체 데이터셋에 대해 한번 학습 완료시킨것
    #메모리저하,속도저하 때문에 1에폭에 모든 데이터를 넣기 힘듬.
    #그래서 데이터를 나눠 주게 됨. 여기서 데이터를 몇번나누는가? -> iteration
    #각 iteration마다 주는 데이터 사이즈 -> batch size
    # ex) 총 데이터가 100개, batch size가 10 이라면 => 1 iteration = 10개 데이터에 대해 학습
    #     따라서 1Epoch = 100/batch size = 1-iteration
    
    
    if model_name == "ResNet50":
        image_size = 224
    elif model_name == "InceptionResNetV2": #keras꺼임. 사전훈련된 컨볼루션 신경망
        image_size = 299

    train_gen = FaceGenerator(appa_dir, utk_dir=utk_dir, batch_size=batch_size, image_size=image_size)
    val_gen = ValGenerator(appa_dir, batch_size=batch_size, image_size=image_size)
    model = get_model(model_name=model_name)
    opt = get_optimizer(opt_name, lr)
    model.compile(optimizer=opt, loss="categorical_crossentropy", metrics=[age_mae])
    model.summary()
    output_dir = Path(__file__).resolve().parent.joinpath(args.output_dir)
    output_dir.mkdir(parents=True, exist_ok=True)
    callbacks = [LearningRateScheduler(schedule=Schedule(nb_epochs, initial_lr=lr)),
                 ModelCheckpoint(str(output_dir) + "/weights.{epoch:03d}-{val_loss:.3f}-{val_age_mae:.3f}.hdf5",
                                 monitor="val_age_mae",
                                 verbose=1,
                                 save_best_only=True,
                                 mode="min")
                 ]

    hist = model.fit_generator(generator=train_gen,
                               epochs=nb_epochs,
                               validation_data=val_gen,
                               verbose=1,
                               callbacks=callbacks)

    np.savez(str(output_dir.joinpath("history.npz")), history=hist.history)


if __name__ == '__main__':
    main()
