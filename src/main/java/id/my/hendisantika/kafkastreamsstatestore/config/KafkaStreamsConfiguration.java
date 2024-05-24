package id.my.hendisantika.kafkastreamsstatestore.config;

import id.my.hendisantika.kafkastreamsstatestore.dto.OrderLocation;
import id.my.hendisantika.kafkastreamsstatestore.kafka.OrderLocationStreamsProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.errors.LogAndContinueExceptionHandler;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.Properties;

import static org.apache.kafka.streams.StreamsConfig.APPLICATION_ID_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG;

/**
 * Created by IntelliJ IDEA.
 * Project : kafka-streams-state-store
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 5/24/24
 * Time: 09:13
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class KafkaStreamsConfiguration {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value(value = "${kafka.streams.applicationId}")
    private String applicationId;

    @Value(value = "${kafka.topics.msgOrderLocation.name}")
    private String msgOrderLocationTopic;

    @Value(value = "${kafka.streams.stateStoreName}")
    private String stateStoreName;

    private final ObjectFactory<OrderLocationStreamsProcessor> orderLocationStreamsProcessorObjectFactory;

    private final Deserializer<String> keyDeSerializer = new StringDeserializer();

    private final Deserializer<OrderLocation> valueDeSerializer =
            new JsonDeserializer<>(OrderLocation.class).ignoreTypeHeaders();

    private final Serde<String> keySerializer = Serdes.String();

    private final Serde<OrderLocation> valueSerializer = new JsonSerde<>(OrderLocation.class).ignoreTypeHeaders();

//    public KafkaStreamsConfiguration(ObjectFactory<OrderLocationStreamsProcessor> orderLocationStreamsProcessorObjectFactory) {
//        this.orderLocationStreamsProcessorObjectFactory = orderLocationStreamsProcessorObjectFactory;
//    }
//
public OrderLocationStreamsProcessor getOrderLocationStreamsProcessor() {
    return orderLocationStreamsProcessorObjectFactory.getObject();
}

    @Bean
    @Primary
    public KafkaStreams kafkaStreams() {
        log.info("Create Kafka Stream Bean with defined topology");
        Topology topology = this.buildTopology(new StreamsBuilder());
        final KafkaStreams kafkaStreams = new KafkaStreams(topology, createConfigurationProperties());
        kafkaStreams.start();
        return kafkaStreams;
    }

    /**
     * This method is used for defining topology for KafkaStreams
     * Topology:
     * 1. read the topic
     * 2. send to stream processor for processing the message
     * 3. persist message to key-value State Store
     *
     * @param streamsBuilder new Stream Builder
     * @return Topology
     */
    private Topology buildTopology(StreamsBuilder streamsBuilder) {
        Topology topology = streamsBuilder.build();

        StoreBuilder<KeyValueStore<String, OrderLocation>> stateStoreBuilder =
                Stores.keyValueStoreBuilder(Stores.persistentKeyValueStore(stateStoreName), keySerializer, valueSerializer);

        topology.addSource("Source", keyDeSerializer, valueDeSerializer, msgOrderLocationTopic)
                .addProcessor("Process", this::getOrderLocationStreamsProcessor, "Source")
                .addStateStore(stateStoreBuilder, "Process");
        return topology;
    }

    /**
     * This method is used for setting the configuration of Kafka Stream
     *
     * @return Properties
     */
    private Properties createConfigurationProperties() {
        final Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(APPLICATION_ID_CONFIG, applicationId);
        props.put(DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG, LogAndContinueExceptionHandler.class);
        return props;
    }
}
