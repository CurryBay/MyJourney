package com.example.caleb.myjourney;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


public class Flight implements Serializable {
    private String airportCode, airlineCode, flightNumber, flightDate, airportCode2, statusText, scheduled, terminal, city;
    private int duration;
    // not sure if these information are needed:
    // private String status, estimated, cityCode;


    public Flight(JSONObject flightInfo) {
        try {
            airportCode = flightInfo.getString("airportCode");
            airlineCode = flightInfo.getString("airlineCode");
            flightNumber = flightInfo.getString("flightNumber");
            flightDate = flightInfo.getString("flightDate");

            JSONArray flightRecord = flightInfo.getJSONArray("flightRecord");
            JSONObject c = flightRecord.getJSONObject(0);
            // status = flightInfo.getString("status");
            airportCode2 = c.getString("airportCode");
            statusText = c.getString("statusText");
            scheduled = c.getString("scheduled");
            terminal = c.getString("terminal");
            city = c.getString("city");
            duration = c.getInt("duration");

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getScheduled() {
        return scheduled;
    }

    public void setScheduled(String scheduled) {
        this.scheduled = scheduled;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    public String getAirportCode2() {
        return airportCode2;
    }

    public void setAirportCode2(String airportCode2) {
        this.airportCode2 = airportCode2;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    /* public String getCityCode() {
        return cityCode;
    }

    public String getStatus() {
            return status;
        }
    public String getEstimated() {
        return estimated;
    } */
}
