package com.simpletouch.auntieannesdelivery.view.view.features;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.simpletouch.auntieannesdelivery.R;
import com.simpletouch.auntieannesdelivery.databinding.FragmentOrdersBinding;
import com.simpletouch.auntieannesdelivery.view.utils.ViewUtils;
import com.simpletouch.business.network.NetworkUtils;
import com.simpletouch.entities.model.Order;

import java.util.ArrayList;
import java.util.List;


public class OrdersFragment extends Fragment {

    FragmentOrdersBinding dataBinding;
    private OrdersViewModel ordersViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_orders, container, false);
        View view = dataBinding.getRoot();

        ordersViewModel = ViewModelProviders.of(this).get(OrdersViewModel.class);
        dataBinding.setOrdersViewModel(ordersViewModel);

        findAllOrdersRequest();
        return view;
    }

    private void findAllOrdersRequest() {
        if (NetworkUtils.isOnline(getContext())) {
            ordersViewModel.setOrders(generateOrderList());
        } else {
            ViewUtils.showToast(getContext(), getString(R.string.no_connection), Toast.LENGTH_SHORT);
        }
    }

    private List<Order> generateOrderList() {
        List<Order> orderList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Order order = new Order();
            order.setOrderNumber(i);
            order.setCustomerHistoryId(20);
            order.setBrandName("Auntie's Anne");
            order.setOrderStatus("Pending");
            order.setOrderDate("27/2/2020");
            order.setOrderNumber(2);
            order.setCurrencyCode("EG");
            order.setOrderTotal(150);

            orderList.add(order);
        }
        return orderList;
    }

    @Override
    public void onResume() {
        findAllOrdersRequest();
        super.onResume();
    }
}
