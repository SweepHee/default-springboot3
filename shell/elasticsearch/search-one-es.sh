INDEX=$1
if [ -z ${INDEX} ];then
        echo "변수1 인덱스명을 입력해주세요"
        exit
fi
curl -XGET "http://localhost:9200/${INDEX}/_search?pretty"