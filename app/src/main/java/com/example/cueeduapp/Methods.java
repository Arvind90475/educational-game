package com.example.cueeduapp;

import java.util.Random;


public class Methods {

    //make the array elements appear randomly
    public void rand_arr_elements(int[] arr) {
        Random random = new Random();
        int temp_index;
        int temp_obj;
        for (int i = 0; i < arr.length - 1; i++) {
            //a random number between i+1 and arr.length - 1
            temp_index = i + 1 + random.nextInt(arr.length - 1 - i);
            //swap the element at i with the element at temp_index
            temp_obj = arr[i];
            arr[i] = arr[temp_index];
            arr[temp_index] = temp_obj;
        }
    }
}
