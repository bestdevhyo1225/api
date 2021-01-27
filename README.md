# Api

![API CI](https://github.com/bestdevhyo1225/api/workflows/API%20CI/badge.svg?branch=stage)

<br>

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
- Spring Cloud Netflix Hystrix  
- Querydsl
- JDK 11
- JUnit 5
- ArchUnit (JUnit 5)

<br>

## 시스템 아키텍쳐

### Layer 의존 관계

- `Web Layer`는 `Usecase Layer`에 의존하고 있다.

- `Usecase Layer`는 `Domain Layer`에 의존하고 있다.

- `Data Layer`는 `Domain Layer`을 의존하고 있으며, Repository의 구현체들이 위치하고 있다.

- `Domain Layer`는 다른 `Layer`를 의존하지 않는다.

![image](https://user-images.githubusercontent.com/23515771/104912115-365b7d80-59cf-11eb-87cd-fb3035bfa507.png)

> ArchUnit 라이브러리를 활용하여, Layer 의존 관계 테스트 완료

- [LayerArchitectureTest](https://github.com/bestdevhyo1225/api/blob/master/src/test/java/com/hyoseok/dynamicdatasource/LayerArchitectureTest.java)

- [LayerDependencyRulesTest](https://github.com/bestdevhyo1225/api/blob/master/src/test/java/com/hyoseok/dynamicdatasource/LayerDependencyRulesTest.java)

- [CyclicDependencyRulesTest](https://github.com/bestdevhyo1225/api/blob/master/src/test/java/com/hyoseok/dynamicdatasource/CyclicDependencyRulesTest.java)

### Query Service 내부 의존 관계

- `Query Service`는 `Controller`에 의해서 호출되며, `@HystrixCommand`를 어노테이션을 통해 Cache 장애를 대응한다.

- `Cache Service`는 `QueryService`에 의해서 호출되며, `@Cacheable` 어노테이션을 통해 데이터를 캐싱한다.

- `Database Service`는 `Query Service`나 `Cache Service`에 의해서 호출되며, `Repository`를 통해 데이터베이스에 직접 조회한다.

![image](https://user-images.githubusercontent.com/23515771/105956013-7b13a280-60ba-11eb-8c36-41a7c2ba4398.png)

<br>

## MySQL Replication 및 Redis 실행 방법

> Docker 기반의 MySQL Replication

- [MySQL Replication 구성 및 실행 방법](https://github.com/bestdevhyo1225/dynamic-datasource/tree/master/docker-mysql)

> Docker 기반의 Redis

```shell
# 명령을 통해 Docker Redis가 실행된다.
docker-compose -f docker-redis/docker-compose.yml up -d
```
