package com.example.m117.represent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by M117 on 3/10/16.
 */
public class billsAdapter extends BaseAdapter {
    Context context;
    String[] bill;
    String[] dates;

    private static LayoutInflater inflater = null;

    public billsAdapter(Context context, String[] bill, String[] dates) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.bill = bill;
        this.dates = dates;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return bill.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return bill[position];
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }


    @Override
    //[name, party, email, website, image,tweet]

    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.bdetails, null);
        TextView name = (TextView) vi.findViewById(R.id.bill);
        name.setText("Bill ID: " + bill[position]);
        TextView date = (TextView) vi.findViewById(R.id.date);
        date.setText("Date Introduced: "+dates[position]);

        return vi;
    }
}
