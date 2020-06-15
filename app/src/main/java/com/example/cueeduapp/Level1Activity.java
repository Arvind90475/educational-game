package com.example.cueeduapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class Level1Activity extends AppCompatActivity {

    Methods methods = new Methods();

    DBHelper dbHelper = new DBHelper(this);
    private String username;
    private final int NO_OF_PIECES = 4;
    private final String BUTTON_NAME_PREFIX = "l";
    int time_to_solve_puzzle = -1;

    //an array of buttons
    Button[] btn = new Button[NO_OF_PIECES];

    //correct sequence of IDs of buttons
    int[] correct_id_seq = new int[NO_OF_PIECES];

    /*this array is the working array.
      Its element's values are similar to correct_id_seq[] except diff locations*/
    int[] rand_id_seq = new int[NO_OF_PIECES];

    //array to keep 2 indexes of 2 elements in the array rand_id_seq from 2 clicks
    int two_indexes_to_swap_img[] = {-1, -1};

    int num_of_clicks = 0; //need 2 clicks to swap images

    Button two_buttons_to_swap[] = {null, null};

    TextView level1TimeView;

    Timer T;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1);


        Intent intent = getIntent();
        username = intent.getStringExtra("usernameStr");
        level1TimeView = findViewById(R.id.level1TimeView);

        // get buttons
        for(int i = 0; i < NO_OF_PIECES; i++){
            btn[i] = findViewById(this.getResources().getIdentifier(
                    BUTTON_NAME_PREFIX + i,"id", this.getPackageName()));
        }

        //first puzzle
        play_game(10, "l");
    }

    public void play_game(int perusal_time_by_seconds, String image_name) {

        //set the values for the correct_id_seq array
        for(int i = 0; i < NO_OF_PIECES; i++){
            correct_id_seq[i] = this.getResources().getIdentifier(image_name
                    + i,"drawable", this.getPackageName());
        }

        // based on the values of correct_id_seq, set the button background
        for(int i = 0; i < NO_OF_PIECES; i++){
            btn[i].setBackgroundResource(correct_id_seq[i]);
        }

        for(int i = 0; i < NO_OF_PIECES; i++){
            btn[i].setClickable(false);
        }

        //display image in an amount of perusal_time_by_seconds
        new CountDownTimer(perusal_time_by_seconds * 1000, 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                level1TimeView.setText(String.format("TIME: %s", Long.toString(millisUntilFinished / 1000)));
            }
            @Override
            public void onFinish() {

                for(int i = 0; i < NO_OF_PIECES; i++){
                    btn[i].setClickable(true);
                }
                make_puzzle_with_time_tick();
            }
        }.start();
    }

    public void make_puzzle_with_time_tick(){
        //construct rand_id_seq array, firstly, start with the correct sequence of ids
        rand_id_seq = Arrays.copyOf(correct_id_seq, correct_id_seq.length);

        //and then call the function rand_arr_elements to randomly swap elements
        //call 2 times for better results
        methods.rand_arr_elements(rand_id_seq);
        //rand_arr_elements(rand_id_seq);

        // based on the values of rand_id_seq, set the buttons' background
        for(int i = 0; i < NO_OF_PIECES; i++){
            btn[i].setBackgroundResource(rand_id_seq[i]);
        }

        //counting time by seconds
        time_to_solve_puzzle = -1;
        T = new Timer();
        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time_to_solve_puzzle++;
                        level1TimeView.setText(String.format("TIME: %d", time_to_solve_puzzle));
                    }
                });
            }
        }, 1000, 1000);
    }

    public void on_click_image(View v){
        Button button = (Button)v;

        //value of temp similar to com.jc165984.puzzle:id/btn0
        String temp_str = button.getResources().getResourceName(button.getId());

        // "id/" + BUTTON_NAME_PREFIX = "id/btn"
        int id_pos = temp_str.indexOf("id/" + BUTTON_NAME_PREFIX);

        //get the button's index.For example, id/btn0 has index 0
        int index = Integer.parseInt(temp_str.substring(id_pos +
                ("id/" + BUTTON_NAME_PREFIX).length()));

        two_indexes_to_swap_img[num_of_clicks] = index;
        two_buttons_to_swap[num_of_clicks] = button;

        if (num_of_clicks == 1) {
            //2 clicks already - swap images now
            two_buttons_to_swap[0].setBackgroundResource(rand_id_seq[two_indexes_to_swap_img[1]]);
            two_buttons_to_swap[1].setBackgroundResource(rand_id_seq[two_indexes_to_swap_img[0]]);
            //update the rand_id_seq array
            int temp = rand_id_seq[two_indexes_to_swap_img[0]];
            rand_id_seq[two_indexes_to_swap_img[0]] = rand_id_seq[two_indexes_to_swap_img[1]];
            rand_id_seq[two_indexes_to_swap_img[1]] = temp;

            //check if the 2 array rand_id_seq and correct_id_seq are equal
            //if it is then the user wins
            if (Arrays.equals(correct_id_seq, rand_id_seq)) {
                if (T != null)
                    T.cancel();
                Log.i("Time = ", Integer.toString(time_to_solve_puzzle));
                dbHelper.insertPlayer(username, time_to_solve_puzzle, 1, "Duck");
                Toast.makeText(this, "You won!  your score is saved", Toast.LENGTH_SHORT).show();
                for(int i = 0; i < NO_OF_PIECES; i++){
                    btn[i].setClickable(false);
                }
            }
        }
        num_of_clicks++;

        if (num_of_clicks == 2)
            num_of_clicks = 0;
    }











}






