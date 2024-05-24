package id.my.hendisantika.kafkastreamsstatestore.kafka;

import id.my.hendisantika.kafkastreamsstatestore.dto.OrderLocation;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Created by IntelliJ IDEA.
 * Project : kafka-streams-state-store
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 5/24/24
 * Time: 09:17
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@Component
public class OrderLocationStreamsProcessor implements Processor<String, OrderLocation> {

    private KeyValueStore<String, OrderLocation> stateStore;

    @Value(value = "${kafka.streams.stateStoreName}")
    private String stateStoreName;

    @Override
    public void init(ProcessorContext context) {
        stateStore = context.getStateStore(stateStoreName);
        Objects.requireNonNull(stateStore, "State store can't be null");
    }

    @Override
    public void process(String orderNumber, OrderLocation orderLocation) {
        log.info("Streams Request to save process Order Location : {}", orderLocation);

        //add processing the kafka message here like save to NoSQL DB, Elastic Search, do aggregation

        stateStore.put(orderNumber, orderLocation);
    }

    @Override
    public void close() {

    }
}
