package com.sgewux.app;

import java.util.Scanner;

import com.google.gson.Gson;
import com.sgewux.app.client.CubeClient;
import com.sgewux.app.models.Cube;
import com.sgewux.app.models.Cube.CubeBuilder;
import com.sgewux.app.models.enums.Categories;
import com.sgewux.app.models.enums.Difficulties;

import feign.Feign;
import feign.gson.GsonDecoder;

public class App {
    public static void main(String[] args) {
        CubeClient cubeClient = Feign.builder().
                                decoder(new GsonDecoder()).
                                target(CubeClient.class, "http://127.0.0.1:8000");
        
        cubeClient.getAllCubes().forEach(c -> System.out.println(c));
        
        Cube newCube = new Cube.CubeBuilder().
                           withBrandName("QiYi").
                           withCategory(Categories.COLLECTION).
                           withDifficulty(Difficulties.NOT_TOO_EASY).
                           withName("Whatever").
                           withPrice(12.5f).
                           withReview("Very smooth cube, i really reccommend it!!").
                           build();

        String json = new Gson().toJson(newCube);
        
        cubeClient.addNewCube(json);

        System.out.println("new cube added!");
    
        cubeClient.getAllCubes().forEach(c -> System.out.println(c));

    // Cube cube = cubeClient.getCubeBySn("e415f64e579911ecaf2c2c44fdacceea");
    // System.out.println("lmao");
    // System.out.println(cube);

    }

    // private static Cube buildCube(){
    //     CubeBuilder builder = new Cube.CubeBuilder();
    //     Scanner sc = new Scanner(System.in);

    //     System.out.println("Cube name: ");
    //     String name = sc.nextLine();
    //     builder.withName(name.isBlank() ? null : name);

    //     System.out.println("Cube brand: ");
    //     String brand = sc.nextLine();
    //     builder.withBrandName(brand.isBlank() ? null : brand);

    //     System.out.println("Cube price: ");
    //     Float price = sc.nextFloat();
    //     builder.withPrice(price);
    // }
}
