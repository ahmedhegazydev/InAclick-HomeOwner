package com.example.hp.in_a_click.test_pay;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.braintreepayments.api.BraintreeFragment;

/**
 * Created by ahmed on 21/12/17.
 */

public class MainActivityTestBrainTree extends AppCompatActivity {

    BraintreeFragment braintree = null;
    String tokenizationKey = "";
    LinearLayout linearLayout = null;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button btnStartPayment = new Button(getApplicationContext());
        btnStartPayment.setText("Start Payment");
        btnStartPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setLayoutParams(new LinearLayout.
                LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        linearLayout.addView(btnStartPayment);

        setContentView(linearLayout);

        init();


    }


    public void init() {
//        DropInRequest dropInRequest = new DropInRequest()
//                .clientToken(clientToken);
//        startActivityForResult(dropInRequest.getIntent(this), REQUEST_CODE);
    }

}
