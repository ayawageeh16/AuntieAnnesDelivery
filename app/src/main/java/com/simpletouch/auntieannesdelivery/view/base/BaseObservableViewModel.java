package com.simpletouch.auntieannesdelivery.view.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.AndroidViewModel;

import java.beans.PropertyChangeListener;

public class BaseObservableViewModel extends AndroidViewModel implements Observable {


    private PropertyChangeRegistry mCalbacks;

    public BaseObservableViewModel(@NonNull Application application) {
        super(application);
        mCalbacks = new PropertyChangeRegistry();
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
       mCalbacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        mCalbacks.remove(callback);
    }

    public void notifyChange(){
        mCalbacks.notifyChange(this, 0);
    }

    public void notifyChange(int viewId){
        mCalbacks.notifyChange(this, viewId);
    }
}
