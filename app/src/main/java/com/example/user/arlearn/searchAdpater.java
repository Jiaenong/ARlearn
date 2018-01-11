package com.example.user.arlearn;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Created by user on 1/1/2018.
 */

public class searchAdpater extends ArrayAdapter<subjectFile> {
    private List<subjectFile> searchList = Collections.emptyList();
    private ArrayList<subjectFile> list;
    LayoutInflater inflater;
    Context context;
    public searchAdpater(Activity context, int resource, List<subjectFile> searchList)
    {
        super(context, resource, searchList);
        this.searchList = searchList;
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.search, parent, false);
        ImageView imageViewSearch = (ImageView)convertView.findViewById(R.id.imageViewSearch);
        TextView textViewTesting = (TextView)convertView.findViewById(R.id.textViewtesting);
        subjectFile subjectfile = getItem(position);

        textViewTesting.setText(subjectfile.getSubjectID());

        textViewTesting.setVisibility(View.GONE);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageBytes = baos.toByteArray();
        imageBytes = Base64.decode(subjectfile.getImage(), Base64.DEFAULT);
        Bitmap decodeImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        imageViewSearch.setImageBitmap(decodeImage);
        return convertView;
    }




}
