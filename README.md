## 스프링 MVC 첫 세팅 
1. 우측 상단 gradle눌러서 새로고침 한 번 
2. src/main/resources폴더로 가서 application.properties 파일에 
'server.port = 80'으로 수정
3. src/main/java에 MVCapplication클래스 main메서드 실행해서 서버띄우기
4. 한글 인코딩 필터 설정(main/resources/application.properties)

#서버 포트 변경
```
server.port = 80
```

#한글 인코딩 적용
```
spring.http.encoding.charset=UTF-8
spring.http.encoding.enabled=true
spring.http.encoding.force=ture
```

## 스프링 MVC 기본 설정 
1. 뷰 리절버 등록 
- 메인메서드가 있는 클래스 혹은 config클래스(@Configuration)에 아래의 내용을 작성
```java
@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");//접두사
		resolver.setSuffix(".jsp");//접미사
		return resolver;
	}
```

2. 데이터베이스설정
- C:\oraclexe\app\oracle\product\11.2.0\server\jdbc\lib에서 ojbc6.jar
- 아래 설정 경로 /WEB-INF/lib에 추가하기
```groovy
//database 관련 라이브러리 추가
// jdbc라이브러리
compile "org.springframework.boot:spring-boot-starter-jdbc"
//오라클라이브러리(11g edition - gradle,maven 라이센스 문제 공식 지원 불가)
compile fileTree(dir: '/src/main/webapp/WEB-INF/lib', include: ['*.jar'])
```
- 스프링에게 DataSource 정보 알려주기 (Hikari DataSource)
```java
@Configuration
@ComponentScan(basePackages = "com.spring.mvc")
public class RootConfig {

    //DB접속 정보 DataSource등록
    @Bean
    public DataSource dataSource() {

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        hikariConfig.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:xe");
        hikariConfig.setUsername("java_web3");
        hikariConfig.setPassword("202104");

        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        return dataSource;
    }
}
```

#jsp 파일 템플릿 
```jsp

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!--jsp에서 자바 코드를 안쓰는 법 (EL,JSTL) -->
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

</body>
</html>

```
