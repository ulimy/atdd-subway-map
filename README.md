<p align="center">
    <img width="200px;" src="https://raw.githubusercontent.com/woowacourse/atdd-subway-admin-frontend/master/images/main_logo.png"/>
</p>
<p align="center">
  <a href="https://techcourse.woowahan.com/c/Dr6fhku7" alt="woowacourse subway">
    <img alt="Website" src="https://img.shields.io/website?url=https%3A%2F%2Fedu.nextstep.camp%2Fc%2FR89PYi5H">
  </a>
  <img alt="GitHub" src="https://img.shields.io/github/license/woowacourse/atdd-subway-map">
</p>

<br>

# 지하철 노선도 미션

스프링 과정 실습을 위한 지하철 노선도 애플리케이션

<br>

## 🚀 Getting Started

### Usage

#### application 구동

```
./gradlew bootRun
```

<br>

## ✏️ Code Review Process

[텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

<br>

## 🐞 Bug Report

버그를 발견한다면, [Issues](https://github.com/woowacourse/atdd-subway-map/issues) 에 등록해주세요 :)

<br>

## 📝 License

This project is [MIT](https://github.com/woowacourse/atdd-subway-map/blob/master/LICENSE) licensed.

## 1단계 기능 요구사항

- 지하철 역
    - [x] 등록
        - [x] 이미 등록된 이름이라면 에러가 발생한다.
    - [x] 목록
    - [x] 삭제
        - [x] 등록되지 않은 역이라면 에러가 발생한다.

- 지하철 노선
    - [x] 등록
        - [x] 이미 등록된 이름이라면 에러가 발생한다.
    - [x] 목록
    - [x] 조회
        - [x] 등록되지 않은 노선이라면 에러가 발생한다.
    - [x] 수정
        - [x] 이미 등록된 이름이라면 에러가 발생한다.
    - [x] 삭제
        - [x] 등록되지 않은 노선이라면 에러가 발생한다.

## 2단계 기능 요구사항

- [x] 스프링 JDBC를 활용하여 H2 데이터베이스에 데이터를 관리한다.
- [x] 스프링 빈을 활용하여 객체를 관리한다.

## 3단계 기본 기능 요구사항

- 지하철 노선
    - [ ] 지하철 노선 등록시 상행종점, 하행종점, 두 종점간의 거리를 추가로 입력받는다.
        - [ ] 종점이 등록되지 않은 지하철 역이라면 에러가 발생한다.
        - [ ] 상행종점과 하행종점이 같다면 에러가 발생한다.
        - [ ] 거리가 1이상의 정수가 아니라면 에러가 발생한다.
- [ ] 두 종점간의 연결정보를 저장하는 구간 정보가 함께 등록된다.
