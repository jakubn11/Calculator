package com.example.kalkulacka;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import adapters.MyRecyclerViewAdapter;
import classes.Sample;

public class HistoryActivity extends AppCompatActivity {

    private ArrayList<String> history;
    private RecyclerView recyclerHistory;
//    private TextView emptyList;
    private MyRecyclerViewAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Historie");

        recyclerHistory = findViewById(R.id.recyclerHistory);
//        emptyList = findViewById(R.id.empty);

        history = new ArrayList<>();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        history = extras.getStringArrayList("History");

        recyclerAdapter = new MyRecyclerViewAdapter(getApplicationContext(), history);
        recyclerHistory.setItemAnimator(new DefaultItemAnimator());
        recyclerHistory.setAdapter(recyclerAdapter);

        Collections.reverse(history);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.menu, menu);
            return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                history.clear();
                recyclerAdapter = new MyRecyclerViewAdapter(getApplicationContext(), history);
                recyclerHistory.setAdapter(recyclerAdapter);
                Intent intent = new Intent();
                intent.putExtra("DeleteHistory", true);
                setResult(RESULT_OK, intent);
                finish();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}