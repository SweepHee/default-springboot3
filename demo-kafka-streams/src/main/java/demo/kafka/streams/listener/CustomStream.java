package demo.kafka.streams.listener;

import java.io.ObjectInputStream;
import java.util.function.Predicate;
import java.util.stream.BaseStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public interface CustomStream<T> extends Stream<T> {

    Stream<T> test(Predicate<? super T> predicate);


}
