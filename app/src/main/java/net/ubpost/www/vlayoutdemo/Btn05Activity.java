package net.ubpost.www.vlayoutdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.FixLayoutHelper;
import com.alibaba.android.vlayout.layout.FloatLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * 浮动布局（FloatLayoutHelper）
 * 布局里的Item只有一个
 * 可随意拖动，但最终会被吸边到两侧
 * 不随页面滚动而移动
 */
public class Btn05Activity extends AppCompatActivity implements MyItemClickListener{

    private Btn05Activity mContext;
    private RecyclerView recyclerView;
    private VirtualLayoutManager layoutManager;
    private RecyclerView.RecycledViewPool viewPool;
    private LinearLayoutHelper linearLayoutHelper;
    private FloatLayoutHelper floatLayoutHelper;
    private MyAdapter adapter_linearLayout;
    private MyAdapter adapter_floatLayout;
    private List<HashMap<String,Object>> listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn05);
        mContext = this;
        listItem = DataDao.getList100();
        recyclerView = (RecyclerView) findViewById(R.id.rv_btn05);
        baseSetup();
        setLayoutHelper();
        setAdapter();
        bindAdapter();
    }

    //绑定适配器
    private void bindAdapter() {
        List<DelegateAdapter.Adapter> adapters = new LinkedList<>();
        adapters.add(adapter_linearLayout);
        adapters.add(adapter_floatLayout);
        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager);
        delegateAdapter.setAdapters(adapters);
        recyclerView.setAdapter(delegateAdapter);
    }

    //设置适配器
    private void setAdapter() {
        //线性布局的适配器
        adapter_linearLayout = new MyAdapter(mContext,linearLayoutHelper,50,listItem);
        adapter_linearLayout.setOnItemClickListener(this);
        //浮动布局适配器
        adapter_floatLayout = new MyAdapter(mContext,floatLayoutHelper,1,listItem){
            // 设置需要展示的数据总数,此处设置是1
            // 为了展示效果,通过重写onBindViewHolder()将布局的第一个数据设置为float
            @Override
            public void onBindViewHolder(MainViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                //通过ViewGroup.LayoutParams给item设置宽高，否则默认为item.xml布局的宽高，可能占全屏的宽
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(150,150);
                holder.itemView.setLayoutParams(layoutParams);
                holder.itemView.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                if (position == 0) {
                    holder.tv.setText("float");
                }
            }
        };
        adapter_floatLayout.setOnItemClickListener(this);
    }

    //设置布局
    private void setLayoutHelper() {
        //设置一个线性布局
        linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setBgColor(getResources().getColor(R.color.colorGreen));
        /**
         * 设置浮动布局
         */
        floatLayoutHelper = new FloatLayoutHelper();
        /**
         * 所有布局的公共属性
         */
        //floatLayoutHelper.setItemCount(1);// 设置布局里Item个数
        //floatLayoutHelper.setPadding(50,50,50,50);// 设置LayoutHelper的子元素相对LayoutHelper边缘的距离
        //floatLayoutHelper.setMargin(50,50,50,50);// 设置LayoutHelper边缘相对父控件（即RecyclerView）的距离
        //floatLayoutHelper.setBgColor(getResources().getColor(R.color.colorGreen));// 设置背景颜色
        //设置背景颜色，对floatLayoutHelper布局没有起作用
        //floatLayoutHelper.setAspectRatio(6);// 设置布局内每行布局的宽与高的比
        /**
         * floatLayoutHelper 特有属性
         */
        floatLayoutHelper.setDefaultLocation(40,40);// 设置布局里Item的初始位置
        floatLayoutHelper.setAlignType(FixLayoutHelper.BOTTOM_RIGHT);// 设置布局里Item的初始位置
        //以上两者会一起影响Item的初始位置
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
