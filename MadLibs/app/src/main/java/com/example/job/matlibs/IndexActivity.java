package com.example.job.matlibs;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;

public class IndexActivity extends AppCompatActivity {

    String[] storyList;
    AssetManager assets;
    ListView indexListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        assets = this.getAssets();

        try {
            storyList = assets.list("text");
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, storyList);

        indexListView = (ListView) findViewById(R.id.indexList);
        indexListView.setAdapter(myAdapter);

        indexListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String filename = storyList[position];
                Intent intent = new Intent(IndexActivity.this, StoryActivity.class);
                intent.putExtra("filename", filename);
                startActivity(intent);
                finish();
            }
        });

    }
}
