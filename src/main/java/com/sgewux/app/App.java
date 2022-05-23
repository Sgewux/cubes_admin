package com.sgewux.app;

import java.util.Arrays;
import java.util.List;
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
import feign.FeignException;
import feign.gson.GsonDecoder;

public class App {
    private final static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        CubeClient cubeClient = Feign.builder().
                                decoder(new GsonDecoder()).
                                target(CubeClient.class, "http://127.0.0.1:8000");
        try (sc) {
            System.out.println(
                "Select the option you would like to perform: C) Create R) Read U) Update D) Delete"
                );
            
                switch (sc.nextLine().toUpperCase()) {
                    case "C": {
                        Cube userCube = buildCube();
                        Cube newCube = cubeClient.addNewCube(new Gson().toJson(userCube));
                        System.out.println("The following cube was succesfully created: ");
                        System.out.println(newCube);
                        break;
                    }

                    case "R": {
                        System.out.println("Write the serial number (leave blank to see all cubes)");
                        List<Cube> listOfCubes;
                        String sn = sc.nextLine();

                        if (!sn.isBlank()) {
                            listOfCubes = Arrays.asList(cubeClient.getCubeBySerial(sn));

                        } else {
                            listOfCubes = cubeClient.getAllCubes();
                        }

                        listOfCubes.forEach(c -> System.out.println(c));
                        break;
                    }

                    case "U": {
                        System.out.println("Serial number of te cube you'd like to update: ");
                        String sn = sc.nextLine();
                        
                        if (!sn.isBlank()) {
                            Cube oldCube = cubeClient.getCubeBySerial(sn);
                            String updatedCubeJson = new Gson().toJson(buildCube(oldCube));
                            Cube updatedCube = cubeClient.updateCube(sn, updatedCubeJson);
                            System.out.println("The following cube was succesfully updated: ");
                            System.out.println("=".repeat(5) + " old cube " + "=".repeat(5));
                            System.out.println(oldCube);
                            System.out.println("=".repeat(5) + " updated cube " + "=".repeat(5));
                            System.out.println(updatedCube);

                        } else {
                            System.err.println("You must provide a serial number to achieve an update operation");
                        }
                        break;
                    }
                    case "D": {
                        System.out.println("Serial number of the cube you'd like to delete: ");
                        String sn = sc.nextLine();
                        if (!sn.isBlank()) {
                            cubeClient.deleteCube(sn);
                            System.out.println("Cube sn " + sn + " was sucesfully deleted. ");
                        } else {
                            System.err.println("You must provide a serial number to achieve a delete operation.");
                        }
                        break;
                    }
                    default:
                        System.err.println("You selected unexistent operation.");
                        break;
                }
        } catch (FeignException e ) {
            if (e.getMessage().contains("404")) {
                System.err.println("Unexistent cube!");
            }
        }


    }

    private static Cube buildCube(){
        CubeBuilder builder = new Cube.CubeBuilder();
        Cube cube;
        while (true) {
            try {
                System.out.println("Cube name: ");
                String name = sc.nextLine();
                builder.withName(name.isBlank() ? null : name);
    
                System.out.println("Cubebrand: ");
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
        

        return cube;
    }

    private static Cube buildCube(Cube other){
        CubeBuilder builder = new Cube.CubeBuilder(other);
        Cube cube;
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
        if (!strPrice.isBlank()) {
            builder.withPrice(Float.valueOf(strPrice));
        }


        System.out.println("Number of pieces: ");
        String strNumOfPieces = sc.nextLine();
        if (!strNumOfPieces.isBlank()) {
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
        
        
        return cube;
    }

}
