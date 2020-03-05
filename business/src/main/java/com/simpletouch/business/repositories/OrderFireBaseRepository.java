package com.simpletouch.business.repositories;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.simpletouch.entities.model.OrderDelivery;

public class OrderFireBaseRepository {

    private DatabaseReference firebaseDataBaseReference;
    private String orderNodeId;

    public OrderFireBaseRepository() {
        if (firebaseDataBaseReference == null) {
            firebaseDataBaseReference = FirebaseDatabase.getInstance().getReference("shareLocation");
        }
    }

    /**
     * Insert New order Node To Firebase Database
     *
     * @param orderDelivery
     */
    public void addNewNodeToFirebaseDatabase(OrderDelivery orderDelivery) {
        String newNodeId = firebaseDataBaseReference.push().getKey();
        this.orderNodeId = newNodeId;
        orderDelivery.setFirebaseId(newNodeId);

        firebaseDataBaseReference.child("latitude").setValue(String.valueOf(orderDelivery.getLatitude()))
                .addOnSuccessListener(aVoid -> Log.d("addNewFirebaseDatabase", "onSuccess"));

        firebaseDataBaseReference.child("longitude").setValue(String.valueOf(orderDelivery.getLongitude()))
                .addOnSuccessListener(aVoid -> Log.d("addNewFirebaseDatabase", "onSuccess"));

    }

    /**
     * Update Order Node With New Latitude And Longitude
     *
     * @param orderDelivery
     */
    public void updateNodeInFirebaseDatabase(OrderDelivery orderDelivery) {
        DatabaseReference nodeReference = firebaseDataBaseReference.child("latitude");
        nodeReference.setValue(100.15121);
    }

    /**
     * Delete Order Node From Firebase Database
     */
    public void deleteNodeFromFirebaseDatabase() {
        DatabaseReference nodeReference = firebaseDataBaseReference.child(orderNodeId);
        nodeReference.removeValue();
    }
}
