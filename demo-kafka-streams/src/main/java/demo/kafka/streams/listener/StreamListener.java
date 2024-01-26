//package demo.kafka.streams.listener;
//
//import com.jayway.jsonpath.JsonPath;
//import demo.common.model.KafkaTopic;
//import org.apache.catalina.util.CustomObjectInputStream;
//import org.apache.kafka.common.serialization.Serdes;
//import org.apache.kafka.streams.KeyValue;
//import org.apache.kafka.streams.StreamsBuilder;
//import org.apache.kafka.streams.kstream.Grouped;
//import org.apache.kafka.streams.kstream.KStream;
//import org.apache.kafka.streams.kstream.Produced;
//import org.apache.kafka.streams.kstream.TimeWindows;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
//import java.time.Duration;
//import java.util.Arrays;
//import java.util.stream.Stream;
//
//@Component
//public class StreamListener {
//
//    private final String INPUT_TOPIC = KafkaTopic.Topic.CHECKOUT_COMPLETE_TOPIC.getTopic();
//    private final String OUTPUT_TOPIC = KafkaTopic.Topic.CHECKOUT_OUTPUT_TOPIC.getTopic();
//
//    @Bean
//    public KStream<String, String> jsonToKStream(StreamsBuilder builder) {
//        String[] test = {"1","2"};
//        Arrays.stream(test).map(i -> i + "aa");
//        KStream<String, String> inputStream = builder.stream(INPUT_TOPIC);
//        inputStream
//                .map ((key, value) -> new KeyValue<>(getProductId(value), getAmount(value)) )
//                .groupByKey(Grouped.with(Serdes.Long(), Serdes.Long()) )
//                .windowedBy(TimeWindows.ofSizeAndGrace(Duration.ofMinutes(1), Duration.ZERO))
//                .reduce(Long::sum)
//                .toStream((key, value) -> key.key())
//                .mapValues(StreamListener::sendingJson)
//                .selectKey((key, value) -> null)
//                .to(OUTPUT_TOPIC, Produced.with(null, Serdes.String()));
//
//        return inputStream;
//    }
//
//
//
//    public static Long getProductId(String productId) {
//        return JsonPath.parse(productId).read("$.productId", Long.class);
//    }
//
//    public static Long getAmount(String amount) {
//        return JsonPath.parse(amount).read("$.amount", Long.class);
//    }
//
//    public static String sendingJson(Long productId, Long amount) {
//        String jsonData = "{\"productId\":%d,\"windowedAmount\":%d,\"createdAt\":%d}";
//        return String.format(jsonData, productId, amount, System.currentTimeMillis());
//    }
//
//}