```
sudo docker run -d --name mysql -v /home/chins/docker/mysql/volume:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 -p 3306:3306 mysql
```



```
sudo docker run --name redis -p 6379:6379 -d --restart=always redis redis-server --appendonly yes --requirepass "123456"
```

