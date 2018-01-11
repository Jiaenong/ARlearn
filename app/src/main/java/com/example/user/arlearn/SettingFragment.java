package com.example.user.arlearn;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment{
   private Switch simpleSwitch1;
   private Switch simpleSwitch2;
   private ImageButton imageButtonSaja;
   private String check = "";

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        simpleSwitch1 = (Switch)view.findViewById(R.id.switch1);
        simpleSwitch2 = (Switch)view.findViewById(R.id.switch2);
        imageButtonSaja = (ImageButton)view.findViewById(R.id.imageButtonSaja);
        imageButtonSaja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(simpleSwitch1.isChecked()) {
                    simpleSwitch2.setChecked(false);
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF4081")));
                }
                else if(simpleSwitch2.isChecked()){
                    simpleSwitch1.setChecked(false);
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#303F9F")));
                }
            }
        });


        return view;
    }

}
