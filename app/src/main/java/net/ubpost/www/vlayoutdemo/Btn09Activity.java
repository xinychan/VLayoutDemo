package net.ubpost.www.vlayoutdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.StickyLayoutHelper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * 粘性/吸边布局（StickyLayoutHelper）
 * 布局只有一个Item，显示逻辑如下：
 * 当它包含的组件处于屏幕可见范围内时，像正常的组件一样随页面滚动而滚动
 * 当组件将要被滑出屏幕返回的时候，可以吸到屏幕的顶部或者底部，实现一种吸住的效果
 */
public class Btn09Activity extends AppCompatActivity implements MyItemClickListener{

    private Btn09Activity mContext;
    private VirtualLayoutManager layoutManager;
    private RecyclerView.RecycledViewPool viewPool;
    private RecyclerView recyclerView;
    private LinearLayoutHelper linearLayoutHelper;//上部分的线性布局
    private LinearLayoutHelper linearLayoutHelper2;//下部分的线性布局
    private StickyLayoutHelper stickyLayoutHelper;//粘性/吸边布局
    private MyAdapter adapter_linearLayout;
    private MyAdapter adapter_linearLayout2;
    private MyAdapter adapter_stickyLayout;
    private List<HashMap<String,Object>> listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn09);
        mContext = this;
        listItem = DataDao.getList100();
        recyclerView = (RecyclerView) findViewById(R.id.rv_btn09);
        baseSetup();
        setLayoutHelper();
        setAdapter();
        bindAdapter();
    }

    //绑定适配器
    private void bindAdapter() {
        List<DelegateAdapter.Adapter> adapters = new LinkedList<>();
        adapters.add(adapter_linearLayout);
        adapters.add(adapter_stickyLayout);
        adapters.add(adapter_linearLayout2);
        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager);
        delegateAdapter.setAdapters(adapters);
        recyclerView.setAdapter(delegateAdapter);
    }

    //设置适配器
    private void setAdapter() {
        //设置线性布局适配器
        adapter_linearLayout = new MyAdapter(mContext,linearLayoutHelper,20,listItem);
        adapter_linearLayout.setOnItemClickListener(this);
        adapter_linearLayout2 = new MyAdapter(mContext,linearLayoutHelper2,20,listItem);
        adapter_linearLayout2.setOnItemClickListener(this);
        //设置粘性布局适配器
        adapter_stickyLayout = new MyAdapter(mContext,stickyLayoutHelper,1,listItem){
            // 设置需要展示的数据总数,此处设置是1
            // 为了展示效果,通过重写onBindViewHolder()将布局的第一个数据设置为Stick
            @Override
            public void onBindViewHolder(MainViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                if (position == 0) {
                    holder.itemView.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                    holder.tv.setText("Stick");
                }
            }
        };
        adapter_stickyLayout.setOnItemClickListener(this);
    }

    //设置布局
    private void setLayoutHelper() {
        //设置上下部分的线性布局
        linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setBgColor(getResources().getColor(R.color.colorGreen));
        linearLayoutHelper2 = new LinearLayoutHelper();
        linearLayoutHelper2.setBgColor(getResources().getColor(R.color.colorAccent));
        //设置粘性布局
        stickyLayoutHelper = new StickyLayoutHelper();
        //所有布局公共属性
        //stickyLayoutHelper.setItemCount(3);// 设置布局里Item个数
        //stickyLayoutHelper.setPadding(20, 20, 20, 20);// 设置LayoutHelper的子元素相对LayoutHelper边缘的距离
        //stickyLayoutHelper.setMargin(20, 20, 20, 20);// 设置LayoutHelper边缘相对父控件（即RecyclerView）的距离
        //stickyLayoutHelper.setBgColor(Color.GRAY);// 设置背景颜色
        stickyLayoutHelper.setAspectRatio(3);// 设置设置布局内每行布局的宽与高的比
        // stickyLayoutHelper 特有属性
        //stickyLayoutHelper.setStickyStart(false);
        // true = 组件吸在顶部；默认在顶部
        // false = 组件吸在底部
        //stickyLayoutHelper.setOffset(200);// 设置吸边位置的偏移量（与Y轴的偏移）
        //在顶部则显示为与屏幕上边界的距离
        //在底部则显示为与屏幕下边界的距离
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
