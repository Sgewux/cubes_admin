package com.sgewux.app.models.enums;

public enum Categories {
    SPEED("speed"), COLLECTION("collection");
    private String strValue;

    private Categories(String strValue){
        this.strValue = strValue;
    }

    public String getStrValue(){
        return this.strValue;
    }
}
