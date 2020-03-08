package com.simpletouch.auntieannesdelivery.view.view.features;

import android.app.Application;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.simpletouch.auntieannesdelivery.view.base.BaseObservableViewModel;
import com.simpletouch.business.repositories.OrderFireBaseRepository;
import com.simpletouch.entities.model.Order;
import com.simpletouch.entities.model.OrderDelivery;

import java.util.ArrayList;
import java.util.List;

public class OrdersViewModel extends BaseObservableViewModel {

    public List<Order> ordersList = new ArrayList<>();
    private OrderFireBaseRepository orderFireBaseRepository;

    public OrdersViewModel(@NonNull Application application) {
        super(application);
        orderFireBaseRepository = new OrderFireBaseRepository();
    }


    public void createOrderNodeInFirebase(OrderDelivery orderDelivery) {
        orderFireBaseRepository.addNewNodeToFirebaseDatabase(orderDelivery);
    }

    public void updateCurrentOrderLatAndLongOnFirebase(OrderDelivery orderDelivery) {
        orderFireBaseRepository.updateNodeInFirebaseDatabase(orderDelivery);
    }

    public void deleteCurrentOrderNodeFromFirebase() {
        orderFireBaseRepository.deleteNodeFromFirebaseDatabase();
    }

    @Bindable
    public List<Order> getOrdersList() {
        return ordersList;
    }

    public void setOrders(List<Order> orders) {
        this.ordersList = orders;
        notifyChange(BR.ordersList);
    }

}
