package com.cow8k.stock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cow8k.stock.*.mapper")
public class StockManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockManageApplication.class, args);
    }

}
