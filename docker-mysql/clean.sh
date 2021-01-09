#!/usr/bin/env bash

docker stop mymaster
docker stop myslave1
docker stop myslave2
docker rm mymaster
docker rm myslave1
docker rm myslave2
docker rmi mymaster
docker rmi myslave1
docker rmi myslave2
