package net.ubpost.www.vlayoutdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.FixLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.ScrollFixLayoutHelper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static android.R.id.list;

/**
 * 可选显示的固定布局（ScrollFixLayoutHelper）
 * 固定在屏幕某个位置，且不可拖拽 & 不随页面滚动而滚动（继承自固定布局（FixLayoutHelper））
 * 唯一不同的是，可以自由设置该Item什么时候显示（到顶部显示 / 到底部显示）
 * 需求场景：到页面底部显示”一键到顶部“的按钮功能
 * 一个RecyclerView可以有多个固定布局
 */
public class Btn04Activity extends AppCompatActivity implements MyItemClickListener {

    private Btn04Activity mContext;
    private RecyclerView recyclerView;
    private VirtualLayoutManager layoutManager;
    private RecyclerView.RecycledViewPool viewPool;
    private MyAdapter adapter_linearLayout;
    private MyAdapter adapter_linearLayout2;
    private MyAdapter adapter_scrollFixLayout;
    private MyAdapter adapter_scrollFixLayout2;
    private LinearLayoutHelper linearLayoutHelper;//第一部分线性布局
    private LinearLayoutHelper linearLayoutHelper2;//第二部分线性布局
    private ScrollFixLayoutHelper scrollFixLayoutHelper;//可选显示的固定布局
    private ScrollFixLayoutHelper scrollFixLayoutHelper2;//可选显示的固定布局
    private List<HashMap<String,Object>> listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn04);
        mContext = this;
        //listItem = DataDao.getList20();
        listItem = DataDao.getList100();
        recyclerView = (RecyclerView) findViewById(R.id.rv_btn04);
        baseSetup();
        setLayoutHelper();
        setAdapter();
        bindAdapter();
    }

    //绑定适配器
    private void bindAdapter() {
        List<DelegateAdapter.Adapter> adapters = new LinkedList<>();
        adapters.add(adapter_linearLayout);
        adapters.add(adapter_scrollFixLayout2);
        adapters.add(adapter_scrollFixLayout);
        adapters.add(adapter_linearLayout2);
        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager);
        delegateAdapter.setAdapters(adapters);
        recyclerView.setAdapter(delegateAdapter);
    }

    //设置适配器
    private void setAdapter() {
        //线性布局的适配器
        adapter_linearLayout = new MyAdapter(mContext,linearLayoutHelper,30,listItem);
        adapter_linearLayout.setOnItemClickListener(this);
        //可选显示的固定布局的适配器
        adapter_scrollFixLayout = new MyAdapter(mContext,scrollFixLayoutHelper,1,listItem){
            // 设置需要展示的数据总数,此处设置是1
            // 为了展示效果,通过重写onBindViewHolder()将布局的第一个数据设置为scrollFix
            @Override
            public void onBindViewHolder(MainViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                if (position == 0) {
                    holder.tv.setText("scrollFix");
                }
            }
        };
        adapter_scrollFixLayout.setOnItemClickListener(this);
        //第二个线性布局的适配器
        adapter_linearLayout2 = new MyAdapter(mContext,linearLayoutHelper2,30,listItem);
        adapter_linearLayout2.setOnItemClickListener(this);
        //第二个可选显示的固定布局的适配器
        adapter_scrollFixLayout2 = new MyAdapter(mContext,scrollFixLayoutHelper2,1,listItem);
        adapter_scrollFixLayout2.setOnItemClickListener(this);
    }

    //设置布局
    private void setLayoutHelper() {
        /*
         * 先设置线性布局
         * 用以滑动
         */
        linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setBgColor(getResources().getColor(R.color.colorGreen));
        linearLayoutHelper2 = new LinearLayoutHelper();
        linearLayoutHelper2.setBgColor(getResources().getColor(R.color.colorAccent));
        /**
         * 可选显示的固定布局
         */
        scrollFixLayoutHelper = new ScrollFixLayoutHelper(FixLayoutHelper.TOP_LEFT,40,40);
        // 参数1:设置吸边时的基准位置(alignType) - 有四个取值:TOP_LEFT(默认), TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
        // 参数2:基准位置的偏移量x
        // 参数3:基准位置的偏移量y
        // 继承自FixLayoutHelper，参数和众多方法与FixLayoutHelper使用一致
        /**
         * 所有布局的公共属性
         */
        //scrollFixLayoutHelper.setItemCount(1);// 设置布局里Item个数
        // 从设置Item数目的源码可以看出，一个FixLayoutHelper只能设置一个
        //scrollFixLayoutHelper.setPadding(50,50,50,50);// 设置LayoutHelper的子元素相对LayoutHelper边缘的距离
        //scrollFixLayoutHelper.setMargin(50,50,50,50);// 设置LayoutHelper边缘相对父控件（即RecyclerView）的距离
        //scrollFixLayoutHelper.setBgColor(getResources().getColor(R.color.colorYellow));// 设置背景颜色
        //设置背景颜色，对fixLayout布局没有起作用
        //scrollFixLayoutHelper.setAspectRatio(3);// 设置布局内每行布局的宽与高的比
        /**
         * scrollFixLayoutHelper特有属性
         */
        scrollFixLayoutHelper.setAlignType(FixLayoutHelper.BOTTOM_LEFT);//设置吸边时的基准位置(alignType)
        scrollFixLayoutHelper.setX(100);// 设置基准位置的横向偏移量X
        scrollFixLayoutHelper.setY(100);// 设置基准位置的纵向偏移量Y
        scrollFixLayoutHelper.setShowType(ScrollFixLayoutHelper.SHOW_ON_ENTER);// 设置Item的显示模式
        /** 共有三种显示模式
         * SHOW_ALWAYS：永远显示(即效果同固定布局)
           SHOW_ON_ENTER：默认不显示视图，当页面滚动到该视图位置时才显示；
           SHOW_ON_LEAVE：默认不显示视图，当页面滚出该视图位置时才显示；往上滚出才会显示，往下滚出不显示
         */

        //第二个可选显示的固定布局
        scrollFixLayoutHelper2 = new ScrollFixLayoutHelper(FixLayoutHelper.TOP_LEFT,40,40);
        scrollFixLayoutHelper2.setShowType(ScrollFixLayoutHelper.SHOW_ON_LEAVE);
    }

    //基本设置
    private void baseSetup() {
        layoutManager = new VirtualLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        viewPool = new RecyclerView.RecycledViewPool();
        viewPool.setMaxRecycledViews(0, 10);
        recyclerView.setRecycledViewPool(viewPool);
    }

    @Override
    public void onItemClick(View view, int postion) {
        Toast.makeText(this, (String) listItem.get(postion).get("ItemTitle"), Toast.LENGTH_SHORT).show();
    }
}
