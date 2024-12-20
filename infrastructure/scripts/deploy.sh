ALL_PORT=("8081" "8082")
CONTAINER_NAME="infrastructure"
HEALTH_CHECK_URI="actuator/health"
NGINX_CONFIG_PATH="/etc/nginx/conf.d/service-url.inc"
BLUE_PORT=8081
GREEN_PORT=8082
TAG="latest"

echo "Health Check = $(curl -s http://localhost/${HEALTH_CHECK_URI} | grep -oP '(blue|green)')"
# 현재 실행 중인 Blue/Green 컨테이너 확인
CURRENT_SERVICE=$(curl -s http://localhost/${HEALTH_CHECK_URI} | grep -oP '(blue|green)')
echo "> Current Service = ${CURRENT_SERVICE}"

if [ "$CURRENT_SERVICE" == "blue" ]; then
        IDLE_PORT=$GREEN_PORT
        NEW_SERVICE="green"
else
        IDLE_PORT=$BLUE_PORT
        NEW_SERVICE="blue"
fi

echo "현재 서비스: $CURRENT_SERVICE"
echo "새로운 서비스: $NEW_SERVICE (포트: $IDLE_PORT)"

echo "Docker 이미지 Pull.."
sudo docker pull jaemin5548/woojjam-infrastructure-server:${TAG}

if docker ps -a --format '{{.Names}}' | grep "${NEW_SERVICE}$"; then
        echo "Container ${NEW_SERVICE} stop and remove..."
        docker stop $NEW_SERVICE
        docker rm $NEW_SERVICE
else
        echo "Container ${NEW_SERVICE} is not found"
fi

sudo docker run --name $NEW_SERVICE -d -p $IDLE_PORT:8080 jaemin5548/woojjam-infrastructure-server
