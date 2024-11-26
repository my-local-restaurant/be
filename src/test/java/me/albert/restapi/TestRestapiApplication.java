package me.albert.restapi;

import org.springframework.boot.SpringApplication;

public class TestRestapiApplication {

    public static void main(String[] args) {
        SpringApplication.from(RestapiApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
