TOPIC_NAME=$1
if [ -z ${TOPIC_NAME} ];then
        echo "변수1 토픽명을 입력하세요"
        exit
fi

/bin/kafka-topics --create --topic "${TOPIC_NAME}" --bootstrap-server localhost:9092 --replication-factor 1 --partitions 3