TOPIC=$1

if [ -z ${TOPIC} ];then
        echo "변수1 입력해주세요"
        exit
fi

IS_TOPIC=$(/bin/kafka-topics --list --bootstrap-server localhost:9092 | grep ${TOPIC})

if [ -z ${IS_TOPIC} ];then
        echo "${TOPIC}은 없는 토픽입니다"
        exit
fi

/bin/kafka-topics --delete --topic "${TOPIC}" --bootstrap-server localhost:9092
echo "${TOPIC} 삭제했습니다"