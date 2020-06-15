package com.example.cueeduapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    EditText username;
    Spinner levelSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        username = findViewById(R.id.username);
        levelSpinner = findViewById(R.id.levelSpinner);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.score){
            Intent intent = new Intent(this, ScoreActivity.class);
            startActivity(intent);
        }




        return super.onOptionsItemSelected(item);
    }


    public void playButtonClicked(View view){
        String usernameStr = username.getText().toString();
        if (usernameStr.length() == 0){
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
        }else {

            int position = levelSpinner.getSelectedItemPosition();
            if (position == 0){
                Intent intent = new Intent(this,Level1Activity.class);
                intent.putExtra("usernameStr",usernameStr);
                startActivity(intent);

            }else if (position == 1){
                Intent intent = new Intent(this,Level2Activity.class);
                intent.putExtra("usernameStr",usernameStr);
                startActivity(intent);

            }else {

                Intent intent = new Intent(this, Level3Activity.class);
                intent.putExtra("usernameStr", usernameStr);
                startActivity(intent);
            }
        }
    }
}
