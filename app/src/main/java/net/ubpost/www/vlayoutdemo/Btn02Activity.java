package net.ubpost.www.vlayoutdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * 网格布局（GridLayoutHelper）
 */
public class Btn02Activity extends AppCompatActivity implements MyItemClickListener{

    private Btn02Activity mContext;
    private RecyclerView recyclerView;
    private VirtualLayoutManager layoutManager;
    private RecyclerView.RecycledViewPool viewPool;
    private MyAdapter adapter;
    private List<HashMap<String, Object>> listItem;//存放的数据
    private GridLayoutHelper gridLayoutHelper;//网格布局

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn02);
        mContext = this;
        listItem = DataDao.getList100();
        baseSetup();
        setLayoutHelper();
        setAdapter();
        bindAdapter();
    }

    //绑定适配器
    private void bindAdapter() {
        //将生成的LayoutHelper 交给Adapter，并绑定到RecyclerView 对象
        List<DelegateAdapter.Adapter> adapters = new LinkedList<>();
        adapters.add(adapter);
        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager);
        delegateAdapter.setAdapters(adapters);
        recyclerView.setAdapter(delegateAdapter);
    }

    //设置适配器
    private void setAdapter() {
        // 创建自定义的Adapter对象 & 绑定数据 & 绑定对应的LayoutHelper进行布局绘制
        // 参数2:绑定对应的LayoutHelper
        // 参数3:传入该布局需要显示的数据个数
        // 参数4:传入需要绑定的数据
        adapter = new MyAdapter(mContext,gridLayoutHelper,32,listItem);
        adapter.setOnItemClickListener(this);// 设置每个Item的点击事件
    }

    //设置布局
    private void setLayoutHelper() {
        //创建一个网格布局；在构造函数设置每行的网格个数
        gridLayoutHelper = new GridLayoutHelper(5);
        /**
         * 所有布局的公共属性
         */
        //gridLayoutHelper.setItemCount(6);//设置布局里Item个数
        //gridLayoutHelper.setPadding(50,50,50,50);//设置LayoutHelper的子元素相对LayoutHelper边缘的距离
        //gridLayoutHelper.setMargin(50,50,50,50);//设置LayoutHelper边缘相对父控件（即RecyclerView）的距离
        gridLayoutHelper.setBgColor(getResources().getColor(R.color.colorYellow));//设置背景颜色
        //gridLayoutHelper.setAspectRatio(6);//设置布局内每行布局的宽与高的比
        /**
         * gridLayoutHelper特有属性
         */
        //gridLayoutHelper.setWeights(new float[]{20,20,30,10,10});//设置每行中 每个网格宽度 占 每行总宽度 的比例
        //默认情况下，每行中每个网格中的宽度相等;如果布局中有4列，那么weights的长度也应该是4；
        //长度大于4，多出的部分不参与宽度计算；如果小于4，不足的部分默认平分剩余的空间
        //这里每行3个网格，传入3个值，各个值总和为100;总和是100，否则布局会超出容器宽度
        //gridLayoutHelper.setVGap(50);// 控制子元素之间的垂直间距
        //gridLayoutHelper.setHGap(40);// 控制子元素之间的水平间距
        gridLayoutHelper.setAutoExpand(false);//是否自动填充空白区域
        //作用：当一行里item的个数 < （每行网格列数 - spanCount/setSpanSizeLookup ）时，是否自动填满空白区域
        //若autoExpand=true，那么视图的总宽度会填满可用区域；否则会在屏幕上留空白区域；默认为true
        gridLayoutHelper.setSpanCount(6);// 设置每行多少个网格；这里设置后，构造函数的网格数不起作用
        // 通过自定义SpanSizeLookup来控制某个Item的占网格个数
        gridLayoutHelper.setSpanSizeLookup(new GridLayoutHelper.SpanSizeLookup(){

            @Override
            public int getSpanSize(int position) {
                if (position > 7) {
                    return 3;
                    // 第7个位置后,每个Item占3个网格
                } else {
                    return 2;
                    // 第7个位置前,每个Item占2个网格
                }
            }
        });

    }

    //基本设置
    private void baseSetup() {
        //创建RecyclerView & VirtualLayoutManager 对象并进行绑定
        recyclerView = (RecyclerView) findViewById(R.id.rv_btn02);
        layoutManager = new VirtualLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        //设置回收复用池大小
        viewPool = new RecyclerView.RecycledViewPool();
        viewPool.setMaxRecycledViews(0,10);
        recyclerView.setRecycledViewPool(viewPool);
    }

    @Override
    public void onItemClick(View view, int postion) {
        Toast.makeText(this, (String) listItem.get(postion).get("ItemTitle"), Toast.LENGTH_SHORT).show();
    }
}
