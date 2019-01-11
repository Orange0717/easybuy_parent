package cn.itsource.easybuy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableConfigServer
public class ConfigAerverApplication_1299 {

    public static void main(String[] args) {
        SpringApplication.run(ConfigAerverApplication_1299.class);
    }
}
