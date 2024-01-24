TOPIC_NAME=$1
if [ -z ${TOPIC_NAME} ];then
        echo "변수1 토픽명을 입력하세요"
        exit
fi

/bin/kafka-console-consumer --topic "${TOPIC_NAME}" --from-beginning --bootstrap-server localhost:9092