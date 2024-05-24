package id.my.hendisantika.kafkastreamsstatestore.kafka;

import id.my.hendisantika.kafkastreamsstatestore.dto.OrderLocation;
import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Component
public class OrderLocationStreamsProcessor implements Processor<String, OrderLocation> {

    private final Logger log = LoggerFactory.getLogger(OrderLocationStreamsProcessor.class);

    private KeyValueStore<String, OrderLocation> stateStore;

    @Value(value = "${kafka.streams.stateStoreName}")
    private String stateStoreName;

    @Override
    public void init(ProcessorContext context) {
        stateStore = context.getStateStore(stateStoreName);
        Objects.requireNonNull(stateStore, "State store can't be null");
    }
}
