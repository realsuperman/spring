package hello.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
		/*
		 원래는 AnnotationConfigApplicationContext를 기반으로 어플리케이션은 구동하지만
		 그레들에 웹 라이브러리 추가시 AnnotationConfigSevletWebServerApplicationContext로 구동함
		 */
	}

}
