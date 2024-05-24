package id.my.hendisantika.kafkastreamsstatestore.service;

import id.my.hendisantika.kafkastreamsstatestore.dto.OrderLocation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * Project : kafka-streams-state-store
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 5/24/24
 * Time: 09:20
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderLocationService {

    private final KafkaStreams kafkaStreams;

    @Value(value = "${kafka.streams.stateStoreName}")
    private String stateStoreName;

    public OrderLocation getOrderLocation(String orderNumber) {
        StoreQueryParameters<ReadOnlyKeyValueStore<String, OrderLocation>> storeQueryParameters =
                StoreQueryParameters.fromNameAndType(stateStoreName, QueryableStoreTypes.keyValueStore());
        OrderLocation orderLocation = kafkaStreams.store(storeQueryParameters)
                .get(orderNumber);
        log.info("Order Location Result: {}", orderLocation);
        return orderLocation;
    }

}
