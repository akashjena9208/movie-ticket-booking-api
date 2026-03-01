package com.akash.moviebooking.api.enums;

public enum Certificate {

    U("Universal"), UA("Parental Guidance"), A("Adults Only");

    private final String description;

    Certificate(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}