package api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"dao", "api", "service"})
@MapperScan(basePackages = "dao")
public class StartMain  {

    public static void main(String[] args) {
        SpringApplication.run(StartMain.class, args);
    }
}
