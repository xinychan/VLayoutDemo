package net.ubpost.www.vlayoutdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * 线性布局（LinearLayoutHelper）
 */
public class Btn01Activity extends AppCompatActivity implements MyItemClickListener{

    private Btn01Activity mContext;
    private RecyclerView recyclerView;
    private VirtualLayoutManager layoutManager;
    private RecyclerView.RecycledViewPool viewPool;
    private MyAdapter adapter;//适配器
    private List<HashMap<String, Object>> listItem;//存放的数据
    private LinearLayoutHelper linearLayoutHelper;//线性布局

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn01);
        mContext = this;
        listItem = DataDao.getList20();
        baseSetup();//基本设置
        setLayoutHelper();//设置LayoutHelper
        setAdapter();//设置adapter
        bindAdapter();//绑定adapter
    }

    //基本设置
    private void baseSetup() {
        /**
         * 创建RecyclerView & VirtualLayoutManager 对象并进行绑定
         * VirtualLayoutManager内部会创建一个LayoutHelperFinder对象，用来后续的LayoutHelper查找
         */
        recyclerView = (RecyclerView) findViewById(R.id.rv_btn01);
        layoutManager = new VirtualLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        /**
         * 设置回收复用池大小
         * 如果一屏内相同类型的 View 个数比较多，需要设置一个合适的大小，防止来回滚动时重新创建 View
         */
        viewPool = new RecyclerView.RecycledViewPool();
        viewPool.setMaxRecycledViews(0,10);//对viewType为0的样式，最多只创建10个View，其他复用
        recyclerView.setRecycledViewPool(viewPool);
    }

    //设置LayoutHelper，创建对应的布局
    private void setLayoutHelper(){
        //创建一个线性布局
        linearLayoutHelper = new LinearLayoutHelper();
        /**
         * 所有布局的公共属性
         */
        //linearLayoutHelper.setItemCount(10);//设置布局里Item个数
        // 如设置的Item总数与Adapter的getItemCount()方法返回的数量不同，会取决于后者
        //linearLayoutHelper.setPadding(20,20,20,20);//设置LayoutHelper的子元素相对LayoutHelper边缘的距离
        //linearLayoutHelper.setMargin(20,20,20,20);//设置LayoutHelper边缘相对父控件（即RecyclerView）的距离
        linearLayoutHelper.setBgColor(getResources().getColor(R.color.colorGry));//设置背景颜色
        //linearLayoutHelper.setAspectRatio(5);//设置布局内每行布局的宽与高的比
        /**
         * linearLayoutHelper特有属性
         */
        //linearLayoutHelper.setDividerHeight(100);
        // 设置间隔高度，这个间隔高度是计算到布局中去的，不像分隔线有View填充的效果
        // 设置的间隔会与RecyclerView的addItemDecoration（）添加的间隔叠加.
    }

    //设置适配器
    private void setAdapter(){
        // 创建自定义的Adapter对象 & 绑定数据 & 绑定对应的LayoutHelper进行布局绘制
        // 参数2:绑定对应的LayoutHelper
        // 参数3:传入该布局需要显示的数据个数
        // 参数4:传入需要绑定的数据
        adapter = new MyAdapter(mContext,linearLayoutHelper,8,listItem){
            // 通过重写onBindViewHolder()设置更丰富的布局效果
            @Override
            public void onBindViewHolder(MainViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                // 为了展示效果,将布局的第一个数据设置为linearLayout
                if (position == 0) {
                    holder.tv.setText("Linear");
                }
                //为了展示效果,将布局里不同位置的Item进行背景颜色设置
                if (position < 2) {
                    holder.itemView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                } else if (position % 2 == 0) {
                    holder.itemView.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                } else {
                    holder.itemView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
            }
        };
        adapter.setOnItemClickListener(this);// 设置每个Item的点击事件
    }

    //绑定适配器
    private void bindAdapter(){
        //将生成的LayoutHelper 交给Adapter，并绑定到RecyclerView 对象
        // 1. 设置Adapter列表（同时也是设置LayoutHelper列表）
        List<DelegateAdapter.Adapter> adapters = new LinkedList<>();
        // 2. 将创建的Adapter对象放入到DelegateAdapter.Adapter列表里
        adapters.add(adapter);
        // 3. 创建DelegateAdapter对象 & 将layoutManager绑定到DelegateAdapter
        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager);
        // 4. 将DelegateAdapter.Adapter列表绑定到DelegateAdapter
        delegateAdapter.setAdapters(adapters);
        // 5. 将delegateAdapter绑定到recyclerView
        recyclerView.setAdapter(delegateAdapter);
        // 6.给recyclerView添加分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext));
    }

    @Override
    public void onItemClick(View view, int postion) {
        Toast.makeText(this, (String) listItem.get(postion).get("ItemTitle"), Toast.LENGTH_SHORT).show();
    }
}
