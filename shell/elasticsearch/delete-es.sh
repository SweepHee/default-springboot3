INDEX_NAME=$1
if [ -z ${INDEX_NAME} ];then
        echo "변수1 인덱스명을 입력하세요"
        exit
fi

curl -X DELETE "localhost:9200/${INDEX_NAME}"