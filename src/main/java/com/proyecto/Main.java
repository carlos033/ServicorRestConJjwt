package com.proyecto;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.proyecto.utiles.PrecargaBD;

@SpringBootApplication(scanBasePackages = {"com.proyecto"})
public class Main {

    @Autowired
    private PrecargaBD myDatabaseSeeder;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    InitializingBean seedDatabase() {
        return myDatabaseSeeder::precargarBaseDeDatos;
    }

}
