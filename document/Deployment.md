## 배포 과정 기록

<br>

### Spring Boot 배포 - AWS EC2 
Amazon EC2는 Amazon Web Services(AWS) 클라우드에서 확장 가능 컴퓨팅 용량을 제공합니다. 
Amazon EC2를 사용하면 하드웨어에 선투자할 필요가 없어 더 빠르게 애플리케이션을 개발하고 배포할 수 있습니다. 원하는 만큼 가상 서버를 구축하고 보안 및 네트워크 구성과 스토리지 관리가 가능합니다. 또한 Amazon EC2는 요구 사항이나 갑작스러운 인기 증대 등 변동 사항에 따라 신속하게 규모를 확장하거나 축소할 수 있어 서버 트래픽 예측 필요성이 줄어듭니다.   
<br>

#### 1. 준비
    1) Git
    
    2) PuTTY (Window) 
<br>

#### 2. EC2 인스턴스 시작
    1) AMI(Amazon Machine Image) 선택 (운영체제)
    
    2) 검토 시, 보안 그룹을 제외한 나머지는 기본으로 설정
    
    3) 보안 그룹 편집 (3306 - MySQL, 8080 - Tomcat) 포트 open
    
    4) 키 페어 생성
    
    5) 키 페어 다운로드 (잃어버리지 않게 잘 보관 !!)
    
    6) 인스턴스 생성 완료
<br>

#### 3. 인스턴스 연결
    1) Window OS [참고](https://developro.tistory.com/5?category=376640)
        - PuTTYgen 실행
        - pem 로드
        - 암호키(.ppk) 저장
        - PuTTY 실행
        - Host Name(public IP) 입력
        - Saved Sessions 이름 입력 (편한 이름으로)
        - save click
        - Connection > SSH > Auth 탭으로 이동
        - Private key file for authentication 항목에 ppk 파일 업로드
        - Session 메뉴로 이동
        - save click
        - open click
        - 경고창 accept click   
        
    2) MAC OS
        - 터미널 명령어 이용
        - ssh -i [pem경로] ubuntu@[ec2 IP 주소 또는 도메인] 
<br>

#### 4. Ubuntu 설정
      1) 받아올 수 있는 서버 정보 업데이트
          - sudo apt-get update
          
      2) java 설치
          - sudo yum install java-1.8.0-openjdk.x86_64
          
      3) maven 설치
          - apt-get install maven
          
      4) mysql 설치
          - sudo apt install mysql-server
          
      5) mysql 접속
          - sudo mysql -u root -p   
<br>

#### 5. 깃으로 프로젝트 불러오기
    1) git clone
<br>

#### 6. 서버 실행
    1) maven으로 jar 파일 생성
        - pom.xml 경로로 이동 
        - mvn compile
        - mvn package 
        
    2) target 폴더로 이동
        - cd target/
        
    3) jar 파일 실행
        - nohup java -jar [project_name]-0.0.1-SNAPSHOT.jar &
<br>

### Vue.js 배포 - Nginx
#### 1. npm 설치
    1) sudo apt install npm

#### 2. npm 업그레이드 (버전이 낮을 경우 시도)
    1) curl -sL https://deb.nodesource.com/setup_10.x | sudo -E bash -
    
    2) apt -y install nodejs make gcc g++
    
#### 3. node 설치
    1) sudo apt-get install -y nodejs
    
#### 4. nginx 설치
    1) sudo apt install nginx
    
#### 5. nginx 세팅
    1) 포트 확인
    
    2) server_name 설정 (도메인 주소)
    
    3) root 설정 (/home/ubuntu/.../dist)
    
    4) location 설정 (기본 경로)
    
#### 6. 빌드
    1) sudo npm install
    
    2) sudo npm run build

<br>

### MySQL 설정
#### 1. ec2 인스턴스에서 mysql 실행 
    1) putty(mac 유저일 시 터미널)로 서버 접속 
    
    2) 설치된 mysql로 mysql -u root -p 를 통해 실행   
<br>
    
#### 2. MysqlWorkBench에 연결하여 조작
    1) 외부에서 사용자가 연결할 수 있도록 권한 부여
        - GRANT ALL privileges ON DB명.* TO '사용자'@'%' IDENTIFIED BY '비밀번호'; (localhost -> %) 
        
    2) 워크벤치에 퍼블릭 IP주소와 사용자 입력 후 연결   
   
<br><br><br>
