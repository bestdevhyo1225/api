#!/usr/bin/env bash

# make mymaster image
docker build -t mymaster .

# make myslave image
docker build -t myslave .

# execute mymaster, myslave container
docker-compose up -d

sleep 20

## shellcheck disable=SC2006
#master_log_file=`mysql -h127.0.0.1 --port 3333 -uroot -e "show master status\G" | grep mysql-bin`
#re="[a-z]*-bin.[0-9]*"
#
#if [[ ${master_log_pos} =~ $re ]];then
#  master_log_file=${BASH_REMATCH[0]}
#fi
#
## shellcheck disable=SC2006
#master_log_pos=`mysql -h127.0.0.1 --port 3333 -uroot -e "show master status\G" | grep Position`
#re="[0-9]+"
#
#if [[ ${master_log_pos} =~ $re ]];then
#  master_log_pos=${BASH_REMATCH[0]}
#fi

query="change master to master_host='172.16.0.10', master_port=3306, master_user='slaveuser', master_password='slavepassword', master_log_file='mysql-bin.000003', master_log_pos=154"

mysql -h127.0.0.1 --port 9031 -uroot -e "${query}"
mysql -h127.0.0.1 --port 9031 -uroot -e "start slave"
