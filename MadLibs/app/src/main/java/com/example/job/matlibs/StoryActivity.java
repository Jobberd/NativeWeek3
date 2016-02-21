package com.example.job.matlibs;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class StoryActivity extends AppCompatActivity {

    AssetManager assets;
    Story currentStory;
    InputStream inputStream;
    TextView progressText;
    TextView typeOfInputNeeded;
    EditText newInput;
    Button acceptInputButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        Bundle extra = getIntent().getExtras();
        String filename = extra.getString("filename");


        assets = this.getAssets();

        progressText = (TextView) findViewById(R.id.progressText);
        typeOfInputNeeded = (TextView) findViewById(R.id.typeOfInputNeeded);
        newInput = (EditText) findViewById(R.id.newInput);
        acceptInputButton = (Button) findViewById(R.id.acceptInput);


        try {
            inputStream = assets.open("text/" + filename, AssetManager.ACCESS_BUFFER);
        } catch (IOException e) {
            e.printStackTrace();
        }

        currentStory = new Story(inputStream);

        typeOfInputNeeded.setText(instructionText());
        progressText.setText(remainderText());

        acceptInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentStory.fillInPlaceholder(newInput.getText().toString());
                if (currentStory.getPlaceholderRemainingCount() >= 1) {
                    newInput.setText("");
                    typeOfInputNeeded.setText(instructionText());
                    progressText.setText(remainderText());
                }
                else {
                    Intent intent = new Intent(StoryActivity.this, ResultActivity.class);
                    intent.putExtra("outputtext", currentStory.toString());
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private String instructionText() {
        return "please type in a " + currentStory.getNextPlaceholder();
    }

    private String remainderText() {
        return currentStory.getPlaceholderRemainingCount() + " words remaining";
    }

}
