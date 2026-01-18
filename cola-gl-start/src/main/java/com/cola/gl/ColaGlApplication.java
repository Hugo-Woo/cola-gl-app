package com.cola.gl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = { "com.cola.gl", "com.alibaba.cola" })
@EntityScan("com.cola.gl.infrastructure.db")
@EnableJpaRepositories("com.cola.gl.infrastructure")
public class ColaGlApplication {

    public static void main(String[] args) {
        SpringApplication.run(ColaGlApplication.class, args);
    }
}
