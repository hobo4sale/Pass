package com.boyz.rho.pass.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.boyz.rho.pass.Activities.MainActivity;
import com.boyz.rho.pass.R;

import java.util.ArrayList;

/**
 * Created by rho on 11/13/16.
 */

public class ListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> sites;
    private ArrayList<String> usernames;
    private LayoutInflater inflater;


    public ListAdapter(MainActivity mainActivity, ArrayList<String> sites, ArrayList<String> usernames) {
        context = mainActivity;
        this.sites = sites;
        this.usernames = usernames;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public int getCount() {
        return sites.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = inflater.inflate(R.layout.login_list_item, null);
        TextView siteText = (TextView) v.findViewById(R.id.sitename);
        TextView userText = (TextView) v.findViewById(R.id.username);

        siteText.setText(sites.get(i));
        userText.setText(usernames.get(i));

        return v;
    }
}
