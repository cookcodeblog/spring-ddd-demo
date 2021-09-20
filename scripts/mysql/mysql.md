[TOC]

# MySQL local test


## Run MySQL server with Docker

```bash
# run mysql server in docker
docker run --name local-mysql  \
-p 3306:3306 \
-e MYSQL_ROOT_HOST='%' -e MYSQL_ROOT_PASSWORD='admin123'   \
-d mysql/mysql-server:latest
```

## Connect to MySQL server

```bash
# connect to mysql server 
docker exec -it local-mysql sh 
mysql -uroot -padmin123
```

## create test database and grant to user

```bash
# replace `orderdb` as your db
# create test database and grant to user
CREATE DATABASE `orderdb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# create user and grant permissions
CREATE USER 'springuser'@'%' IDENTIFIED BY 'test123' PASSWORD EXPIRE NEVER;
ALTER USER 'springuser'@'%' IDENTIFIED WITH mysql_native_password BY 'test123';
GRANT ALL ON orderdb.* to 'springuser'@'%';

FLUSH PRIVILEGES;

exit

# Verify
mysql -uspringuser -ptest123

# replace `orderdb` as your db
show datbases;
use orderbd;
show tables;

```


