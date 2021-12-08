# apache-kafka

The Apache Kafka Documentation provides information about

How to install, configure, and use Apache kafka - local set up . 

Download Binary : https://kafka.apache.org/downloads

Edit Zookeeper & Kafka configs using a text editor zookeeper.properties:
dataDir=/your/path/to/data/zookeeper
server.properties: log.dirs=/your/path/to/.    data/kafka
cd /Users/rajamuthua/Documents/kafka_2.12-2.1.1/bin
Start Zookeeper   > rajamuthua-a02:bin rajamuthua$./zookeeper-server-start.sh ../config/zookeeper.properties
Start kafka server   >  rajamuthua-a02:bin rajamuthua$ ./kafka-server-start.sh ../config/server.properties

**Folder :**kafkademo  - Simple Java Client Producer , Consumer .
**Folder :**KafkaTopicConfig , KafkaProducerMulti --Sample Spring Boot Code for Topic Configuration creation , Kafa Multi Producer , Kafka Multi Consumer Groups .
