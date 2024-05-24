package id.my.hendisantika.kafkastreamsstatestore.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

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
public class KafkaStreamsConfiguration {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value(value = "${kafka.streams.applicationId}")
    private String applicationId;

    @Value(value = "${kafka.topics.msgOrderLocation.name}")
    private String msgOrderLocationTopic;

    @Value(value = "${kafka.streams.stateStoreName}")
    private String stateStoreName;
}
