#!/bin/bash

HEALTH_CHECK_URI="actuator/health"
NGINX_CONFIG_PATH="/etc/nginx/conf.d/service-url.inc"
DOCKER_USERNAME=${DOCKER_USERNAME}
DOCKER_REPOSITORY=${DOCKER_REPOSITORY}
BLUE_PORT=8081
GREEN_PORT=8082
TAG="latest"

# 1. 현재 실행 중인 Blue/Green 컨테이너 확인
if docker ps --format '{{.Names}}' | grep -q '^blue$'; then
    CURRENT_SERVICE="blue"
elif docker ps --format '{{.Names}}' | grep -q '^green$'; then
    CURRENT_SERVICE="green"
else
    echo "실행 중인 Blue 또는 Green 컨테이너를 찾을 수 없습니다."
    CURRENT_SERVICE=""
fi

# 2. 새로운 서비스 환경 및 포트 번호 설정
if [ "$CURRENT_SERVICE" == "blue" ]; then
        IDLE_PORT=$GREEN_PORT
        NEW_SERVICE="green"
else
        IDLE_PORT=$BLUE_PORT
        NEW_SERVICE="blue"
fi

echo "현재 서비스: $CURRENT_SERVICE"
echo "새로운 서비스: $NEW_SERVICE (포트: $IDLE_PORT)"

# 3. Docker 이미지 Pull
echo "Docker 이미지 Pull.."
sudo docker pull $DOCKER_USERNAME/$DOCKER_REPOSITORY:${TAG}

# 4. 새로운 서비스가 이미 컨테이너에 등록이 되어 있다면 종료
if docker ps -a --format '{{.Names}}' | grep "${NEW_SERVICE}$"; then
        echo "Container ${NEW_SERVICE} stop and remove..."
        docker stop $NEW_SERVICE
        docker rm $NEW_SERVICE
else
        echo "Container ${NEW_SERVICE} is not found"
fi

# 5. 새로운 버전의 서비스를 실행
echo "> New Service Start : $NEW_SERVICE (PORT : $IDLE_PORT)"
sudo docker run --name $NEW_SERVICE -d -p $IDLE_PORT:8080 $DOCKER_USERNAME/$DOCKER_REPOSITORY

TOTAL_HEALTH_CHECK_COUNT=15

# 6. 새로운 버전의 서비스가 성공적으로 배포가 되었는지 Health check
for i in {1..$TOTAL_HEALTH_CHECK_COUNT}; do
        HEALTH_STATUS=$(curl -s http://localhost:${IDLE_PORT}/${HEALTH_CHECK_URI} | grep -o '"status":"UP"' | head -n 1)
        if [ "$HEALTH_STATUS" == '"status":"UP"' ]; then
                echo "Health check success: $NEW_SERVICE"
                break
        else
                echo "Health check failed, Retry ($i / 15)..."
                sleep 5
        fi
done

# 7. Nginx가 새로운 포트로 포워딩 되도록 변경
echo "> Nginx setting update"
sudo tee $NGINX_CONFIG_PATH > /dev/null <<EOL
upstream backend {
        server localhost:$IDLE_PORT;
}
EOL

echo "> nginx reloading"
sudo nginx -s reload

# 8. 이전 버전 삭제
if [ ! -z "$CURRENT_SERVICE" ] && docker ps -a --format '{{.Names}}' | grep -q "^${CURRENT_SERVICE}$"; then
    if docker ps --format '{{.Names}}' | grep -q "^${CURRENT_SERVICE}$"; then
        echo "기존 서비스 ${CURRENT_SERVICE}가 실행 중입니다. 중지 및 삭제합니다..."
        docker stop $CURRENT_SERVICE
    else
        echo "기존 서비스 ${CURRENT_SERVICE}는 실행 중이지 않습니다. 삭제만 진행합니다..."
    fi
    docker rm $CURRENT_SERVICE
else
    echo "기존 서비스 ${CURRENT_SERVICE}가 존재하지 않습니다. 삭제를 건너뜁니다."
fi
echo "배포 완료: ${NEW_SERVICE}가 현재 활성화되었습니다." 
