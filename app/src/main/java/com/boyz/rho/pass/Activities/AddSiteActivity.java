package com.boyz.rho.pass.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.boyz.rho.pass.R;
import com.boyz.rho.pass.Utils.PassDataSource;

/**
 * Created by rho on 11/13/16.
 */

public class AddSiteActivity extends Activity implements View.OnClickListener{

    private EditText siteText;
    private EditText userText;
    private EditText passText;
    private PassDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_site);

        siteText = (EditText) findViewById(R.id.edit_site);
        userText = (EditText) findViewById(R.id.edit_user);
        passText = (EditText) findViewById(R.id.edit_password);

        Button submitButton = (Button) findViewById(R.id.button);
        submitButton.setOnClickListener(this);

        dataSource = new PassDataSource(this);
        try {
            dataSource.open(getIntent().getExtras().getString("password", null));
        }
        catch (Exception e) {
            Toast.makeText(this, "Error opening database", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.button:
                break;
        }
    }
}
