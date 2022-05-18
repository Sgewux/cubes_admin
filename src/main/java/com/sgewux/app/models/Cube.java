package com.sgewux.app.models;

import com.google.gson.annotations.SerializedName;
import com.sgewux.app.models.enums.Categories;
import com.sgewux.app.models.enums.Difficulties;
import com.sgewux.app.models.exceptions.ShortReviewLengthException;
import com.sgewux.app.models.exceptions.UnsetFieldsException;

public final class Cube {
    @SerializedName(value = "Category")
    private   String category;

    @SerializedName(value = "Brand")
    private   String brandName;

    @SerializedName(value = "NumOfPieces")
    private   Integer numOfPieces;

    @SerializedName(value = "Difficulty")
    private   String difficulty;

    @SerializedName(value = "Review")
    private   String review;

    @SerializedName(value = "Price")
    private  Float price;

    @SerializedName(value = "Name")
    private   String name;

    @SerializedName(value = "SN")
    private   String serialNumber;

    public Cube(String category, String brandName, Integer numOfPieces, String difficulty, String review,
            Float price, String name, String serialNumber) {
        this.category = category;
        this.brandName = brandName;
        this.numOfPieces = numOfPieces;
        this.difficulty = difficulty;
        this.review = review;
        this.price = price;
        this.name = name;
        this.serialNumber = serialNumber;
    }

    

    public Cube(String category, String brandName, Integer numOfPieces, String difficulty, String review, Float price,
            String name) {
        this.category = category;
        this.brandName = brandName;
        this.numOfPieces = numOfPieces;
        this.difficulty = difficulty;
        this.review = review;
        this.price = price;
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{ \n").
        append("Name: ").append(this.name).append("\n").
        append( this.serialNumber != null ? "SN: " + this.serialNumber + "\n" : "").
        append("Brand: ").append(this.brandName).append("\n").
        append("Category: ").append(this.category).append("\n").
        append("Difficulty: ").append(this.difficulty).append("\n").
        append("Number of pieces: ").append(this.numOfPieces != null ? this.numOfPieces : "Not provided").append("\n").
        append("Price: ").append(this.price != null ? this.price : "Not provided").append("\n").
        append("Review: ").append(this.review).append("\n}");

        return stringBuilder.toString();

    }

    public static class CubeBuilder {
        
        private String category;
        private String brandName;
        private Integer numOfPieces;
        private String difficulty;
        private String review;
        private Float price;
        private String name;

        public CubeBuilder(){}
        
        public CubeBuilder(Cube other){
            this.category = other.category;
            this.brandName = other.brandName;
            this.numOfPieces = other.numOfPieces;
            this.difficulty = other.difficulty;
            this.review = other.review;
            this.price = other.price;
            this.name = other.name;
        }

        public Cube build(){
            if ((category != null) && (brandName != null) && (difficulty != null) && (review != null) && (name != null)) {
                // All fields must be not null except numOfPieces and price. Those could be null.
                return new Cube(category, brandName, numOfPieces, difficulty, review, price, name);
            } else {
                throw new UnsetFieldsException();
            }
        }

        public void clearValues(){
            // This method will set all the fields to null again
            this.category = null;
            this.brandName = null;
            this.numOfPieces = null;
            this.difficulty = null;
            this.review = null;
            this.price = null;
            this.name = null;
        }

        public CubeBuilder withCategory(Categories category){
            this.category = category.getStrValue();
            return this;
        }

        public CubeBuilder withDifficulty(Difficulties difficulty){
            this.difficulty = difficulty.getStrValue();
            return this;
        }

        public CubeBuilder withBrandName(String brandName){
            this.brandName = brandName;
            return this;
        }

        public CubeBuilder withNumOfPieces(Integer numOfPieces){
            this.numOfPieces = numOfPieces;
            return this;
        }

        public CubeBuilder withReview(String review){
            if (review.length() >= 15) {
                this.review = review;
                return this;
            } else {
                throw new ShortReviewLengthException();
            }
        }
        
        public CubeBuilder withPrice(Float price){
            this.price = price;
            return this;
        }

        public CubeBuilder withName(String name){
            this.name = name;
            return this;
        }

    }

}