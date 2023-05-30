package com.group08.onlineShop.dto.responseDTO;

public class StripeResponse {
    private boolean status;
    private String details;

    public StripeResponse() {
        super();
        this.status = true;
    }

    public StripeResponse(boolean status, String details) {
        super();
        this.status = status;
        this.details = details;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "StripeResponse [status=" + status + ", details=" + details + "]";
    }
}
