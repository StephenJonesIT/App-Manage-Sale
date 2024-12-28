# Sử dụng hình ảnh cơ bản của Ubuntu
FROM ubuntu:20.04

# Cài đặt các gói cần thiết
RUN apt-get update && apt-get install -y \
    openjdk-11-jdk \
    wget \
    unzip \
    git \
    && rm -rf /var/lib/apt/lists/*

# Thiết lập biến môi trường JAVA_HOME
ENV JAVA_HOME /usr/lib/jvm/java-11-openjdk-amd64

# Tải và cài đặt Android SDK
RUN mkdir -p /opt/android-sdk && \
    cd /opt/android-sdk && \
    wget https://dl.google.com/android/repository/commandlinetools-linux-6858069_latest.zip -O commandlinetools.zip && \
    unzip commandlinetools.zip && \
    rm commandlinetools.zip

# Thiết lập biến môi trường ANDROID_HOME
ENV ANDROID_HOME /opt/android-sdk
ENV PATH $ANDROID_HOME/cmdline-tools/bin:$PATH

# Chấp nhận các điều khoản và cài đặt các gói SDK cần thiết
RUN yes | sdkmanager --licenses && \
    sdkmanager "platform-tools" "platforms;android-30" "build-tools;30.0.3"

# Thiết lập thư mục làm việc
WORKDIR /app

# Sao chép mã nguồn vào container
COPY . /app

# Thiết lập lệnh mặc định khi container khởi động
CMD ["./gradlew", "assembleDebug"]
