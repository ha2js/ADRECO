## ML 환경 세팅

<br>

### OS
    Windows 10
    Ubuntu 18.04

<br>

### Dependencies
    Python 3.6.10
    Tensorflow 2.3.0
    CUDA 10.1
    cuDNN 7.6.5
    torch 1.7.0

<br>

### Setting process
    1. vscode
        - https://code.visualstudio.com 설치 파일 다운 (.deb)
        - cd ~/Downloads
        - sudo dpkg -i code*.deb
     
     
    2. CUDA
        - CUDA https://developer.nvidia.com/cuda-toolkit-archive
        - cuDNN https://developer.nvidia.com/rdp/cudnn-archive
        - cuDNN 압축 해제
        - CUDA Toolkit이 설치된 경로에 삽입
        - nvcc --version (설치 확인)
        - nvidia-smi (설치 확인)
    
    
    3. Aanaconda
        - https://www.anaconda.com/products/individual
    
    
    4. 가상환경 생성
        - conda create -n adreco python=3.6.10 (+ python 설치)
        - conda info --envs (가상환경 목록 확인)
        - conda activate adreco
        
        
    5. torch 설치
        - https://pytorch.org/get-started/previous-versions/
        - conda install pytorch==1.7.0 torchvision==0.8.0 torchaudio==0.7.0 cudatoolkit=10.1 -c pytorch
        - pip install torch==1.7.0+cu101 torchvision==0.8.1+cu101 torchaudio==0.7.0 -f https://download.pytorch.org/whl/torch_stable.html
    
    
    6. opencv 설치
        - sudo apt-get install libopencv-*
    
    
    7. package 설치
        - pip install tensorflow==2.3.0
        - pip install face_recognition
        - pip install easydict
        - pip install omegaconf
        - pip install pymysql
        - pip install hydra-core
        - pip install sklearn
        - pip install hydra-core
        - pip install albumentations
        - pip install hydra-core
        - pip isntall wandb
        - pip install tqdm
        - conda install jupyter notebook
        - conda install numpy
        - conda install pillow
        - conda install opencv
        - conda install -c conda-forge dlib
        - conda install cmake
        - conda install -c menpo opencv3
        - conda install pandas


    9. MySQL 설치
        - sudo apt-get install mysql-server
        - sudo mysql_secure_installation
        - sudo mysql -u root -p (확인)
        - sudo apt install mysql-workbench (workbench 설치)
