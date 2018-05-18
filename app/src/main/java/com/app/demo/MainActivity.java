package com.app.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by thkcheng on 2017/12/23.
 */
public class MainActivity extends AppCompatActivity {

    DragListView dragListView;
    private TextView tvMag;
    private DragListAdapter adapter = null;
    ArrayList<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dragListView = findViewById(R.id.lvDragView);
        tvMag = findViewById(R.id.tv);

        initData();

        adapter = new DragListAdapter(this, data, tvMag);
        dragListView.setAdapter(adapter);

        dragListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, data.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initData() {
        data = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            data.add("DragExchangeItem_" + i);
        }
    }
}
