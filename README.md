# Api

**`Framework & Library`**

- Spring Boot (Gradle)
- Spring Web
- Spring Security
- Spring Data JPA
- Spring Data Redis (Lettuce)
- Querydsl
- JDK 11
- JUnit 5
- ArchUnit (JUnit 5)
  
**`ETC`**

- Global Exception Handler
- Logging Interceptor

<br>

## Master/Slave Docker Container 만들기

- [Master / Slave 기반 Docker MySQL 컨테이너 구성하러 가기](https://github.com/bestdevhyo1225/dynamic-datasource/tree/master/docker-mysql)

<br>

## 테스트 결과

### MySQL - Master / Slave 테스트

> Insert, Update, Delete는 Master를 통해서 진행된다. (`@Transactional`)

Log를 보면, `determineCurrentLookupKey() - isReadOnly : false`가 표시되는 것을 확인할 수 있다.

`Insert`

![image](https://user-images.githubusercontent.com/23515771/103011231-eed30480-457c-11eb-9881-3419e4b9945b.png)

`Update`

- 아래 이미지에서 보이는 Select는 Update를 위한 Select임

<img width="1262" alt="스크린샷 2020-12-24 오전 12 15 28" src="https://user-images.githubusercontent.com/23515771/103011467-54bf8c00-457d-11eb-9200-3fc7920ba8b1.png">

> Select는 Slave를 통해서 진행된다. (`@Transactional(readOnly = true)`)

Select 쿼리의 경우, Log에 `determineCurrentLookupKey() - isReadOnly : true`가 표시되는 것을 확인할 수 있다.

`Select`

<img width="1197" alt="스크린샷 2020-12-24 오전 12 18 40" src="https://user-images.githubusercontent.com/23515771/103011746-c7c90280-457d-11eb-9e19-e049f2e5bef5.png">

<br>

### Redis 테스트

> `@Cachable`, `@CachePut` 어노테이션이 있는 메소드는 데이터를 `Redis`에 캐시한다.

첫 번째 조회를 하면, `MySQL`에서 데이터를 가져오고, `Redis`에 캐시한다.

![image](https://user-images.githubusercontent.com/23515771/103762063-0d47fe00-505b-11eb-9f36-765e2907acd2.png)

<img width="1212" alt="스크린샷 2021-01-06 오후 8 11 37" src="https://user-images.githubusercontent.com/23515771/103762422-9c551600-505b-11eb-8844-271cf3da598c.png">

두 번째 조회를 하면, `Redis`에서 데이터를 가져오고, `Logging`만 출력한다.

<img width="1011" alt="스크린샷 2021-01-06 오후 8 12 22" src="https://user-images.githubusercontent.com/23515771/103762488-b5f65d80-505b-11eb-9ff1-4aa000b9765a.png">

<br>

### 시스템 아키텍쳐

- `Web`은 `Usecase`에 의존하고 있다.

- `Usecase`는 `Domain`을 의존하고 있다.

- `Domain`은 어떤 Layer도 의존하지 않는다.

- `Data`는 `Domain`을 의존하고 있으며, Repository의 구현체들이 위치하고 있다.

![image](https://user-images.githubusercontent.com/23515771/104912115-365b7d80-59cf-11eb-87cd-fb3035bfa507.png)

> ArchUnit 라이브러리를 활용하여, 아키텍쳐 테스트 완료

- [LayerArchitectureTest](https://github.com/bestdevhyo1225/api/blob/master/src/test/java/com/hyoseok/dynamicdatasource/LayerArchitectureTest.java)

- [LayerDependencyRulesTest](https://github.com/bestdevhyo1225/api/blob/master/src/test/java/com/hyoseok/dynamicdatasource/LayerDependencyRulesTest.java)

- [CyclicDependencyRulesTest](https://github.com/bestdevhyo1225/api/blob/master/src/test/java/com/hyoseok/dynamicdatasource/CyclicDependencyRulesTest.java)
