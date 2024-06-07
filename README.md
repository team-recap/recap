# Recap &middot; [![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/facebook/react/blob/main/LICENSE) [![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](https://reactjs.org/docs/how-to-contribute.html#your-first-pull-request)
Recap은 텍스트 요약을 위한 Java 라이브러리입니다.

* **TextRank 알고리즘 :** Recap은 TextRank 알고리즘을 사용하여 텍스트 요약을 빠르게 수행할 수 있습니다.
* **코사인/자카드 유사도 제공 :** 문장 간 유사도를 측정하는 계산법으로 코사인과 자카드 유사도 방법을 제공하여 프로젝트에 맞는 계산방법을 선택할 수 있습니다.
* **순수 Java 라이브러리 :** 100% Java로 구현된 라이브러리이기 때문에 Java가 설치된 곳이라면 어디서든지 사용이 가능합니다.
* **낮은 의존도 :** 형태소 분석기 외에는 라이브러리에 대한 의존성이 없으므로 간편하게 텍스트 요약을 구현할 수 있습니다.

## 사용 방법
Recap을 사용하기 위해 형태소 분석을 위한 `한나눔 라이브러리`와 `Recap 라이브러리`를 추가합니다.
### Gradle
```gradle
repositories {
    // 레포지토리 추가
    maven { url 'https://jitpack.io' }
}

dependencies {
    // 라이브러리 추가
    implementation "kr.bydelta:koalanlp-hnn:2.1.4:assembly"
    implementation 'com.github.team-recap:recap:latest'
}
```
### Maven
```xml
<!-- 레포지토리 추가 -->
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<!-- 라이브러리 추가 -->
<dependency>
    <groupId>kr.bydelta</groupId>
    <artifactId>koalanlp-hnn</artifactId>
    <version>2.1.4</version>
</dependency>
<dependency>
    <groupId>com.github.team-recap</groupId>
    <artifactId>recap</artifactId>
    <version>v0.0.6</version>
</dependency>
```
### 텍스트 요약
```java
// 요약할 텍스트
String text = "네. 제가 가져온 아이디어는 소셜 로그인을 쉽게 구축할 수 있는 라이브러리입니다. 웹 서비스를 제작해보신 분들을 알겠지만 소셜 로그인을 구현하는게 굉장히 어렵습니다. 소셜 플랫폼과의 연동뿐만아니라 해당 과정을 클라이언트와 연동하는 과정이 생각보다 많이 복잡합니다. 그래서 이 과정을 차라리 라이브러리화 해서 다양한 소셜 플랫폼을 지원할 뿐만아니라 쉽게 이용할 수 있도록 제작해보고 싶습니다.";

Summarizer summarizer = new Summarizer(); // Summarizer 객체 생성
List<String> summarizedText = summarizer.summarize(text, // 요약할 텍스트
    Graph.SimilarityMethods.COSINE_SIMILARITY); // 문장 간 유사도 계산법 - COSINE, JACCARD 유사도 측정법 사용 가능

System.out.println(summarizedText);
// [웹 서비스를 제작해보신 분들을 알겠지만 소셜 로그인을 구현하는게 어렵습니다., 이 과정을 라이브러리화 해서 다양한 소셜 플랫폼을 지원할 쉽게 이용할 수 있도록 제작해보고 싶습니다.]
```

## 아키텍처
![structure](https://github.com/team-recap/recap/assets/35624367/b8060762-19b6-455f-a26a-3c18dac6d160)


## 라이선스
[MIT license](https://github.com/team-recap/recap/blob/main/LICENSE)
