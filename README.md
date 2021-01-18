# Api

- MySQL Replication 구축
- Redis Cache 구축
- Custom LoggingInterceptor 구현
- Global Exception Handler 구현
- Jwt 기반 Security 구축

<br>

## Framework & Library

- Spring Boot (Gradle)
- Spring Web
- Spring Security
- Spring Data JPA
- Spring Data Redis (Lettuce)
- Querydsl
- JDK 11
- JUnit 5
- ArchUnit (JUnit 5)

<br>

## 시스템 아키텍쳐

- `Web Layer`는 `Usecase Layer`에 의존하고 있다.

- `Usecase Layer`는 `Domain Layer`에 의존하고 있다.

- `Data Layer`는 `Domain Layer`을 의존하고 있으며, Repository의 구현체들이 위치하고 있다.

- `Domain Layer` 내부에서는 의존하는 상황이 발생하지만, 다른 외부의 Layer는 절대로 의존하지 않는다.

![image](https://user-images.githubusercontent.com/23515771/104912115-365b7d80-59cf-11eb-87cd-fb3035bfa507.png)

> ArchUnit 라이브러리를 활용하여, 아키텍쳐 테스트 완료

- [LayerArchitectureTest](https://github.com/bestdevhyo1225/api/blob/master/src/test/java/com/hyoseok/dynamicdatasource/LayerArchitectureTest.java)

- [LayerDependencyRulesTest](https://github.com/bestdevhyo1225/api/blob/master/src/test/java/com/hyoseok/dynamicdatasource/LayerDependencyRulesTest.java)

- [CyclicDependencyRulesTest](https://github.com/bestdevhyo1225/api/blob/master/src/test/java/com/hyoseok/dynamicdatasource/CyclicDependencyRulesTest.java)

<br>

## MySQL Replication 및 Redis 실행 방법

> Docker 기반의 MySQL Replication

- [MySQL Replication 구성 및 실행 방법](https://github.com/bestdevhyo1225/dynamic-datasource/tree/master/docker-mysql)

> Docker 기반의 Redis

```shell
docker-compose -f docker-redis/docker-compose.yml up -d
```
