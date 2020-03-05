package com.simpletouch.auntieannesdelivery.view.utils;

import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.material.textfield.TextInputLayout;
import com.simpletouch.auntieannesdelivery.view.adapters.OrdersRecyclerViewAdapter;
import com.simpletouch.entities.model.Order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class DataBindingAdapter {


    /**
     * This Method Handle TextInputLayout Error Visibility,
     * If There Is And Error It Set The Error Enabled Property With True,
     * Else It Set It With False And TextInputLayout Returns To It's Original State
     *
     * @param textInputLayout
     * @param isEnabled
     */
    @BindingAdapter("isErrorEnabled")
    public static void setTextInputLayoutErrorEnabled(TextInputLayout textInputLayout, Boolean isEnabled) {
        if (isEnabled != null) {
            textInputLayout.setErrorEnabled(isEnabled);

            textInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (!textInputLayout.getEditText().getText().toString().isEmpty()) {
                        textInputLayout.setErrorEnabled(false);
                    }
                }
            });
        } else {
            textInputLayout.setErrorEnabled(false);
        }
    }

    /**
     * This Method Finds The Returned ErrorMessage Id In The String File And Set It As String To The TextInputLayout
     *
     * @param textInputLayoutError
     * @param errorMessage
     */
    @BindingAdapter("errorText")
    public static void setTextInputLayoutError(TextInputLayout textInputLayoutError, int errorMessage) {
        if (errorMessage != 0) {
            textInputLayoutError.setError(textInputLayoutError.getContext().getString(errorMessage));
        }
    }


    /**
     *
     * This Method Displays The String URL Into The ImageView Using Glide, If Founded Else It Displays The PlaceHolder Image From The Drawable File,
     * And It Support Image Caching
     *
     * @param view
     * @param imageURL
     */
    @BindingAdapter(value = {"imageUrl", "placeholder"}, requireAll = false)
    public static void setImageUsingGlide(final ImageView view, String imageURL, Drawable placeHolder) {
        if (imageURL != null) {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(placeHolder)
                    .error(placeHolder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .signature(new ObjectKey(imageURL));

            Glide.with(view.getContext()).setDefaultRequestOptions(requestOptions).load(imageURL).into(view);
        }
    }


    /**
     * This Method Converts Date And Time Of String To 'yyyy-MM-dd'T'hh:mm:ss' Format And Set It To the TextView
     *
     * @param view
     * @param dateTime
     */
    @BindingAdapter("setDateFormat")
    public static void setDateFormat(TextView view, String dateTime) {
        Locale current = view.getContext().getResources().getConfiguration().locale;
        Date date = null;
        try {
            SimpleDateFormat df =
                    new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.ENGLISH);
            df.setTimeZone(TimeZone.getTimeZone(" "));
            date = df.parse(dateTime);
            df.setTimeZone(TimeZone.getDefault());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy - hh:mm", current);
        String dateAfterFormat = formatter.format(date);
        view.setText(dateAfterFormat);

    }


    /**
     * This Method Setup RecyclerView LayoutManager, Adapter, And Adapter List Of Data
     * @param recyclerView
     * @param list
     * @param <T>
     */
    @BindingAdapter("setupRecyclerView")
    public static <T> void setupRecyclerView(RecyclerView recyclerView, List<T> list) {

        // Check if the list of objects is null, if true return
        if (list == null) {
            return;
        }
        // Check if layoutManager of the recyclerView is null, if true set new LayoutManger to the recyclerView
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if(layoutManager == null){
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        }
        // Set recyclerView HasFixedSizes to true so that when changing the contents of the adapter does not change it's height or the width.
        recyclerView.setHasFixedSize(true);

        // Set recyclerView Adapter
        OrdersRecyclerViewAdapter adapter = new OrdersRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOrderList((List<Order>) list);
    }

}
