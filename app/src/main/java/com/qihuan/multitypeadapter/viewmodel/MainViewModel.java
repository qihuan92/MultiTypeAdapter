package com.qihuan.multitypeadapter.viewmodel;

import com.qihuan.multitypeadapter.model.Cat;
import com.qihuan.multitypeadapter.model.Dog;
import com.qihuan.multitypeadapter.model.Fish;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * MainViewModel
 *
 * @author qi
 * @date 2018/11/29
 */
public class MainViewModel extends ViewModel {

    private MutableLiveData<List<Object>> dataList = new MutableLiveData<>();
    private List<Object> list = new ArrayList<>();

    public MainViewModel() {
        setData();
    }

    public LiveData<List<Object>> getDataList() {
        return dataList;
    }

    public void setData() {
        list.clear();
        List<Cat> catList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            catList.add(new Cat().setName("cat " + i));
        }
        list.addAll(catList);

        List<Dog> dogList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            dogList.add(new Dog().setName("dog " + i));
        }
        list.addAll(dogList);

        List<Fish> fishList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            fishList.add(new Fish().setName("fish " + i));
        }
        list.addAll(fishList);

        Collections.shuffle(list);
        dataList.postValue(list);
    }
}
