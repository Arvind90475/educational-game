package com.example.cueeduapp;

import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class ScoreActivity extends AppCompatActivity {

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dbHelper = new DBHelper(this);


        ArrayAdapter<String> scoreAdapter = new ArrayAdapter<>(this, R.layout.item);

        // get the ListView element
        ListView scoreList = findViewById(R.id.scoreList);

        //set adapter on the listView
        scoreList.setAdapter(scoreAdapter);



        // update the adapter form the DB
        Cursor cursor = dbHelper.getAllPlayers();
        // check if cursor contains record
        if(cursor != null){
            if (cursor.getCount() > 0){
                cursor.moveToFirst();
                do{
                    String tempStr =
                    leftJustification(" "+ cursor.getString(cursor.getColumnIndex(DBHelper.USERNAME_COL)),
                        10) +

                    leftJustification(cursor.getString(cursor.getColumnIndex(DBHelper.SCORE_COL)),
                        10) +

                    leftJustification(cursor.getString(cursor.getColumnIndex(DBHelper.LEVEL_COL)),
                        5) +

                    leftJustification(cursor.getString(cursor.getColumnIndex(DBHelper.DATE_COL)),
                    5);




                    // writing this on adapter
                    scoreAdapter.add(tempStr);

                }while (cursor.moveToNext());
            }
        }
    }


    private String leftJustification(String string, int length){
        string.trim();
        return String.format("%-" + length + "s", string);
    }

}
