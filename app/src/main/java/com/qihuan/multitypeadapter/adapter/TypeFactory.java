package com.qihuan.multitypeadapter.adapter;

import android.view.View;

/**
 * TypeFactory
 *
 * @author qi
 * @date 2018/11/30
 */
public interface TypeFactory {

    int type(Item data);

    BaseViewHolder createViewHolder(int type, View view);
}
