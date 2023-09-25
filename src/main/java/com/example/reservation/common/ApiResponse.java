package com.example.reservation.common;

import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDate;

@Data
public class ApiResponse {

    private Boolean success;
    private String message;

    public ApiResponse(Boolean success , String message) {
        this.message = message;
        this.success = success;
    }

    public String getTimeStamp() {
        return LocalDate.now().toString();
    }

}
