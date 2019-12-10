```
sudo docker run -d --name mysql -v /home/ubuntu/docker/mysql/volume:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 -p 3306:3306 mysql
```



```
sudo docker run --name redis -p 6379:6379 -d --restart=always redis redis-server --appendonly yes --requirepass "123456"
```

```
sudo docker run -d --name elasticsearch01 -v /home/ubuntu/docker/elasticsearch/volume/elasticsearch.yml:/etc/elasticsearch/elasticsearch.yml -e ES_JAVA_OPTS="-Xms512m -Xmx512m" -p 9200:9200 -p 9300:9300 elasticsearch
```

```
docker run -it -e ELASTICSEARCH_URL="http://172.17.0.3:9200" --link a8df3db662db:elasticsearch01 -p 5601:5601 kibana
```

```
CREATE USER canal IDENTIFIED BY 'canal'; 

GRANT SELECT, REPLICATION SLAVE, REPLICATION CLIENT ON *.* TO 'canal'@'%';
-- GRANT ALL PRIVILEGES ON *.* TO 'canal'@'%' ;

FLUSH PRIVILEGES;

docker inspect 64c43cebb4b8 | grep IPAddress

docker network ls

docker run -e canal.instance.master.address=172.19.0.2:3306 \
                    -e canal.instance.dbUsername=canal \
                    -e canal.instance.dbPassword=canal \
                    -e canal.instance.connectionCharset=UTF-8 \
                    -e canal.instance.tsdb.enable=true \
                    -e canal.instance.gtidon=false \
                    -e canal.instance.filter.regex=.*\\..*  -d -p 11111:11111 --name canal-server --link mysql_db_1:mysql_db_1 --net mysql_default canal/canal-server
```