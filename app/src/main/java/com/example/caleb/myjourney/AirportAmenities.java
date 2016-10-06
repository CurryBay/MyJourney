package com.example.caleb.myjourney;

/**
 * Created by tinghaong on 6/10/16.
 */

public class AirportAmenities {
    private String amenitiesName;
    private int imageId;

    public AirportAmenities(String amenity, int imageInfo) {
        amenitiesName = amenity;
        imageId = imageInfo;
    }

    public String getAmenitiesName() {
        return amenitiesName;
    }

    public int getImageId() {
        return imageId;
    }
}
