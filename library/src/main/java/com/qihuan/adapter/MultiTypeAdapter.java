package com.qihuan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.IntRange;
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
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public MultiTypeAdapter() {
        this(null);
    }

    @SuppressWarnings("WeakerAccess")
    public MultiTypeAdapter(@Nullable List<Item> dataList) {
        this.dataList = dataList == null ? new ArrayList<>() : dataList;
        this.typeFactory = getTypeFactory();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == View.NO_ID) {
            return emptyViewHolder(parent.getContext());
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        BaseViewHolder viewHolder = typeFactory.createViewHolder(viewType, view);
        bindViewClickListener(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        // noinspection unchecked
        holder.onBind(dataList.get(position), position, this);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (typeFactory == null) {
            return View.NO_ID;
        }
        return dataList.get(position).type(typeFactory);
    }

    private void bindViewClickListener(BaseViewHolder viewHolder) {
        View itemView = viewHolder.itemView;
        if (itemView == null) {
            return;
        }
        if (onItemClickListener != null) {
            itemView.setOnClickListener(v -> onItemClickListener.onItemClick(dataList.get(viewHolder.getLayoutPosition()), itemView, viewHolder.getLayoutPosition()));
        }
        if (onItemLongClickListener != null) {
            itemView.setOnLongClickListener(v -> onItemLongClickListener.onItemLongClick(dataList.get(viewHolder.getLayoutPosition()), itemView, viewHolder.getLayoutPosition()));
        }
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
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

    // <editor-fold defaultstate="collapsed" desc="数据操作">
    @SuppressWarnings("unused")
    public void setDataList(@NonNull List<Item> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public void addData(@IntRange(from = 0) int position, @NonNull Item item) {
        dataList.add(position, item);
        notifyItemInserted(position);
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public void addData(@NonNull Item item) {
        addData(dataList.size(), item);
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public void addData(@IntRange(from = 0) int position, @NonNull Collection<? extends Item> itemList) {
        dataList.addAll(position, itemList);
        notifyItemRangeInserted(position, itemList.size());
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public void addData(@NonNull Collection<? extends Item> itemList) {
        addData(dataList.size(), itemList);
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public void removeData(@IntRange(from = 0) int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public void setData(@IntRange(from = 0) int position, @NonNull Item item) {
        dataList.set(position, item);
        notifyItemChanged(position);
    }

    @SuppressWarnings("unused")
    public void clearDataList() {
        dataList.clear();
        notifyDataSetChanged();
    }

    @SuppressWarnings("unused")
    @NonNull
    public List<Item> getDataList() {
        return dataList;
    }
    // </editor-fold>

    public interface OnItemClickListener {
        void onItemClick(Item data, View view, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(Item data, View view, int position);
    }
}
