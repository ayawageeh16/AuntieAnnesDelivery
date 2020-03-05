package com.simpletouch.entities.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.google.gson.annotations.SerializedName;
import com.simpletouch.entities.BR;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
public class Order extends BaseObservable implements Parcelable {

    @SerializedName("BrandID")
    private int brandID ;

    @SerializedName("BrandName")
    private String brandName ;

    @SerializedName("Logo")
    private String logo ;

    @SerializedName("CurrencyCode")
    private String currencyCode;

    @SerializedName("CustomerHistoryId")
    private int customerHistoryId ;

    @SerializedName("OrderNumber")
    private int orderNumber ;

    @SerializedName("Rating")
    private float rating ;

    @SerializedName("OrderTotal")
    private double orderTotal ;

    @SerializedName("OrderDate")
    private String orderDate ;

    @SerializedName("IsBrandHasRatingItems")
    private boolean isBrandHasRatingItems ;

    @SerializedName("IsOrderRatedBefore")
    private boolean isOrderRatedBefore ;

    @SerializedName("IsCanceled")
    private boolean isCanceled ;

    @SerializedName("OrderSourceId")
    private int orderSourceId ;

    @SerializedName("OrderSourceName")
    private String orderSourceName ;

    @SerializedName("OrderStatusId")
    private int orderStatusId ;

    @SerializedName("OrderStatus")
    private String orderStatus ;

    @SerializedName("Color")
    private String color ;

    @SerializedName("ColorCode")
    private String colorCode ;

    @SerializedName("IsOrderStatusValidToRate")
    private boolean isOrderStatusValidToRate;

    @SerializedName("IsOrderHasDetails")
    private boolean isOrderHasDetails;

    protected Order(Parcel in) {
        brandID = in.readInt();
        brandName = in.readString();
        logo = in.readString();
        currencyCode = in.readString();
        customerHistoryId = in.readInt();
        orderNumber = in.readInt();
        rating = in.readFloat();
        orderTotal = in.readDouble();
        orderDate = in.readString();
        isBrandHasRatingItems = in.readByte() != 0;
        isOrderRatedBefore = in.readByte() != 0;
        isCanceled = in.readByte() != 0;
        orderSourceId = in.readInt();
        orderSourceName = in.readString();
        orderStatusId = in.readInt();
        orderStatus = in.readString();
        color = in.readString();
        colorCode = in.readString();
        isOrderStatusValidToRate = in.readByte() != 0;
        isOrderHasDetails = in.readByte() != 0;
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(brandID);
        parcel.writeString(brandName);
        parcel.writeString(logo);
        parcel.writeString(currencyCode);
        parcel.writeInt(customerHistoryId);
        parcel.writeInt(orderNumber);
        parcel.writeFloat(rating);
        parcel.writeDouble(orderTotal);
        parcel.writeString(orderDate);
        parcel.writeByte((byte) (isBrandHasRatingItems ? 1 : 0));
        parcel.writeByte((byte) (isOrderRatedBefore ? 1 : 0));
        parcel.writeByte((byte) (isCanceled ? 1 : 0));
        parcel.writeInt(orderSourceId);
        parcel.writeString(orderSourceName);
        parcel.writeInt(orderStatusId);
        parcel.writeString(orderStatus);
        parcel.writeString(color);
        parcel.writeString(colorCode);
        parcel.writeByte((byte) (isOrderStatusValidToRate ? 1 : 0));
        parcel.writeByte((byte) (isOrderHasDetails ? 1 : 0));
    }

    @Bindable
    public int getBrandID() {
        return brandID;
    }

    public void setBrandID(int brandID) {
        this.brandID = brandID;
    }

    @Bindable
    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @Bindable
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Bindable
    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Bindable
    public int getCustomerHistoryId() {
        return customerHistoryId;
    }

    public void setCustomerHistoryId(int customerHistoryId) {
        this.customerHistoryId = customerHistoryId;
    }

    @Bindable
    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Bindable
    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Bindable
    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    @Bindable
    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    @Bindable
    public boolean isBrandHasRatingItems() {
        return isBrandHasRatingItems;
    }

    public void setBrandHasRatingItems(boolean brandHasRatingItems) {
        isBrandHasRatingItems = brandHasRatingItems;
    }

    @Bindable
    public boolean isOrderRatedBefore() {
        return isOrderRatedBefore;
    }

    public void setOrderRatedBefore(boolean orderRatedBefore) {
        isOrderRatedBefore = orderRatedBefore;
    }

    @Bindable
    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    @Bindable
    public int getOrderSourceId() {
        return orderSourceId;
    }

    public void setOrderSourceId(int orderSourceId) {
        this.orderSourceId = orderSourceId;
    }

    @Bindable
    public String getOrderSourceName() {
        return orderSourceName;
    }

    public void setOrderSourceName(String orderSourceName) {
        this.orderSourceName = orderSourceName;
    }

    @Bindable
    public int getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(int orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    @Bindable
    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Bindable
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Bindable
    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    @Bindable
    public boolean isOrderStatusValidToRate() {
        return isOrderStatusValidToRate;
    }

    public void setOrderStatusValidToRate(boolean orderStatusValidToRate) {
        isOrderStatusValidToRate = orderStatusValidToRate;
    }

    @Bindable
    public boolean isOrderHasDetails() {
        return isOrderHasDetails;
    }

    public void setOrderHasDetails(boolean orderHasDetails) {
        isOrderHasDetails = orderHasDetails;
    }
}
