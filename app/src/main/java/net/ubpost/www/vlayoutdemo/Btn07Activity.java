package net.ubpost.www.vlayoutdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * 通栏布局（SingleLayoutHelper）
 * 布局只有一栏，该栏只有一个Item
 * 可用于广告展示栏
 */
public class Btn07Activity extends AppCompatActivity implements MyItemClickListener{

    private Btn07Activity mContext;
    private VirtualLayoutManager layoutManager;
    private RecyclerView.RecycledViewPool viewPool;
    private RecyclerView recyclerView;
    private LinearLayoutHelper linearLayoutHelper;
    private SingleLayoutHelper singleLayoutHelper;
    private MyAdapter adapter_linearLayout;
    private MyAdapter adapter_singleLayout;
    private List<HashMap<String,Object>> listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn07);
        mContext = this;
        listItem = DataDao.getList100();
        recyclerView = (RecyclerView) findViewById(R.id.rv_btn07);
        baseSetup();
        setLayoutHelper();
        setAdapter();
        bindAdapter();
    }

    //绑定适配器
    private void bindAdapter() {
        List<DelegateAdapter.Adapter> adapters = new LinkedList<>();
        adapters.add(adapter_linearLayout);
        adapters.add(adapter_singleLayout);
        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager);
        delegateAdapter.setAdapters(adapters);
        recyclerView.setAdapter(delegateAdapter);
    }

    //设置适配器
    private void setAdapter() {
        //线性布局的适配器
        adapter_linearLayout = new MyAdapter(mContext,linearLayoutHelper,20,listItem);
        adapter_linearLayout.setOnItemClickListener(this);
        //通栏布局的适配器
        adapter_singleLayout = new MyAdapter(mContext,singleLayoutHelper,1,listItem){
            // 设置需要展示的数据总数,此处设置是1
            // 为了展示效果,通过重写onBindViewHolder()将布局的第一个数据设置为Single
            @Override
            public void onBindViewHolder(MainViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                if (position == 0) {
                    holder.tv.setText("Single");
                }
            }
        };
        adapter_singleLayout.setOnItemClickListener(this);
    }

    //设置布局
    private void setLayoutHelper() {
        //设置一个线性布局
        linearLayoutHelper = new LinearLayoutHelper(5);
        linearLayoutHelper.setBgColor(getResources().getColor(R.color.colorGreen));
        //设置一个通栏布局
        singleLayoutHelper = new SingleLayoutHelper();
        // 所有布局公共属性
        //singleLayoutHelper.setItemCount(3);// 设置布局里Item个数
        //singleLayoutHelper.setPadding(20, 20, 20, 20);// 设置LayoutHelper的子元素相对LayoutHelper边缘的距离
        //singleLayoutHelper.setMargin(20, 20, 20, 20);// 设置LayoutHelper边缘相对父控件（即RecyclerView）的距离
        singleLayoutHelper.setBgColor(getResources().getColor(R.color.colorYellow));// 设置背景颜色
        singleLayoutHelper.setAspectRatio(3);// 设置设置布局内每行布局的宽与高的比
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
