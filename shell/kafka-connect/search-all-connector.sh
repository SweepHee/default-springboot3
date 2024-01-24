CONNECTOR_NAME=$1
if [ -z ${CONNECTOR_NAME} ];then
        echo "변수1 커넥터명을 입력하세요"
        exit
fi
curl "http://localhost:8083/connectors/${CONNECTOR_NAME}"