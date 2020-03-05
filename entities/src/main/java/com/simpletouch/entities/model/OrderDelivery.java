package com.simpletouch.entities.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderDelivery {

    private String firebaseId;
    private long orderId;
    private long deliveryManId;
    private double latitude;
    private double longitude;
}
