package com.qihuan.multitypeadapter.view.viewholder;

import android.view.View;
import android.widget.TextView;

import com.qihuan.adapter.BaseViewHolder;
import com.qihuan.adapter.MultiTypeAdapter;
import com.qihuan.annotation.ItemType;
import com.qihuan.multitypeadapter.R;
import com.qihuan.multitypeadapter.model.Dog;

import androidx.annotation.NonNull;

/**
 * DogViewHolder
 *
 * @author qi
 * @date 2018/11/29
 */
@ItemType(layoutId = R.layout.item_dog, dataClass = Dog.class)
public class DogViewHolder extends BaseViewHolder<Dog> {

    public DogViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void onBind(Dog data, int position, MultiTypeAdapter adapter) {
        TextView tvTitle = (TextView) getView(R.id.tv_title);
        tvTitle.setText(data.getName());
    }
}
