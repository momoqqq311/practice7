package com.example.hong.practice6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * Created by hong on 2017-04-13.
 */

public class restaurant_info_adapter extends BaseAdapter {
    ArrayList<restaurant_info> data = new ArrayList<restaurant_info>();
    ArrayList<restaurant_info> data2 = new ArrayList<restaurant_info>();
    Context context;
    boolean checkbox_use;

    restaurant_info_adapter(Context context, ArrayList<restaurant_info> data, boolean checkbox_use) {
        this.context = context;
        this.data = data;
        this.checkbox_use = checkbox_use;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);
        }

        TextView t1 = (TextView) convertView.findViewById(R.id.textview);
        TextView t2 = (TextView) convertView.findViewById(R.id.textview2);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox);
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.get(position).ischecked){
                    data.get(position).ischecked=false;
                }else{
                    data.get(position).ischecked=true;
                }
            }
        });

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        restaurant_info one = data.get(position);
        t1.setText(one.getName());
        t2.setText(one.getTel());
        if (one.getKind().equals("한식")) {
            imageView.setImageResource(R.drawable.bossam);
        } else if (one.getKind().equals("일식")) {
            imageView.setImageResource(R.drawable.susi);
        } else if (one.getKind().equals("중식")) {
            imageView.setImageResource(R.drawable.china);
        } else {
            imageView.setImageResource(R.drawable.spagetti);
        }

        if (checkbox_use) {
            cb.setChecked(false);
            cb.setVisibility(View.VISIBLE);
        } else {
            cb.setChecked(false);
            cb.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    Comparator<restaurant_info> nameAsc = new Comparator<restaurant_info>() {
        @Override
        public int compare(restaurant_info o1, restaurant_info o2) {
            return o1.getName().compareToIgnoreCase(o2.getName());
        }

    };
    Comparator<restaurant_info> nameAsc2 = new Comparator<restaurant_info>() {
        @Override
        public int compare(restaurant_info o1, restaurant_info o2) {
            return o1.getKind().compareToIgnoreCase(o2.getKind());
        }
    };

    public void setNameAscSort() {
        Collections.sort(data, nameAsc);
        this.notifyDataSetChanged();
    }

    public void setNameAsc2Sort() {
        Collections.sort(data, nameAsc2);
        this.notifyDataSetChanged();

    }public void checkbox_use(boolean TF){
            checkbox_use=TF;
            notifyDataSetChanged();
    }
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String filterString = constraint.toString();
                FilterResults results = new FilterResults();
                ArrayList<restaurant_info> list = data;
                data2 = data;
                int count = list.size();
                ArrayList<restaurant_info> nlist = new ArrayList<restaurant_info>();
                if(filterString.length()==0){
                    results.values= data2;
                    results.count = data2.size();
                }
                else{
                    for (int i = 0; i < count; i++) {
                        if (list.get(i).getName().contains(filterString)) {
                            nlist.add(list.get(i));
                        }
                    }
                    results.values = nlist;
                    results.count = nlist.size();
                }


                return results;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                data = (ArrayList<restaurant_info>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;


    }


}
