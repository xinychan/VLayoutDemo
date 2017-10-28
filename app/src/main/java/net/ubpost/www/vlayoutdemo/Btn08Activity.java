package net.ubpost.www.vlayoutdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.OnePlusNLayoutHelper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * 一拖N布局 （OnePlusNLayoutHelper）
 * 将布局分为不同比例，最多是1拖4
 */
public class Btn08Activity extends AppCompatActivity implements MyItemClickListener{

    private Btn08Activity mContext;
    private VirtualLayoutManager layoutManager;
    private RecyclerView.RecycledViewPool viewPool;
    private RecyclerView recyclerView;
    private LinearLayoutHelper linearLayoutHelper;
    private OnePlusNLayoutHelper onePlusNLayoutHelper;
    private MyAdapter adapter_linearLayout;
    private MyAdapter adapter_onePlusNLayoutHelper;
    private List<HashMap<String,Object>> listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn08);
        mContext = this;
        listItem = DataDao.getList100();
        recyclerView = (RecyclerView) findViewById(R.id.rv_btn08);
        baseSetup();
        setLayoutHelper();
        setAdapter();
        bindAdapter();
    }

    //绑定适配器
    private void bindAdapter() {
        List<DelegateAdapter.Adapter> adapters = new LinkedList<>();
        //adapters.add(adapter_linearLayout);
        adapters.add(adapter_onePlusNLayoutHelper);
        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager);
        delegateAdapter.setAdapters(adapters);
        recyclerView.setAdapter(delegateAdapter);
    }

    //设置适配器
    private void setAdapter() {
        //线性布局适配器
        adapter_linearLayout = new MyAdapter(mContext,linearLayoutHelper,20,listItem);
        adapter_linearLayout.setOnItemClickListener(this);
        //一拖 N 布局适配器
        adapter_onePlusNLayoutHelper = new MyAdapter(mContext,onePlusNLayoutHelper,5,listItem){
            // 设置需要展示的数据总数,此处设置是5,即1拖4；最多为5，不能超过5，超过会报IllegalArgumentException
            //java.lang.IllegalArgumentException: OnePlusNLayoutHelper only supports maximum 5 children now
            // 为了展示效果,通过重写onBindViewHolder()将布局的第一个数据设置为onePlus
            @Override
            public void onBindViewHolder(MainViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                if (position == 0) {
                    holder.itemView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    holder.tv.setText("onePlus");
                }
                if (position == 3) {
                    holder.itemView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    holder.tv.setText("onePlus");
                }
            }
        };
        adapter_onePlusNLayoutHelper.setOnItemClickListener(this);
    }

    //设置布局
    private void setLayoutHelper() {
        //设置一个线性布局
        linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setBgColor(getResources().getColor(R.color.colorGreen));
        //设置一个一拖N布局
        onePlusNLayoutHelper = new OnePlusNLayoutHelper();
        // 在构造函数里传入显示的Item数
        // 最多是1拖4,即5个
        // 所有布局公共属性
        //onePlusNLayoutHelper.setItemCount(3);// 设置布局里Item个数
        //onePlusNLayoutHelper.setPadding(20, 20, 20, 20);// 设置LayoutHelper的子元素相对LayoutHelper边缘的距离
        //onePlusNLayoutHelper.setMargin(20, 20, 20, 20);// 设置LayoutHelper边缘相对父控件（即RecyclerView）的距离
        onePlusNLayoutHelper.setBgColor(getResources().getColor(R.color.colorYellow));// 设置背景颜色
        //onePlusNLayoutHelper.setAspectRatio(3);// 设置设置布局内每行布局的宽与高的比
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
