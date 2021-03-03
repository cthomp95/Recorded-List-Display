package com.example.listingtestapp;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.lang.System;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String filename = "submissions.txt";

    Button submit, show_previous_submissions;
    EditText userInput;
    TextView fileContent;
    String currMrno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        show_previous_submissions = findViewById(R.id.show_previous_submissions_button);
        submit = findViewById(R.id.submit_button);
        userInput = findViewById(R.id.userInput);
        fileContent = findViewById(R.id.content);
        fileContent.setText("ABDS");
       // File file = new File(context.getFilesDir(), filename);

        show_previous_submissions.setOnClickListener(this);
        submit.setOnClickListener(this);

        InitializeList(filename);

    }

    public void printMessage(String m) {
        Toast.makeText(this, m, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        Button b = (Button) view;
        String b_text = b.getText().toString();

        switch (b_text.toLowerCase()) {
            case "submit": {
                addTolist();
                break;
            }
            case "show_previous_submissions": {
                readData();
                break;
            }
        }

    }

    private void addTolist() {
        try {
            FileInputStream current = openFileInput(filename); //opens filestream to pull data
            int b;
            StringBuilder fulllist = new StringBuilder();

            while ((b = current.read()) != -1) {
                fulllist.append((char) b);
            }

            String inputs = userInput.getText().toString();

            Date curDate = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMMM d 'at' hh:mm a ");

            String formattedDateString = formatter.format(curDate);
            inputs = inputs + formattedDateString;
            String fullsubmissions = inputs+fulllist;

            fileContent.setText(inputs);

            try (FileOutputStream rewrite = openFileOutput(filename, Context.MODE_PRIVATE)) {
                rewrite.write(fullsubmissions.getBytes());
                rewrite.flush();
            }
            current.close();

        }

        catch (IOException e) {
            e.printStackTrace();
        }

        userInput.setText("Next");
        printMessage("saving to" + filename + "completed...");
    }



    private void readData()
    {
        try
        {
            FileInputStream fin = openFileInput(filename);
            int a;
            StringBuilder temp = new StringBuilder();
            while ((a = fin.read()) != -1)
            {
                temp.append((char)a);
            }

            // setting text from the file.
            fileContent.setText(temp.toString());
            fin.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        printMessage("reading from file " + filename + " completed..");
    }

    private void InitializeList(String file)
    {
        try
        {
            FileOutputStream fos = openFileOutput(file, Context.MODE_PRIVATE);
            String tempfill = "";
            fos.write(tempfill.getBytes());
            fos.flush();
            fos.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        userInput.setText("Next");
        //printMessage("saved to file " + filename + " completed...");
    }




}