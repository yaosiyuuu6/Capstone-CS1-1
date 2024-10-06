package com.example.Neighborhood_Walk;


import org.json.JSONException;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableScheduling;


import java.io.IOException;
import java.text.ParseException;

@SpringBootApplication
@MapperScan("com.example.Neighborhood_Walk.Mapper")
@EnableScheduling
public class NeighborhoodWalk {


    public static void main(String[] args) throws JSONException, ParseException, IOException {

        SpringApplication.run(NeighborhoodWalk.class, args);
    }


}
