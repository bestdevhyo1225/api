# Dynamic Datasource (Replication - Master/Slave)

Docker로 Master/Slave MySQL을 직접 구성해보고, Spring Boot에서 연동시 잘 적용되는지 테스트 하기 위함

<br>

## Master/Slave Docker Container 만들기

- [Master / Slave 기반 Docker MySQL 컨테이너 구성](https://github.com/bestdevhyo1225/dynamic-datasource/tree/master/docker-mysql)

<br>

## 테스트 결과

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

## Todo

- [ ] : 다중 Slave 구성하기
