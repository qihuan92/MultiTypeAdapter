package com.qihuan.adapter;

import android.view.View;

import androidx.annotation.NonNull;

/**
 * EmptyViewHolder
 *
 * @author qi
 * @date 2018/12/4
 */
public class EmptyViewHolder extends BaseViewHolder {

    public EmptyViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void onBind(Item data, int position, MultiTypeAdapter adapter) {

    }
}
