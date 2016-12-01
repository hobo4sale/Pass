package com.boyz.rho.pass.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.boyz.rho.pass.R;
import com.boyz.rho.pass.Utils.PassDataSource;
import com.boyz.rho.pass.Utils.PasswordGeneratorHelper;

/**
 * Created by rho on 11/13/16.
 */

public class AddSiteActivity extends Activity implements View.OnClickListener{

    private EditText siteText;
    private EditText userText;
    private EditText passText;

    private CheckBox lowerCheck;
    private CheckBox upperCheck;
    private CheckBox numberCheck;
    private CheckBox specialCheck;

    private PassDataSource dataSource;
    private PasswordGeneratorHelper genHelper = new PasswordGeneratorHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_site);

        siteText = (EditText) findViewById(R.id.edit_site);
        userText = (EditText) findViewById(R.id.edit_user);
        passText = (EditText) findViewById(R.id.edit_password);

        lowerCheck = (CheckBox) findViewById(R.id.lowerCheck);
        upperCheck = (CheckBox) findViewById(R.id.upperCheck);
        numberCheck = (CheckBox) findViewById(R.id.numberCheck);
        specialCheck = (CheckBox) findViewById(R.id.specialCheck);

        Button submitButton = (Button) findViewById(R.id.submit_button);
        submitButton.setOnClickListener(this);

        Button generateButton = (Button) findViewById(R.id.generate_button);
        generateButton.setOnClickListener(this);

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
            case R.id.submit_button:
                String site = siteText.getText().toString();
                String username = userText.getText().toString();
                String password = passText.getText().toString();
                dataSource.addPassword(site, username, password);
                finish();
                break;
            case R.id.generate_button:
                char[] gen = genHelper.getPassword(upperCheck.isChecked(), lowerCheck.isChecked(),
                        numberCheck.isChecked(), specialCheck.isChecked());
                passText.setText(gen, 0, gen.length);
                break;
        }
    }
}
