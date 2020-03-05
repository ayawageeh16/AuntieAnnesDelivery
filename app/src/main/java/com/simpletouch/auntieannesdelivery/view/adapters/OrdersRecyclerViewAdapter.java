package com.simpletouch.auntieannesdelivery.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.simpletouch.auntieannesdelivery.R;
import com.simpletouch.auntieannesdelivery.databinding.OrderCardItemBinding;
import com.simpletouch.auntieannesdelivery.view.listener.OrderRecyclerViewListener;
import com.simpletouch.auntieannesdelivery.view.view.features.OrderDetailsActivity;
import com.simpletouch.entities.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrdersRecyclerViewAdapter extends RecyclerView.Adapter<OrdersRecyclerViewAdapter.OrdersViewHolder> {

    private String ORDER_OBJECT = "ORDER_OBJECT";

    private List<Order> orderList = new ArrayList<>();
    private Context context;

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        OrderCardItemBinding dataBindind = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.order_card_item,
                parent,
                false);


        return new OrdersViewHolder(dataBindind);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {
        holder.bind(orderList.get(position));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }



    class OrdersViewHolder extends RecyclerView.ViewHolder implements OrderRecyclerViewListener {

        OrderCardItemBinding orderCardItemBinding;

        public OrdersViewHolder(@NonNull OrderCardItemBinding orderCardItemBinding) {
            super(orderCardItemBinding.getRoot());
            this.orderCardItemBinding = orderCardItemBinding;
            orderCardItemBinding.setListener(this);

        }

        public void bind(Object obj) {
            orderCardItemBinding.setVariable(BR.order, obj);
            orderCardItemBinding.executePendingBindings();
        }

        @Override
        public void onOrderCardClicked(Order order) {
            Intent intent = new Intent(context,  OrderDetailsActivity.class);
            intent.putExtra(ORDER_OBJECT, order);
            context.startActivity(intent);
        }
    }

}
