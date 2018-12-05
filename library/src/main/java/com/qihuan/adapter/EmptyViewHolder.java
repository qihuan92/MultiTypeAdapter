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

    @SuppressWarnings("WeakerAccess")
    public EmptyViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void onBind(Object data, int position, MultiTypeAdapter adapter) {

    }
}
