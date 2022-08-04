# TweetAppBackendService

Steps to run the Tweet app project:


Step 1: Start the zookeeper server for kafka to run the application using following command.
            	D:\apache_kafka_2.13-2.8.0\bin\windows> zookeeper-server-start.bat ../../config/zookeeper.properties

Step 2: Start the Kafka server using the following command.
              D:\apache_kafka_2.13-2.8.0\bin\windows> kafka-server-start.bat ../../config/server.properties

Step 3: Create two topic for TweetApp tweets and Logs to post message in kafka server using following command.
              C:\Users\Prakash> kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 3 --topic TweetApp
              C:\Users\Prakash> kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 3 --topic Logs

Step 4: Open Kafka TweetApp consumer and Logs Consumer to listen the Tweets and Logs posted through kafka producer by subscribing to kafka topic. 
              C:\Users\Prakash> kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic TweetApp
              C:\Users\Prakash> kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic  Logs

Step 5: Start the MongoDB server when using in local or use it from online cluster. 
              C:\Program Files\MongoDB\Server\5.0\bin > mongod.exe

Step 6: Start the Tweet app spring boot application which is running on port number 8080.
	            http://localhost:8080/

Step 7: Check whether actuator is working or not using the below link from the browser.
	            http://localhost:8080/actuator/
  
Step 8: Check the health status of the application by using following link.
	            http://localhost:8080/actuator/health

Step 9: We can check the performance of the application by using Prometheus in two ways. 
	            Method 1: Go to below endpoint in browser to check the textual data.
			                        http://localhost:8080/actuator/prometheus
              Method 2: Go to Prometheus folder in your system, open cmd and run the following     command and open the Prometheus endpoint in browser to get the graphical interface of Prometheus.
	                             D:\prometheus-2.37.0.windows-amd64> .\prometheus.exe
		                            http://localhost:9090/
Step 10: Go to Grafana folder in your system, open cmd and run the following  command and open the Grafana endpoint in browser to get the advanced graphical interface of the application.
	            D:\grafana-9.0.6\bin > grafana-server.exe
	            http://localhost:3000/

Step 11: Go to Elastic Search folder in your system, open cmd and run the following command and open Elastic Search endpoint in browser for detailed log tracking.
	            D:\elasticsearch-8.3.3\bin > .\elasticsearch.bat 
	            http://localhost:9200/

Step 12: Go to Kibana folder in your system, open cmd and run the following command and open Kibana endpoint in browser for log tracking in GUI.
              D:\kibana-8.3.3\bin> .\kibana.bat
	            http://localhost:5601/

Step 13: Go to Logstash folder in your system, open cmd and run the following command and before that place the logstash.conf file into conf folder by specifying the tweetlog.log file path in logstash.conf and open Logstash endpoint in browser for pulling logs from various sources from your application.
	            D:\logstash-8.3.3\bin> .\logstash.bat –f D:\logstash-8.3.3\config\logstash.conf
	            http://localhost:9600/

Step 14: For Swagger UI of Tweet App, go to the following endpoint in browser.     
              http://localhost:8080/swagger-ui.html
