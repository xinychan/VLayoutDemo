package net.ubpost.www.vlayoutdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.StaggeredGridLayoutHelper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * 瀑布流布局（StaggeredGridLayoutHelper）
 */
public class Btn10Activity extends AppCompatActivity implements MyItemClickListener{

    private Btn10Activity mContext;
    private VirtualLayoutManager layoutManager;
    private RecyclerView.RecycledViewPool viewPool;
    private RecyclerView recyclerView;
    private StaggeredGridLayoutHelper staggeredGridLayoutHelper;
    private MyAdapter adapter;
    private List<HashMap<String,Object>> listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn10);
        mContext = this;
        listItem = DataDao.getList100();
        recyclerView = (RecyclerView) findViewById(R.id.rv_btn10);
        baseSetup();
        setLayoutHelper();
        setAdapter();
        bindAdapter();
    }

    //绑定适配器
    private void bindAdapter() {
        List<DelegateAdapter.Adapter> adapters = new LinkedList<>();
        adapters.add(adapter);
        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager);
        delegateAdapter.setAdapters(adapters);
        recyclerView.setAdapter(delegateAdapter);
    }

    //设置适配器
    private void setAdapter() {
        adapter = new MyAdapter(mContext,staggeredGridLayoutHelper,40,listItem){
            // 设置需要展示的数据总数,此处设置是40
            // 通过重写onBindViewHolder()设置更加丰富的布局
            @Override
            public void onBindViewHolder(MainViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,150 +position % 5 * 20);
                //layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                layoutParams.height = (int) (Math.random() * 100) + 150;
                holder.itemView.setLayoutParams(layoutParams);
                // 为了展示效果,设置不同位置的背景色
                if (position % 3 == 0) {
                    holder.itemView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                } else if (position % 2 == 0) {
                    holder.itemView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    holder.itemView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
                // 为了展示效果,通过将布局的第一个数据设置为staggeredGrid
                if (position == 0) {
                    holder.tv.setText("staggeredGrid");
                }

            }
        };
        adapter.setOnItemClickListener(this);
    }

    //设置布局
    private void setLayoutHelper() {
        //瀑布流布局
        staggeredGridLayoutHelper = new StaggeredGridLayoutHelper();

        // 所有布局公有属性
        //staggeredGridLayoutHelper.setItemCount(20);// 设置布局里Item个数
        //staggeredGridLayoutHelper.setPadding(20, 20, 20, 20);// 设置LayoutHelper的子元素相对LayoutHelper边缘的距离
        //staggeredGridLayoutHelper.setMargin(20, 20, 20, 20);// 设置LayoutHelper边缘相对父控件（即RecyclerView）的距离
        staggeredGridLayoutHelper.setBgColor(getResources().getColor(R.color.colorYellow));// 设置背景颜色
        //staggeredGridLayoutHelper.setAspectRatio(3);// 设置设置布局内每行布局的宽与高的比

        // staggeredGridLayoutHelper 特有属性
        staggeredGridLayoutHelper.setLane(4);// 设置控制瀑布流每行的Item数
        //staggeredGridLayoutHelper.setHGap(40);// 设置子元素之间的水平间距
        staggeredGridLayoutHelper.setVGap(40);// 设置子元素之间的垂直间距

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
