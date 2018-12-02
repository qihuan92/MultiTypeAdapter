package com.qihuan.multitypeadapter.view;

import android.os.Bundle;

import com.qihuan.adapter.MultiTypeAdapter;
import com.qihuan.multitypeadapter.R;
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
    }

    private void initDataListener() {
        mainViewModel.getDataList()
                .observe(this, dataList -> {
                    adapter.setDataList(dataList);
                });
    }
}
