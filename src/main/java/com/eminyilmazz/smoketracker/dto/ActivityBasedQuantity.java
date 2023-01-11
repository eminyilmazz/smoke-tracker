package com.eminyilmazz.smoketracker.dto;


import com.eminyilmazz.smoketracker.enums.Activity;

public class ActivityBasedQuantity {
    private Activity activity;
    private Long quantity;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public ActivityBasedQuantity(Activity activity, Long quantity) {
        this.activity = activity;
        this.quantity = quantity;
    }
}
