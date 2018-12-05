package com.qihuan.adapter;

import android.view.View;

import androidx.annotation.NonNull;

/**
 * TypeFactory
 *
 * @author qi
 * @date 2018/11/30
 */
interface TypeFactory {

    int type(Object data);

    @NonNull
    BaseViewHolder createViewHolder(int type, View view);
}
