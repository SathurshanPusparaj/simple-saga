A simple choreography SAGA

kafka-topics.bat --zookeeper 127.0.0.1:2181 --topic payment_request_topic --create --partitions 3 --replication-factor 1
kafka-topics.bat --zookeeper 127.0.0.1:2181 --topic payment_response_topic --create --partitions 3 --replication-factor 1