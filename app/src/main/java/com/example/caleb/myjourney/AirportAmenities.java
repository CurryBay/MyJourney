package com.example.caleb.myjourney;

/**
 * Created by tinghaong on 6/10/16.
 */

public class AirportAmenities {
    private String amenitiesName;
    private int imageId;
    private String shopId;

    public AirportAmenities(String amenity, String shopInfo, int imageInfo) {
        shopId = shopInfo;
        amenitiesName = amenity;
        imageId = imageInfo;
    }

    public String getAmenitiesName() {
        return amenitiesName;
    }

    public int getImageId() {
        return imageId;
    }

    public String getShopId() { return shopId; }
}
