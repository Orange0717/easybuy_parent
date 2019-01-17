package cn.itsource.easybuy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = "cn.itsource.easybuy.mapper")
@EnableFeignClients(basePackages = "cn.itsource.easybuy.server") //如果包的命名规范一致，可以不加，最好加上
public class ProductServerApplication_8002 {

    public static void main(String[] args) {
        SpringApplication.run(ProductServerApplication_8002.class);
    }
}
