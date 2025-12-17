# kafka-runner

Runner for Spring EmbeddedKafkaKraftBroker local test deployment

Usage:
`java -jar target/kafka-runner-1.0.0.jar [options]`

```
Options: IMPORTANT: EmbeddedKafkaKraftBroker does not support setting a port (e.g. 9092) - it will always be random so check the logs 
--partitions=1       Default partitions per topic (default: 1)
--topics=topic1,topic2  Comma-separated list of topics to create 

Example: `java -jar target/kafka-runner-1.0.0.jar --partitions=3 --topics="test1,test2,test3"`
```