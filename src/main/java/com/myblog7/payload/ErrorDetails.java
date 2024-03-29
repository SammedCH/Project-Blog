package com.myblog7.payload;

import java.util.Date;

public class ErrorDetails {
    private Date timestamp;
    private String message;

    private String details;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessege(String messege) {
        this.message = messege;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public ErrorDetails(Date timestamp, String messege, String details) {
        this.timestamp = timestamp;
        this.message = messege;
        this.details = details;
    }


}
