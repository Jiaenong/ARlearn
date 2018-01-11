package com.example.user.arlearn;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by user on 12/21/2017.
 */

public class ImageAdapter extends ArrayAdapter<subjectFile>{
    private final List<subjectFile> list;
    private subjectFile subjectfile;
    Activity context;
    public Target target;

    public ImageAdapter(Activity context, int resource, List<subjectFile> list)
    {
        super(context, resource, list);
        this.list = list;
        this.context = context;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.image, parent, false);

        //ImageLoader imageLoader;
        ImageView imageViewInitial = (ImageView) view.findViewById(R.id.imageViewPresent);
        TextView textViewID = (TextView) view.findViewById(R.id.textViewID);
        TextView textViewName = (TextView) view.findViewById(R.id.textViewName);
        TextView textViewType = (TextView) view.findViewById(R.id.textViewType);
        TextView textViewDescription1 = (TextView) view.findViewById(R.id.textViewDescription1);
        TextView textViewDescription2 = (TextView) view.findViewById(R.id.textViewDescription2);
        TextView textViewVersion = (TextView) view.findViewById(R.id.textViewVersion);

        //imageLoader = Controller.getPermission().getImageLoader();

        subjectFile subjectfile = getItem(position);

        textViewID.setText(subjectfile.getSubjectID());
        textViewName.setText(subjectfile.getName());
        textViewType.setText(subjectfile.getType());
        textViewDescription1.setText(subjectfile.getDescription1());
        textViewDescription2.setText(subjectfile.getDescription2());
        textViewVersion.setText(subjectfile.getVersion());

        textViewID.setVisibility(View.GONE);
        textViewName.setVisibility(View.GONE);
        textViewType.setVisibility(View.GONE);
        textViewDescription1.setVisibility(View.GONE);
        textViewDescription2.setVisibility(View.GONE);
        textViewVersion.setVisibility(View.GONE);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageBytes = baos.toByteArray();
        imageBytes = Base64.decode(subjectfile.getImage(), Base64.DEFAULT);
        Bitmap decodeImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        imageViewInitial.setImageBitmap(decodeImage);

        /*Picasso.Builder builder = new Picasso.Builder(context);
        builder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                exception.printStackTrace();
            }
        });*/
        //builder.build().load(subjectfile.getImage()).into(imageViewInitial);
        //Picasso.with(context).load(subjectfile.getImage()).into(imageViewInitial);
        return view;

    }

}
