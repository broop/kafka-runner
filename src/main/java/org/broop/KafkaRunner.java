package org.broop;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.EmbeddedKafkaKraftBroker;

import java.util.Arrays;

/**
 * Runner for Spring EmbeddedKafkaKraftBroker local test deployment
 * <p>
 * Usage:
 * java -jar target/kafka-runner-1.0.0.jar [options]
 * <p>
 * Options: IMPORTANT: EmbeddedKafkaKraftBroker does not support setting a port (e.g. 9092) - it will always be random so check the logs
 * --partitions=1       Default partitions per topic (default: 1)
 * --topics=topic1,topic2  Comma-separated list of topics to create
 * Example: java -jar target/kafka-runner-1.0.0.jar --partitions=3 --topics="test1,test2,test3"
 */
@Slf4j
@Getter
@Builder
public class KafkaRunner {

    @Builder.Default
    private final int partitions = 1;
    @Builder.Default
    private final String[] topics = new String[]{"test-topic"};
    private EmbeddedKafkaBroker broker;

    static void main(String[] args) {
        var builder = KafkaRunner.builder();
        for (String arg : args) {
            if (arg.startsWith("--partitions=")) {
                builder.partitions(Integer.parseInt(arg.substring(13)));
            } else if (arg.startsWith("--topics=")) {
                builder.topics(arg.substring(9).split(","));
            }
        }
        KafkaRunner runner = builder.build();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> runner.broker.destroy()));
        runner.start();
    }

    public void start() {
        log.info("Starting Local Kafka Runner...");
        this.broker = new EmbeddedKafkaKraftBroker(1, partitions, this.topics);
        broker.afterPropertiesSet();

        log.info("=================================================");
        log.info("Local Kafka Runner started successfully!");
        log.info("Bootstrap servers: {}", broker.getBrokersAsString());
        log.info("Topics created: {}", Arrays.toString(topics));
        log.info("=================================================");
    }
}