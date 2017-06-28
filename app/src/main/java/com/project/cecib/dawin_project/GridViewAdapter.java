package com.project.cecib.dawin_project;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cecib on 15/05/2017.
 */

public class GridViewAdapter extends BaseAdapter{
    private Context context;
    private ArrayList data = new ArrayList();
    private int layoutRessourceId;

    public GridViewAdapter(Context context, int layoutRessourceId, ArrayList data) {
        this.context = context;
        this.data = data;
        this.layoutRessourceId = layoutRessourceId;
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
        return data.indexOf(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ImageView img = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutRessourceId, parent, false);
            img = (ImageView) row.findViewById(R.id.image);
            row.setTag(img);
        }
        else{
            img = (ImageView) row.getTag();
        }

        img.setImageBitmap((Bitmap) data.get(position));

        return row;
    }

    public void setData(ArrayList d){
        data = d;
    }

}
