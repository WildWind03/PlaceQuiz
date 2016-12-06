package com.nsu.alexander.countryquiz;

import java.util.logging.Logger;

public class Place {
    private static final Logger logger = Logger.getLogger(Place.class.getName());
    private final double latitude;
    private final double gratitude;
    private final String rightPlace;
    private final String wrongPlace;
    private final String placeDescription;

    public Place(double latitude, double gratitude, String rightPlace, String wrongPlace, String placeDescriotion) {
        this.latitude = latitude;
        this.gratitude = gratitude;
        this.rightPlace = rightPlace;
        this.wrongPlace = wrongPlace;
        this.placeDescription = placeDescriotion;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getGratitude() {
        return gratitude;
    }

    public String getRightPlace() {
        return rightPlace;
    }

    public String getWrongPlace() {
        return wrongPlace;
    }

    public String getPlaceDescription() {
        return placeDescription;
    }
}
