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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Spinner spinner_enemy_size;
    private Spinner spinner_hero_size;
    private Spinner spinner_target_size;
    private Switch switch_moving_target;
    private ImageButton button_play;
    private boolean heroSelected = false;
    private boolean enemySelected = false;
    private boolean targetSelected = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        instanciarElementosDeTela();
        setArrayAdapter();
        setListeners();
    }

    private void instanciarElementosDeTela() {
        spinner_enemy_size = findViewById(R.id.spinner_enemy_size);
        spinner_hero_size = findViewById(R.id.spinner_hero_size);
        spinner_target_size = findViewById(R.id.spinner_target_size);
        button_play = findViewById(R.id.button_play);
        switch_moving_target = findViewById(R.id.switch_moving_target);
    }

    private void setArrayAdapter() {
        List<String> list = new ArrayList<>();
        list.add("Selecione");
        for (int i = 40; i <= 150; i+=5){
            list.add(String.valueOf(i));
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_selected, list){
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
        spinner_target_size.setAdapter(arrayAdapter);
    }

    private void setListeners() {
        button_play.setOnClickListener(mPlayButtonListener);
        button_play.setClickable(false);
        spinner_hero_size.setOnItemSelectedListener(mItemSelected);
        spinner_enemy_size.setOnItemSelectedListener(mItemSelected);
        spinner_target_size.setOnItemSelectedListener(mItemSelected);
    }

    private Spinner.OnItemSelectedListener mItemSelected = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()){
                case R.id.spinner_hero_size:
                    if (position > 0) {
                        heroSelected = true;
                    }
                    break;

                case R.id.spinner_enemy_size:
                    if (position > 0) {
                        enemySelected = true;
                    }
                    break;

                case R.id.spinner_target_size:
                    if (position > 0) {
                        targetSelected = true;
                    }
                    break;
            }
            checkItens();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            //do nothing
        }

        private void checkItens() {
            if (heroSelected && enemySelected && targetSelected){
                button_play.setImageResource(R.drawable.ic_play_btn_black);
                button_play.setClickable(true);
            }else {
                button_play.setImageResource(R.drawable.ic_play_btn_gray);
                button_play.setClickable(false);
            }
        }
    };

    private View.OnClickListener mPlayButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getBaseContext(), GameActivity.class);
            intent.putExtra(Constants.HERO_SIZE_VALUE, Float.parseFloat(spinner_hero_size.getSelectedItem().toString()));
            intent.putExtra(Constants.ENEMY_SIZE_VALUE, Float.parseFloat(spinner_enemy_size.getSelectedItem().toString()));
            intent.putExtra(Constants.TARGET_SIZE_VALUE, Float.parseFloat(spinner_target_size.getSelectedItem().toString()));
            intent.putExtra(Constants.TARGET_MOVING_VALUE, switch_moving_target.isChecked());
            startActivityForResult(intent, Constants.GAME_ACTIVITY_REQUEST_CODE);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.GAME_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            setArrayAdapter();
            heroSelected = enemySelected = targetSelected = false;
            switch_moving_target.setChecked(false);
        }
    }
}
