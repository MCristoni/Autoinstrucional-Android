package com.mcristoni.autoinstrucional;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Spinner spinner_enemy_size;
    private Spinner spinner_hero_size;
    private ImageButton button_play;
    private boolean heroSelected = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        spinner_enemy_size = findViewById(R.id.spinner_enemy_size);
        spinner_hero_size = findViewById(R.id.spinner_hero_size);
        button_play = findViewById(R.id.button_play);
        setArrayAdapter();
        setListeners();
    }

    private void setListeners() {
        button_play.setOnClickListener(mPlayButtonListener);
        button_play.setClickable(false);
        spinner_hero_size.setOnItemSelectedListener(mHeroSizeSelected);
    }

    private void setArrayAdapter() {
        String[] sizes = {"Selecione","10","15","20","25","30","35"};
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_selected, sizes){
            @Override
            public boolean isEnabled(int position) {
                return position > 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                if(position == 0){
                    textView.setTextColor(Color.GRAY);
                }
                else {
                    textView.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item);

        spinner_enemy_size.setAdapter(arrayAdapter);
        spinner_hero_size.setAdapter(arrayAdapter);
    }

    private Spinner.OnItemSelectedListener mHeroSizeSelected = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position > 0) {
                heroSelected = true;
                checkItens();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            //do nothing
        }
    };

    private View.OnClickListener mPlayButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getBaseContext(), GameActivity.class);
            startActivity(intent);
        }
    };

    private void checkItens() {
        if (heroSelected){
            button_play.setImageResource(R.drawable.ic_play_btn_black);
            button_play.setClickable(true);
        }
    }
}
