package com.qihuan.adapter;

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
    private List<Object> dataList;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public MultiTypeAdapter() {
        this(null);
    }

    @SuppressWarnings("WeakerAccess")
    public MultiTypeAdapter(@Nullable List<Object> dataList) {
        this.dataList = dataList == null ? new ArrayList<>() : dataList;
        this.typeFactory = getTypeFactory();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflateLayout(parent, viewType);
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
        return typeFactory.type(dataList.get(position));
    }

    private View inflateLayout(@NonNull ViewGroup parent, int viewType) {
        if (viewType == View.NO_ID) {
            return new View(parent.getContext());
        }
        return LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
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

    // <editor-fold defaultstate="collapsed" desc="数据操作">
    @SuppressWarnings("unused")
    public void setDataList(@NonNull List<Object> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public void addData(@IntRange(from = 0) int position, @NonNull Object data) {
        dataList.add(position, data);
        notifyItemInserted(position);
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public void addData(@NonNull Object data) {
        addData(dataList.size(), data);
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public void addData(@IntRange(from = 0) int position, @NonNull Collection<? extends Object> itemList) {
        dataList.addAll(position, itemList);
        notifyItemRangeInserted(position, itemList.size());
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public void addData(@NonNull Collection<? extends Object> itemList) {
        addData(dataList.size(), itemList);
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public void removeData(@IntRange(from = 0) int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public void setData(@IntRange(from = 0) int position, @NonNull Object data) {
        dataList.set(position, data);
        notifyItemChanged(position);
    }

    @SuppressWarnings("unused")
    public void clearDataList() {
        dataList.clear();
        notifyDataSetChanged();
    }

    @SuppressWarnings("unused")
    @NonNull
    public List<Object> getDataList() {
        return dataList;
    }
    // </editor-fold>

    public interface OnItemClickListener {
        void onItemClick(Object data, View view, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(Object data, View view, int position);
    }
}
