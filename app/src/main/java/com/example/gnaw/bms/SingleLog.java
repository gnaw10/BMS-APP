package com.example.gnaw.bms;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by gnaw on 2016/11/2.
 */

public class SingleLog extends Activity{
    private TextView logTitle,logBody;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singlelog);
        logBody=(TextView)findViewById(R.id.singleLogBody);
        logTitle=(TextView)findViewById(R.id.singleLogTitle);
        Bundle singleLogBundle= getIntent().getExtras();
        logBody.setText(singleLogBundle.getString("logListBody"));
        logTitle.setText(singleLogBundle.getString("logListTitle"));

    }
}
