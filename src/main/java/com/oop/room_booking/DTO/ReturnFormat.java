package com.oop.room_booking.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReturnFormat {
    @JsonProperty("Error")
    private String Error;
    public ReturnFormat(String Error) {
        this.Error = Error;
    }
}
