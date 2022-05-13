package com.sgewux.app.models.enums;

public enum Difficulties {
    EASY("easy"), NOT_TOO_EASY("not too easy"), HARD("hard");

    private String strValue;

    private Difficulties(String strValue){
        this.strValue = strValue;
    }

    public String getStrValue(){
        return this.strValue;
    }

}
