package com.sgewux.app;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.google.gson.Gson;
import com.sgewux.app.client.CubeClient;
import com.sgewux.app.models.Cube;
import com.sgewux.app.models.Cube.CubeBuilder;
import com.sgewux.app.models.enums.Categories;
import com.sgewux.app.models.enums.Difficulties;
import com.sgewux.app.models.exceptions.ShortReviewLengthException;
import com.sgewux.app.models.exceptions.UnsetFieldsException;

import feign.Feign;
import feign.gson.GsonDecoder;

public class App {
    public static void main(String[] args) {
        // CubeClient cubeClient = Feign.builder().
        //                         decoder(new GsonDecoder()).
        //                         target(CubeClient.class, "http://127.0.0.1:8000");
        
        // cubeClient.getAllCubes().forEach(c -> System.out.println(c));
        
        // Cube newCube = new Cube.CubeBuilder().
        //                    withBrandName("QiYi").
        //                    withCategory(Categories.COLLECTION).
        //                    withDifficulty(Difficulties.NOT_TOO_EASY).
        //                    withName("Whatever").
        //                    withPrice(12.5f).
        //                    withReview("Very smooth cube, i really reccommend it!!").
        //                    build();

        // String json = new Gson().toJson(newCube);
        
        // cubeClient.addNewCube(json);

        // System.out.println("new cube added!");
    
        // cubeClient.getAllCubes().forEach(c -> System.out.println(c));

    // Cube cube = cubeClient.getCubeBySn("e415f64e579911ecaf2c2c44fdacceea");
    // System.out.println("lmao");
    // System.out.println(cube);

    Cube c = buildCube();
    System.out.println(c);
    System.out.println("=".repeat(10));
    System.out.println(new Gson().toJson(c));

    }

    private static Cube buildCube(){
        CubeBuilder builder = new Cube.CubeBuilder();
        Cube cube;
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                try {
                    System.out.println("Cube name: ");
                    String name = sc.nextLine();
                    builder.withName(name.isBlank() ? null : name);
        
                    System.out.println("Cube brand: ");
                    String brand = sc.nextLine();
                    builder.withBrandName(brand.isBlank() ? null : brand);
        
                    System.out.println("Cube price: ");
                    String strPrice = sc.nextLine();
                    builder.withPrice(strPrice.isBlank() ? null : Float.valueOf(strPrice));


                    System.out.println("Number of pieces: ");
                    String strNumOfPieces = sc.nextLine();
                    builder.withNumOfPieces(strNumOfPieces.isBlank() ? null : Integer.valueOf(strNumOfPieces));

                    System.out.println("Category. S = speed | C = collection. Write just the letter");
                    switch (sc.nextLine().toUpperCase()) {
                        case "S":
                            builder.withCategory(Categories.SPEED);
                            break;
                        case "C":
                            builder.withCategory(Categories.COLLECTION);
                            break;
                        default:
                            System.err.println(
                                "You wrote a unavailable category. Ignoring that value and setting category as not provided."
                                );
                            break;
                    }

                    System.out.println("Difficulty. E = easy | N = not too easy | H = hard. Write just the letter");
                    switch (sc.nextLine().toUpperCase()) {
                        case "E":
                            builder.withDifficulty(Difficulties.EASY);
                            break;
                        case "N":
                            builder.withDifficulty(Difficulties.NOT_TOO_EASY);
                            break;
                        case "H":
                            builder.withDifficulty(Difficulties.HARD);
                            break;
                        default:
                            System.err.println(
                                "You wrote unavailable difficulty. Ignoring that value and setting difficulty as not provided"
                            );
                            break;
                    }

                    System.out.println("Review: ");
                    String review = sc.nextLine();
                    builder.withReview(review);

                    cube = builder.build();
                    break;

                } catch (UnsetFieldsException | ShortReviewLengthException e) {
                    builder.clearValues();
                    System.err.println(e.getMessage());
                    System.err.println("Starting building process again...");
                
                } catch (NumberFormatException e) {
                    builder.clearValues();
                    System.err.println(
                        "Type casting failed. " + 
                        e.getMessage() + 
                        " Please send the correct type for that field.");
                    System.err.println("Starting building process again...");
                }

            }
        }

        return cube;
    }

    private static Cube buildCube(Cube other){
        CubeBuilder builder = new Cube.CubeBuilder(other);
        Cube cube;
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Leave the fields blank to use the value from the other cube");

            System.out.println("Name: ");
            String name = sc.nextLine();
            if (!name.isBlank()) {
                builder.withName(name);
            }

            System.out.println("Cube brand: ");
            String brand = sc.nextLine();
            if (!brand.isBlank()) {
                builder.withBrandName(brand);
            }

            System.out.println("Cube price: ");
            String strPrice = sc.nextLine();
            if (!brand.isBlank()) {
                builder.withPrice(Float.valueOf(strPrice));
            }


            System.out.println("Number of pieces: ");
            String strNumOfPieces = sc.nextLine();
            if (!brand.isBlank()) {
                builder.withNumOfPieces(Integer.valueOf(strNumOfPieces));
            }

            System.out.println("Category. S = speed | C = collection. Write just the letter");
            switch (sc.nextLine().toUpperCase()) {
                case "S":
                    builder.withCategory(Categories.SPEED);
                    break;
                case "C":
                    builder.withCategory(Categories.COLLECTION);
                    break;
                default:
                    System.err.println(
                        "You wrote a unavailable category. Ignoring that value and letting old category."
                        );
                    break;
            }

            System.out.println("Difficulty. E = easy | N = not too easy | H = hard. Write just the letter");
            switch (sc.nextLine().toUpperCase()) {
                case "E":
                    builder.withDifficulty(Difficulties.EASY);
                    break;
                case "N":
                    builder.withDifficulty(Difficulties.NOT_TOO_EASY);
                    break;
                case "H":
                    builder.withDifficulty(Difficulties.HARD);
                    break;
                default:
                    System.err.println(
                        "You wrote unavailable difficulty. Ignoring that value and letting old category."
                    );
                    break;
            }

            System.out.println("Review: ");
            String review = sc.nextLine();
            if (!review.isBlank()) {
                builder.withReview(review);
            }

            cube = builder.build();
        }
        
        return cube;
    }
}
