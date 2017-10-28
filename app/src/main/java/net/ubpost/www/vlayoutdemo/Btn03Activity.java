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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * 固定布局（FixLayoutHelper）
 */
public class Btn03Activity extends AppCompatActivity implements MyItemClickListener{

    private Btn03Activity mContext;
    private RecyclerView recyclerView;
    private VirtualLayoutManager layoutManager;
    private RecyclerView.RecycledViewPool viewPool;
    private MyAdapter adapter_linearLayout;
    private MyAdapter adapter_fixLayout;
    private List<HashMap<String,Object>> listItem;
    private List<HashMap<String,Object>> listItem2;//固定布局数据源
    private LinearLayoutHelper linearLayoutHelper;//线性布局
    private FixLayoutHelper fixLayoutHelper;//固定布局

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn03);
        mContext = this;
        //listItem = DataDao.getList20();
        listItem = DataDao.getList100();
        listItem2 = DataDao.getList20();
        recyclerView = (RecyclerView) findViewById(R.id.rv_btn03);
        baseSetup();
        setLayoutHelper();
        setAdapter();
        bindAdapter();
    }

    //绑定适配器
    private void bindAdapter() {
        List<DelegateAdapter.Adapter> adapters = new LinkedList<>();
        adapters.add(adapter_linearLayout);
        adapters.add(adapter_fixLayout);
        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager);
        delegateAdapter.setAdapters(adapters);
        recyclerView.setAdapter(delegateAdapter);
    }

    //设置适配器
    private void setAdapter() {
        //线性布局的适配器
        adapter_linearLayout = new MyAdapter(mContext,linearLayoutHelper,20,listItem);
        //此处传入的数值如果大于等于listItem的总大小，则下一个布局（fixLayout）会报下标越界异常
        //java.lang.IndexOutOfBoundsException: Index: 20, Size: 20
        adapter_linearLayout.setOnItemClickListener(this);
        //固定布局的适配器
        adapter_fixLayout = new MyAdapter(mContext,fixLayoutHelper,1,listItem2){
            // 这里虽然传入listItem2，但实际上adapter中的存放的数据仍然是首次传入的listItem数据
            // 此处fixLayout布局中展示的为adapter_linearLayout中listItem的下一条数据，即第21条数据
            // 但在fixLayout中的位置为0
            // 设置需要展示的数据总数,此处设置是1
            // 为了展示效果,通过重写onBindViewHolder()将布局的第一个数据设置为Fix
            @Override
            public void onBindViewHolder(MainViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                if (position == 0) {
                    holder.tv.setText("Fix");
                }
            }
        };
        adapter_fixLayout.setOnItemClickListener(this);

    }

    //设置布局
    private void setLayoutHelper() {
        /*
         * 先设置一个线性布局
         * 用以滑动
         */
        linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setBgColor(getResources().getColor(R.color.colorYellow));
        /**
         * 固定布局
         */
        fixLayoutHelper = new FixLayoutHelper(FixLayoutHelper.TOP_LEFT,40,40);
        // 参数说明:
        // 参数1:设置吸边时的基准位置(alignType) - 有四个取值:TOP_LEFT(默认), TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
        // 参数2:基准位置的偏移量x
        // 参数3:基准位置的偏移量y
        /**
         * 所有布局的公共属性
         */
        //fixLayoutHelper.setItemCount(1);// 设置布局里Item个数
        // 从设置Item数目的源码可以看出，一个FixLayoutHelper只能设置一个
        //fixLayoutHelper.setPadding(50,50,50,50);// 设置LayoutHelper的子元素相对LayoutHelper边缘的距离
        //fixLayoutHelper.setMargin(50,50,50,50);// 设置LayoutHelper边缘相对父控件（即RecyclerView）的距离
        fixLayoutHelper.setBgColor(getResources().getColor(R.color.colorGreen));// 设置背景颜色
        //设置背景颜色，对fixLayout布局没有起作用
        //fixLayoutHelper.setAspectRatio(3);// 设置布局内每行布局的宽与高的比
        /**
         * fixLayoutHelper特有属性
         */
        fixLayoutHelper.setAlignType(FixLayoutHelper.BOTTOM_LEFT);//设置吸边时的基准位置(alignType)
        fixLayoutHelper.setX(100);// 设置基准位置的横向偏移量X
        fixLayoutHelper.setY(100);// 设置基准位置的纵向偏移量Y
        //这些属性替换构造方法中的参数
    }

    //基本设置
    private void baseSetup() {
        layoutManager = new VirtualLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        viewPool  = new RecyclerView.RecycledViewPool();
        viewPool.setMaxRecycledViews(0,10);
        recyclerView.setRecycledViewPool(viewPool);
    }

    @Override
    public void onItemClick(View view, int postion) {
        Toast.makeText(this, (String) listItem.get(postion).get("ItemTitle"), Toast.LENGTH_SHORT).show();
    }
}
