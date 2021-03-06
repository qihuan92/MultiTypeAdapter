package com.qihuan.multitypeadapter.view.viewholder;

import android.view.View;

import com.qihuan.adapter.BaseViewHolder;
import com.qihuan.adapter.MultiTypeAdapter;
import com.qihuan.annotation.BindItemView;
import com.qihuan.multitypeadapter.R;
import com.qihuan.multitypeadapter.model.Cat;

import androidx.annotation.NonNull;

/**
 * CatViewHolder
 *
 * @author qi
 * @date 2018/11/29
 */
@BindItemView(R.layout.item_cat)
public class CatViewHolder extends BaseViewHolder<Cat> {

    public CatViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void onBind(Cat data, int position, MultiTypeAdapter adapter) {
        setText(R.id.tv_title, data.getName());
    }
}
