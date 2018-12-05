package com.qihuan.multitypeadapter.view;

import android.os.Bundle;
import android.widget.Toast;

import com.qihuan.adapter.MultiTypeAdapter;
import com.qihuan.multitypeadapter.R;
import com.qihuan.multitypeadapter.model.Cat;
import com.qihuan.multitypeadapter.viewmodel.MainViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;
    private RecyclerView rvList;
    private MultiTypeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        initView();
        initList();
        initDataListener();
    }

    private void initView() {
        rvList = findViewById(R.id.rv_list);
    }

    private void initList() {
        rvList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MultiTypeAdapter();
        rvList.setAdapter(adapter);

        adapter.setOnItemClickListener((data, view, position) -> {
            if (data instanceof Cat) {
                Toast.makeText(this, ((Cat) data).getName(), Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "position: " + position, Toast.LENGTH_SHORT).show();
        });

        adapter.setOnItemLongClickListener((data, view, position) -> {
            Toast.makeText(this, "long click: position: " + position, Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void initDataListener() {
        mainViewModel.getDataList()
                .observe(this, dataList -> {
                    adapter.setDataList(dataList);
                });
    }
}
