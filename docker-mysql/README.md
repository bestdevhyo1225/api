# Master / Slave 기반 Docker MySQL 컨테이너 구성

<br>

## Docker Container 실행 방법

> Master, Slave로 나눠진 Docker MySQL 컨테이너를 실행한다.

```shell
# 내부에 Image 생성, Container 실행하는 명령 스크립트가 있음.
./init.sh
```

<br>

## Master 상태 확인하기

> docker 컨테이너에 접속하는 쉘 스크립트를 실행한다.

```shell
./master.sh
```

> 다음 명령을 입력하면, 현재 Master-Slave 상태인지 확인할 수 있다.

```shell
show processlist\G
```

> 아래 이미지에서 Id가 2번인 Row를 보면, State가 `Master has sent all binlog slave; wating for more updates` 라고 뜨게 되면, 성공한 것이라고 보면된다.

![image](https://user-images.githubusercontent.com/23515771/102971124-1e155180-453c-11eb-9146-1faaea912dba.png)

<br>

## Slave 상태 확인하기

> docker 컨테이너에 접속하는 쉘 스크립트를 실행한다.

```shell
# read 1 db
./slave1.sh

# read 2 db
./slave2.sh
```

> 다음 명령을 입력하면, 현재 Master-Slave 상태인지 확인할 수 있다.

```shell
show processlist\G
```

> 아래의 이미지에서 Id 4, 5번인 Row를 보게 되면, State가 각각 `Waiting for master to send event`, `Slave has read all relay log; wating for more updates` 인 것을 볼 수 있는데, 이러한 상태이면 정상적으로 연결된 것이라 볼 수 있다. 

<img width="961" alt="스크린샷 2020-12-23 오후 4 38 05" src="https://user-images.githubusercontent.com/23515771/102971837-700aa700-453d-11eb-93c0-49439cbf939f.png">

<br>

> 추가적으로 다음 명령을 통해, 자세하게 상태를 알아볼 수 있다.

```shell
show slave status\G
```

<img width="1181" alt="스크린샷 2020-12-23 오후 4 43 08" src="https://user-images.githubusercontent.com/23515771/102972279-23739b80-453e-11eb-8935-37ce350524f9.png">

<img width="1229" alt="스크린샷 2020-12-23 오후 4 43 42" src="https://user-images.githubusercontent.com/23515771/102972332-37b79880-453e-11eb-9340-d3017d66429e.png">

<br>

## 문제 및 해결 방법

`문제`

- Slave 컨테이너에서 Master를 연결할 때, Network 문제로 연결되지 않았음

`해결`

- docker-compose 파일에 Network 관련 설정을 추가해서 해결했다.(Docker Network는 틈틈히 공부해야 함.)

```yaml
# netowrks: container 들이 사용할 network 대역을 설정함.
# ipv4_address를 옵션을 사용하는 경우, container 의 ip를 고정시킬 수 있음
services:
  master:
    networks:
    dock_net:
      ipv4_address: 172.16.0.10

  slave1:
    networks:
    dock_net:
      ipv4_address: 172.16.0.11
      
  slave2:
    networks:
    dock_net:
      ipv4_address: 172.16.0.12

networks:
  dock_net:
    driver: bridge
    ipam:
      config:
        - subnet: 172.16.0.0/16
```

<br>

## 참고

- [Docker-compose로 Mysql Replication 자동 구성하기](https://kimdubi.github.io/etc/docker-mysql-replication/)
- [[MySQL]Replication으로 DB Master, Slave구조 만들어서 데이터 동기화 하기(feat. docker, shell script)](https://kamang-it.tistory.com/entry/MySQLReplication%EC%9C%BC%EB%A1%9C-DB-Master-Slave%EA%B5%AC%EC%A1%B0-%EB%A7%8C%EB%93%A4%EC%96%B4%EC%84%9C-%EB%8D%B0%EC%9D%B4%ED%84%B0-%EB%8F%99%EA%B8%B0%ED%99%94-%ED%95%98%EA%B8%B0feat-docker-shell-script)
