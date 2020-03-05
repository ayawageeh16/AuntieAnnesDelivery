package com.simpletouch.auntieannesdelivery.view.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.Toast;

import com.simpletouch.auntieannesdelivery.R;
import com.simpletouch.auntieannesdelivery.databinding.ActivityMainBinding;
import com.simpletouch.auntieannesdelivery.view.utils.ViewUtils;
import com.simpletouch.auntieannesdelivery.view.view.features.OrdersFragment;
import com.simpletouch.business.network.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding dataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new OrdersFragment())
                .addToBackStack(null)
                .commit();
    }


}
