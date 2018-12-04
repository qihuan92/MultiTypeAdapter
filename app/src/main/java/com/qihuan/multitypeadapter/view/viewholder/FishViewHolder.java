package com.qihuan.multitypeadapter.view.viewholder;

import android.view.View;
import android.widget.TextView;

import com.qihuan.adapter.BaseViewHolder;
import com.qihuan.adapter.MultiTypeAdapter;
import com.qihuan.annotation.BindItemView;
import com.qihuan.multitypeadapter.R;
import com.qihuan.multitypeadapter.model.Fish;

import androidx.annotation.NonNull;

/**
 * FishViewHolder
 *
 * @author qi
 * @date 2018/11/29
 */
@BindItemView(R.layout.item_fish)
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
