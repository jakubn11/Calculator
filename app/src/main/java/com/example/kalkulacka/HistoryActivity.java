package com.example.kalkulacka;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import adapters.MyRecyclerViewAdapter;
import classes.Sample;

public class HistoryActivity extends AppCompatActivity implements MyRecyclerViewAdapter.OnResultListener {

    private ArrayList<String> history;
    private RecyclerView recyclerHistory;
    private TextView emptyList;
    private MyRecyclerViewAdapter recyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private MenuItem delete, deleteNothing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Historie");

        recyclerHistory = findViewById(R.id.recyclerHistory);
        emptyList = findViewById(R.id.emptyText);

        history = new ArrayList<>();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        history = extras.getStringArrayList("History");

        if (history.size() == 0) {
            recyclerHistory.setVisibility(View.GONE);
            emptyList.setVisibility(View.VISIBLE);
        } else {
            recyclerHistory.setVisibility(View.VISIBLE);
            emptyList.setVisibility(View.GONE);
        }

        Collections.reverse(history);

        recyclerAdapter = new MyRecyclerViewAdapter(getApplicationContext(), history, this);
        recyclerHistory.setAdapter(recyclerAdapter);
        layoutManager = new LinearLayoutManager(this);
        recyclerHistory.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        delete = menu.findItem(R.id.delete);
        deleteNothing = menu.findItem(R.id.delete_nothing);

        checkList();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                history.clear();
                recyclerHistory.setAdapter(recyclerAdapter);
                Intent intent = new Intent();
                intent.putExtra("DeleteHistory", true);
                setResult(RESULT_OK, intent);
                finish();
                return true;

            case R.id.delete_nothing:
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void checkList() {
        if (history.isEmpty()) {
            deleteNothing.setVisible(true);
            delete.setVisible(false);
        }else {
            deleteNothing.setVisible(false);
            delete.setVisible(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onResultClick(int position) {
        String result = history.get(position);
        Intent intent = new Intent();
        intent.putExtra("DataFromHistory", result);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}