package com.qihuan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * MultiTypeAdapter
 *
 * @author qi
 * @date 2018/11/27
 */
public class MultiTypeAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final String IMPL = "_Impl";

    private TypeFactory typeFactory;
    private List<Item> dataList;
    private BaseViewHolder emptyViewHolder;

    public MultiTypeAdapter() {
        this.typeFactory = getTypeFactory();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == View.NO_ID) {
            return emptyViewHolder(parent.getContext());
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return typeFactory.createViewHolder(viewType, view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (dataList == null) {
            return;
        }
        // noinspection unchecked
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
        if (typeFactory == null) {
            return View.NO_ID;
        }
        return dataList.get(position).type(typeFactory);
    }

    @SuppressWarnings("unused")
    public void setDataList(@NonNull List<Item> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @SuppressWarnings("unused")
    public void clearDataList() {
        if (dataList == null) {
            return;
        }
        dataList.clear();
        notifyDataSetChanged();
    }

    @Nullable
    private TypeFactory getTypeFactory() {
        TypeFactory typeFactory = null;
        try {
            typeFactory = (TypeFactory) Class.forName(TypeFactory.class.getCanonicalName() + IMPL).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ignored) {
        }
        return typeFactory;
    }

    @SuppressWarnings("unused")
    public void setEmptyViewHolder(BaseViewHolder emptyViewHolder) {
        this.emptyViewHolder = emptyViewHolder;
    }

    private BaseViewHolder emptyViewHolder(Context context) {
        if (emptyViewHolder == null) {
            return new EmptyViewHolder(new View(context));
        }
        return emptyViewHolder;
    }
}
