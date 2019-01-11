# MultiType Adapter
![license](https://img.shields.io/github/license/qihuan92/MultiTypeAdapter.svg) ![bintray](https://img.shields.io/bintray/v/qihuan92/maven/multitype-adapter.svg) ![API](https://img.shields.io/badge/API-19%2B-brightgreen.svg?style=flat)

使用注解的方式构建多种条目类型的列表

## 引入

```groovy
dependencies {
    implementation 'com.qihuan.multitype:multitype-adapter:1.0.0'
    annotationProcessor 'com.qihuan.multitype:multitype-adapter-compiler:1.0.0'
}
```

## 使用

1. 创建实体类，示例：Example.java

   ```java
   public class Example {
   
       private String name;
       private String imageUrl;
   
       public String getName() {
           return name;
       }
   
       public Fish setName(String name) {
           this.name = name;
           return this;
       }
   
       public String getImageUrl() {
           return imageUrl;
       }
   
       public Fish setImageUrl(String imageUrl) {
           this.imageUrl = imageUrl;
           return this;
       }
   }
   ```

2. 定义布局文件，示例：item_exampl.xml

   ```xml
   <?xml version="1.0" encoding="utf-8"?>
   <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
       android:layout_width="match_parent"
       android:layout_height="wrap_content"
       android:orientation="vertical">
   
       <!-- TODO ... -->
   </LinearLayout>
   ```

3. 创建一个 ViewHolder 继承  [`BaseViewHolder<T>`](https://github.com/qihuan92/MultiTypeAdapter/blob/master/library/src/main/java/com/qihuan/adapter/BaseViewHolder.java) 并添加 [`@BindItemView()`](https://github.com/qihuan92/MultiTypeAdapter/blob/master/annotation/src/main/java/com/qihuan/annotation/BindItemView.java) 注解，并且传入定义好的布局文件

   ```java
   @BindItemView(R.layout.item_exampl)
   public class ExampleViewHolder extends BaseViewHolder<Example> {
   
       public FishViewHolder(@NonNull View itemView) {
           super(itemView);
       }
   
       @Override
       public void onBind(Example data, int position, MultiTypeAdapter adapter) {
           
       }
   }
   ```

4. 在 `Activity` 中加入 `RecyclerView`  并实例化化 [`MultiTypeAdapter`](https://github.com/qihuan92/MultiTypeAdapter/blob/master/library/src/main/java/com/qihuan/adapter/MultiTypeAdapter.java) ，示例：

   ```java
   public class MainActivity extends AppCompatActivity {
   
       private RecyclerView rvList;
       private MultiTypeAdapter adapter;
   
       @Override
       protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_main);
           initView();
           initList();
       }
   
       private void initView() {
           rvList = findViewById(R.id.rv_list);
       }
   
       private void initList() {
           rvList.setLayoutManager(new LinearLayoutManager(this));
           adapter = new MultiTypeAdapter();
           rvList.setAdapter(adapter);
       }
   }
   ```

5. 填充数据

   ```java
   List<Object> dataList = new ArrayList<>();
   // TODO 添加数据
   adapter.setDataList(dataList);
   ```

6. 点击事件

   ```java
   // 点击事件
   adapter.setOnItemClickListener((data, view, position) -> {
       // TODO
   });
   
   // 长按事件
   adapter.setOnItemLongClickListener((data, view, position) -> {
       // TODO
   });
   ```

## TODO

- [ ] 支持组件化项目
- [ ] 支持线性布局和网格布局混排列表