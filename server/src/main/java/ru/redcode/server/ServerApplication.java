package ru.redcode.server;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
//        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
//        dotenv.entries().forEach(
//                dotenvEntry ->  System.setProperty(dotenvEntry.getKey(), dotenvEntry.getValue())
//        );
//
//        log.info("SPRING_DATASOURCE_URL = {}", dotenv.get("SPRING_DATASOURCE_URL"));
//        log.info("SPRING_DATASOURCE_USERNAME = {}", dotenv.get("SPRING_DATASOURCE_USERNAME"));
//        log.info("SPRING_DATASOURCE_PASSWORD = {}", dotenv.get("SPRING_DATASOURCE_PASSWORD"));

        SpringApplication.run(ServerApplication.class, args);
    }

}
