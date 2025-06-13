# Groom 서버
해당 문서에 대해 제안사항이 있다면 PR을 올리고 슬랙에 공유해주세요.

## 컨벤션

### 브랜치 네이밍
- {type}/#{이슈번호}

### 커밋 컨벤션
- [AngularJS commit conventions](https://lewis-kku.tistory.com/45)를 준수합니다.  
- [Intellij Plugin](https://plugins.jetbrains.com/plugin/13389-conventional-commit)  
- 만약 브랜치에서 해결하는 이슈와 다른 이슈에 해당하는 커밋을 작성해야할 경우  
`{type}({scope}): {내용}(#{이슈번호})`로 작성합니다.

### PR Merge 컨벤션
- PR병합은 Squash-Merge를 사용합니다.  
- 머지 커밋 이름은 기본 생성된 이름 `{PR제목} (#{PR번호})`을 사용합니다.


### 코드 컨벤션
1. 클래스 작성시 kotlin은 internal, Java는 package-private (default 접근 제어자)을 사용합니다.
2. 도메인 엔티티와 디비 엔티티를 분리합니다.
    1. 디비 엔티티는 도메인 로직을 가지지 않습니다.
    2. update, domain 컨버팅 로직, 콜렉션 원소 변경로직(1대다 관계가 있을경우)을 가집니다.
        1. 애매하다 싶다면, MyBatis 등 레포지터리에서 쿼리로 처리할 수 있는 내용을 자바로 넣는다고 생각해도 좋습니다.
        2. JPA는 변경감지로 일괄 변경이 기본 쿼리기 때문에 update, 콜렉션 변경처럼 변경감지에 해당하는 로직만 넣습니다.
    3. 이외 결정하기 애매할 경우 PR에 명시해주세요.

