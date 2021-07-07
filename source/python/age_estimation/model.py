import better_exceptions
from keras.applications import ResNet50, InceptionResNetV2
from keras.layers import Dense
from keras.models import Model
from keras import backend as K


def age_mae(y_true, y_pred):
    true_age = K.sum(y_true * K.arange(0, 101, dtype="float32"), axis=-1)
    pred_age = K.sum(y_pred * K.arange(0, 101, dtype="float32"), axis=-1)
    mae = K.mean(K.abs(true_age - pred_age))
    return mae


def get_model(model_name="ResNet50"):
    base_model = None

    if model_name == "ResNet50":
        base_model = ResNet50(include_top=False, weights='imagenet', input_shape=(224, 224, 3), pooling="avg")
    elif model_name == "InceptionResNetV2":
        base_model = InceptionResNetV2(include_top=False, weights='imagenet', input_shape=(299, 299, 3), pooling="avg")


                                                                                                        .
    prediction = Dense(units=101, kernel_initializer="he_normal", use_bias=False, activation="softmax", # 기존 ResNet50 모델에 층 1개를 추가한것.(은닉 층이 추가된 거같음)
                       name="pred_age")(base_model.output)

     # Dense : Neural Network 를 구성하는 Layer를 생성하는데 필요함                                             
     # units : 현재 dense를 통해서 만들 hidden layer의 Node의 수(Node : 신경망을 구성하는 하나의 단위 => 노드의 집합이 신경망임)
     # kernel_initializer : 커널 가중치 행렬의 이니셜 라이저(가중치 초기화 함수라고 보면 됨)

    #인공신경망을 효율적으로 학습시키기 위한 개선 방법
    #   1.가중치 초기화(Weight initialization)
    #       - #LeCun 초기화
    #       - Xavier 초기화
    #       - He 초기화(he_uniform, he_normal) -> 3개중에서 He초기화가 가장 좋음............... --> 어케 개선하누 이미 제일 좋은거 쓰는데;
    #   2.배치 정규화(Batch Normalization)
    #       장점
    #           - 학습 속도를 빠르게 함.
    #           - 신경망을 가중치 초기화(Weight Initialization)나 하이퍼파라미터 설정에 대해 강건(Robust)하게 만들어줌
    #           - 오버피팅(overfitting) 막아줌(오버피팅 : 과도한 학습 -> 그로 인한 정확도가 오히려 떨어짐)
    #   3.드랍아웃(Dropout)


    # 활성화 함수(activation function)
    #   - 신경망의 output을 결정하는 식(equation)
    #   - 현재 뉴런의 input을 feeding 하여 생성된 output이 다음 레이어로 전해지는 과정 중 역할을 수행하는 수학적인 게이트(gate)
    #   - 각 뉴런의 output을 0과 1 또는 -1과 1사이로 표준화(normalization) 하여 모델이 복잡한 데이터를 학습하는 데 도움을 줌.
    #   - 여기서 사용된 softmax는 다종류 분류에 많이 사용됨.
    #       - 장점 : 확률의 총합이 1 이므로 어떤 분류에 속할 확률이 가장 높을지를 쉽게 인지할 수 있음.
    #                   ex) y1일 확률 0.7, y2일 확률 0.2, y3일 확률  0.1  --> 합쳐서 1인걸 확인 가능!!1 y1일 확률이 가장 크다. -> 예측이 잘된다..
    #       - 단점 : hidden layer에서 softmax를 사용하면 기울기 소실 문제 등 기울기를 제대로 찾지 못해, 학습 효율성이 감소한다는 단점이 있다.(학습의 효율성 : 지식을 습득하는 과정)
    #       보통 Dense층은 출력 층 전의 hidden layer으로 많이 쓰임(영상이 아닌 수치 자료 입력 시 입력층으로도 많이 쓰임)
    model = Model(inputs=base_model.input, outputs=prediction)

    return model


def main():
    model = get_model("InceptionResNetV2")
    model.summary()


if __name__ == '__main__':
    main()
