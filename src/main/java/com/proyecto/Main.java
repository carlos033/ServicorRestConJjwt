package com.proyecto;

import com.proyecto.utiles.PrecargaBD;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = {"com.proyecto"})
public class Main {

    @Autowired
    private PrecargaBD myDatabaseSeeder;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

//    @Bean
//    InitializingBean seedDatabase() {
//        return () -> {
//            myDatabaseSeeder.precargarBaseDeDatos();
//        };
//    }

}
