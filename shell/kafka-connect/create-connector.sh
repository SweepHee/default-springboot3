CONNECTOR_NAME=$1
TOPIC_NAME=$2
if [ -z ${CONNECTOR_NAME} ];then
        echo \"변수1 커넥터을 입력해주세요\"
        exit
fi
if [ -z ${TOPIC_NAME} ];then
        echo \"변수2 토픽명을 입력해주세요\"
        exit
fi

curl -X POST http://localhost:8083/connectors -H 'Content-Type: application/json' -d \
"{
  \"name\": \"${CONNECTOR_NAME}\",
  \"config\": {
    \"connector.class\": \"io.confluent.connect.elasticsearch.ElasticsearchSinkConnector\",
    \"tasks.max\": \"1\",
    \"topics\": \"${TOPIC_NAME}\",
    \"key.ignore\": \"true\",
    \"schema.ignore\": \"true\",
		\"schemas.enable\": \"false\",
    \"connection.url\": \"http://host.docker.internal:9200\",
    \"type.name\": \"kafka-logs\",
    \"name\": \"${CONNECTOR_NAME}\",
    \"value.converter\": \"org.apache.kafka.connect.json.JsonConverter\",
    \"value.converter.schemas.enable\": \"false\",
		\"transforms\": \"TimestampRouter\",
		\"transforms.TimestampRouter.type\": \"org.apache.kafka.connect.transforms.TimestampRouter\",
		\"transforms.TimestampRouter.topic.format\": \"log-checkout.complete-\${timestamp}\",
		\"transforms.TimestampRouter.timestamp.format\": \"YYYYMM\",
		\"flush.synchronously\": \"true\"
  }
}"