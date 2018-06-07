package com.hall.jia.tournamentsquareapp.listadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.hall.jia.tournamentsquareapp.JoinActivity;
import com.hall.jia.tournamentsquareapp.R;

import java.util.HashMap;
import java.util.List;

public class TournamentList extends android.widget.BaseExpandableListAdapter {

    private List<String> header_titles;
    private HashMap<String, List<String>> child_titles;
    private Context ctx;

    public TournamentList(Context ctx, List<String> header_titles, HashMap<String, List<String>> child_titles) {
        this.ctx = ctx;
        this.child_titles = child_titles;
        this.header_titles = header_titles;

    }


    @Override
    public int getGroupCount() {
        return header_titles.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return child_titles.get(header_titles.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return header_titles.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child_titles.get(header_titles.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String title = (String) this.getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.parent_layout, null);
        }
        TextView textView = convertView.findViewById(R.id.heading_item);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(title);
        //HOW WE'RE GONNA SET UP PROGRESSBAR
        //int titlel = (int) this.getChild(groupPosition, 5);

        return convertView;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup viewGroup) {
        String title = (String) this.getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.child_layout, null);

        }
        TextView textView = convertView.findViewById(R.id.child_item);
        textView.setText(title);

        Button button = convertView.findViewById(R.id.btnhi);
        if (isLastChild) {
            button.setVisibility(View.VISIBLE);
        } else {
            button.setVisibility(View.GONE);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("button  alontg", "char 4 or over");

            }
        });

        Button button2 = convertView.findViewById(R.id.btnJoin);
        if (isLastChild) {

            button2.setVisibility(View.VISIBLE);

        } else {
            button2.setVisibility(View.GONE);
        }
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("button  alontg", "char 4 or over");


                ((JoinActivity)ctx).hi();


            }
        });


        return convertView;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}