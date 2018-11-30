package com.qihuan.multitypeadapter.view.viewholder;

import android.view.View;
import android.widget.TextView;

import com.qihuan.annotation.ItemType;
import com.qihuan.multitypeadapter.R;
import com.qihuan.multitypeadapter.adapter.BaseViewHolder;
import com.qihuan.multitypeadapter.adapter.MultiTypeAdapter;
import com.qihuan.multitypeadapter.model.Fish;

import androidx.annotation.NonNull;

/**
 * FishViewHolder
 *
 * @author qi
 * @date 2018/11/29
 */
@ItemType(layoutId = R.layout.item_fish, dataClass = Fish.class)
public class FishViewHolder extends BaseViewHolder<Fish> {

    public FishViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void onBind(Fish data, int position, MultiTypeAdapter adapter) {
        TextView tvTitle = (TextView) getView(R.id.tv_title);
        tvTitle.setText(data.getName());
    }
}
