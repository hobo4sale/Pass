package com.boyz.rho.pass.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.boyz.rho.pass.R;
import com.boyz.rho.pass.Utils.PassDataSource;
import com.boyz.rho.pass.Utils.PasswordGeneratorHelper;

/**
 * Created by rho on 11/30/16.
 */

public class EditSiteActivity extends Activity implements View.OnClickListener {

    private EditText siteText;
    private EditText userText;
    private EditText passText;
    private PassDataSource dataSource;
    private PasswordGeneratorHelper genHelper = new PasswordGeneratorHelper();

    private String sitename;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_site);
        Bundle bundle = getIntent().getExtras();

        siteText = (EditText) findViewById(R.id.edit_site);
        userText = (EditText) findViewById(R.id.edit_user);
        passText = (EditText) findViewById(R.id.edit_password);

        sitename = bundle.getString("site");
        username = bundle.getString("username");
        password = bundle.getString("password");

        Button submitButton = (Button) findViewById(R.id.submit_button);
        submitButton.setOnClickListener(this);

        Button generateButton = (Button) findViewById(R.id.generate_button);
        generateButton.setOnClickListener(this);

        dataSource = new PassDataSource(this);
        try {
            dataSource.open(getIntent().getExtras().getString("databasePassword", null));
        }
        catch (Exception e) {
            Toast.makeText(this, "Error opening database", Toast.LENGTH_SHORT).show();
        }
        fillWidgets();
    }

    private void fillWidgets() {
        siteText.setText(sitename);
        userText.setText(username);
        passText.setText(password);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.submit_button:
                dataSource.updateSite(siteText.getText().toString(), userText.getText().toString(),
                        passText.getText().toString());
                finish();
                break;
            case R.id.generate_button:
                char[] gen = genHelper.getPassword(true, true, true, true);
                passText.setText(gen, 0, gen.length);
                break;
        }
    }
}
