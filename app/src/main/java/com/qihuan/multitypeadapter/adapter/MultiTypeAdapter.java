package com.qihuan.multitypeadapter.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qihuan.annotation.Factory;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * MultiTypeAdapter
 *
 * @author qi
 * @date 2018/11/27
 */
public class MultiTypeAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    @Factory
    TypeFactory typeFactory;
    private List<Item> dataList;

    public MultiTypeAdapter() {
        FactoryInjector.inject(this);
        typeFactory = new TypeFactoryImpl();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return typeFactory.createViewHolder(viewType, view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (dataList == null) {
            return;
        }
        holder.onBind(dataList.get(position), position, this);
    }

    @Override
    public int getItemCount() {
        if (dataList == null) {
            return 0;
        }
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).type(typeFactory);
    }

    public void setDataList(@NonNull List<Item> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public void clearDataList() {
        if (dataList == null) {
            return;
        }
        dataList.clear();
        notifyDataSetChanged();
    }
}
