package com.example.Neighborhood_Walk;

import org.json.JSONException;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.io.IOException;
import java.text.ParseException;

@SpringBootApplication
@MapperScan("com.example.Neighborhood_Walk.Mapper")
public class NeighborhoodWalk {

    public static void main(String[] args) throws JSONException, ParseException, IOException {

        SpringApplication.run(NeighborhoodWalk.class, args);
    }

}
