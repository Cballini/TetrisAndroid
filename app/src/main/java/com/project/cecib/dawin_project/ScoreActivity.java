package com.project.cecib.dawin_project;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by cecib on 27/06/2017.
 */

public class ScoreActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.best_score);

        //récupération meilleurs scores
        SharedPreferences best = PreferenceManager.getDefaultSharedPreferences(this);
        TextView score1 =(TextView) findViewById(R.id.best1);
        TextView score2 =(TextView) findViewById(R.id.best2);
        TextView score3 =(TextView) findViewById(R.id.best3);
        score1.setText("1er : "+String.valueOf(best.getInt("score1",0)));
        score2.setText("2ème : "+String.valueOf(best.getInt("score2",0)));
        score3.setText("3ème : "+String.valueOf(best.getInt("score3",0)));

    }
}
