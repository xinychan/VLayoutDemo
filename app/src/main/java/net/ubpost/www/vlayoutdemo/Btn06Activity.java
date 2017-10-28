package net.ubpost.www.vlayoutdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.ColumnLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * 栏格布局（ColumnLayoutHelper）
 * 该布局只设有一栏（该栏设置多个Item）
 * 可理解为只有一行的线性布局
 */
public class Btn06Activity extends AppCompatActivity implements MyItemClickListener{

    private Btn06Activity mContext;
    private VirtualLayoutManager layoutManager;
    private RecyclerView.RecycledViewPool viewPool;
    private RecyclerView recyclerView;
    private MyAdapter adapter_linearLayout;
    private MyAdapter adapter_columnLayout;
    private LinearLayoutHelper linearLayoutHelper;
    private ColumnLayoutHelper columnLayoutHelper;
    private List<HashMap<String,Object>> listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn06);
        mContext = this;
        listItem = DataDao.getList100();
        recyclerView = (RecyclerView) findViewById(R.id.rv_btn06);
        baseSetup();
        setLayoutHelper();
        setAdapter();
        bindAdapter();
    }

    //绑定适配器
    private void bindAdapter() {
        List<DelegateAdapter.Adapter> adapters = new LinkedList<>();
        adapters.add(adapter_linearLayout);
        adapters.add(adapter_columnLayout);
        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager);
        delegateAdapter.setAdapters(adapters);
        recyclerView.setAdapter(delegateAdapter);
    }

    //设置适配器
    private void setAdapter() {
        //线性布局的适配器
        adapter_linearLayout = new MyAdapter(mContext,linearLayoutHelper,20,listItem);
        adapter_linearLayout.setOnItemClickListener(this);
        //栏格布局的适配器
        adapter_columnLayout = new MyAdapter(mContext,columnLayoutHelper,5,listItem){
            @Override
            public void onBindViewHolder(MainViewHolder holder, int position) {
                // 设置需要展示的数据总数,此处设置是5
                // 为了展示效果,通过重写onBindViewHolder()将布局的第一个数据设置为Column
                super.onBindViewHolder(holder, position);
                if (position == 0) {
                    holder.tv.setText("Column");
                    holder.itemView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
            }
        };
        adapter_columnLayout.setOnItemClickListener(this);
    }

    //设置布局
    private void setLayoutHelper() {
        //设置一个线性布局
        linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setBgColor(getResources().getColor(R.color.colorGreen));
        //设置一个栏格布局
        columnLayoutHelper = new ColumnLayoutHelper();
        //所有布局的公共属性
        //columnLayoutHelper.setItemCount(3);// 设置布局里Item个数
        //columnLayoutHelper.setPadding(20, 20, 20, 20);// 设置LayoutHelper的子元素相对LayoutHelper边缘的距离
        //columnLayoutHelper.setMargin(20, 20, 20, 20);// 设置LayoutHelper边缘相对父控件（即RecyclerView）的距离
        columnLayoutHelper.setBgColor(getResources().getColor(R.color.colorYellow));// 设置背景颜色
        //columnLayoutHelper.setAspectRatio(2);// 设置布局内每行布局的宽与高的比
        //columnLayoutHelper特有属性
        columnLayoutHelper.setWeights(new float[]{20, 40, 20});// 设置该行每个Item占该行总宽度的比例
        //作用同gridLayoutHelper布局中一致
    }

    //基本设置
    private void baseSetup() {
        layoutManager = new VirtualLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        viewPool = new RecyclerView.RecycledViewPool();
        viewPool.setMaxRecycledViews(0,10);
        recyclerView.setRecycledViewPool(viewPool);
    }

    @Override
    public void onItemClick(View view, int postion) {
        Toast.makeText(this, (String) listItem.get(postion).get("ItemTitle"), Toast.LENGTH_SHORT).show();
    }
}
