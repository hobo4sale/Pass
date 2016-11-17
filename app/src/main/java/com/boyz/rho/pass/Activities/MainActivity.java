package com.boyz.rho.pass.Activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.boyz.rho.pass.Fragments.PopupFragment;
import com.boyz.rho.pass.R;
import com.boyz.rho.pass.Utils.DialogCloseListener;
import com.boyz.rho.pass.Utils.ListAdapter;
import com.boyz.rho.pass.Utils.Login;
import com.boyz.rho.pass.Utils.PassDataSource;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener,
                AdapterView.OnItemClickListener, DialogCloseListener{

    private ListView listView;
    private ArrayList<Login> list;
    private PassDataSource dataSource;
    private String password;
    private ListAdapter adapter;
    private ClipboardManager clipoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        password = getIntent().getExtras().getString("password");
        SQLiteDatabase.loadLibs(this);
        dataSource = new PassDataSource(this);
        try {
            dataSource.open(password);
        }
        catch (Exception e) {

        }

        clipoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

    }

    @Override
    protected void onResume() {
        super.onResume();

        list = dataSource.getAllPasswords();

        adapter = new ListAdapter(this, list);
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        int id  = view.getId();
        switch(id) {
            case R.id.fab:
                Intent intent = new Intent(this, AddSiteActivity.class);
                intent.putExtra("password", password);
                startActivityForResult(intent, RESULT_OK);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Login login = list.get(i);
        Bundle bundle = new Bundle();
        bundle.putString("site", login.getSite());
        bundle.putString("username", login.getUsername());
        bundle.putString("password", login.getPassword());
        bundle.putString("databasePassword", password);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        PopupFragment newFragment = new PopupFragment();
        newFragment.setArguments(bundle);
        newFragment.show(ft, "dialog");

    }

    @Override
    public void handleDialogClose(boolean delete) {
        if (delete) {
            list = dataSource.getAllPasswords();
        }
        adapter = new ListAdapter(this, list);
        listView.setAdapter(adapter);
    }
}
