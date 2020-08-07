package com.number.converter.dto;

//Using a dto instead of an entity since
//we're only passing data from one context to another
public class NumConvertDto {
    //Using camel case instead of snake case everywhere else
    //so just sticking with that convention for the Dto
    private String status;
    private String numInEnglish;

    public NumConvertDto(String status, String numInEnglish) {
        this.status = status;
        this.numInEnglish = numInEnglish;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNumInEnglish() {
        return numInEnglish;
    }

    public void setNumInEnglish(String numInEnglish) {
        this.numInEnglish = numInEnglish;
    }
}
