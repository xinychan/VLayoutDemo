package net.ubpost.www.vlayoutdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * vlayout 的使用
 * 1-引入依赖
 * 2-RecyclerView使用VirtualLayoutManager
 * 3-创建自定义Adapter，继承自DelegateAdapter，或者继承自VirtualLayoutAdapter（均来自V-Layout的Adapter）
 * 4-创建一个或多个LayoutHelper，实现响应的布局
 * 5-自定义Adapter中使用LayoutHelper，有多少个LayoutHelper就新建多少个自定义Adapter
 * 6-创建List<DelegateAdapter.Adapter>，将新建的自定义Adapter全部添加到list
 * 7-创建DelegateAdapter，通过delegateAdapter.setAdapters，将上述包含了多个自定义Adapter的list设置进去
 * 8-RecyclerView使用DelegateAdapter适配器：recyclerView.setAdapter(delegateAdapter);
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

//以下为不同的LayoutHelper的类型
//    private RecyclerView recyclerView;//RecyclerView
//    private VirtualLayoutManager layoutManager;//VirtualLayoutManager布局管理器
//    private RecyclerView.RecycledViewPool viewPool;//RecycledViewPool复用回收池
//    private MyAdapter Adapter_linearLayout;//线性布局
//    private MyAdapter Adapter_GridLayout;//网格布局
//    /**
//     * 固定在屏幕某个位置，且不可拖拽 & 不随页面滚动而滚动
//     */
//    private MyAdapter Adapter_FixLayout;//固定布局
//    /**
//     * 固定在屏幕某个位置，且不可拖拽 & 不随页面滚动而滚动（继承自固定布局（FixLayoutHelper））
//     唯一不同的是，可以自由设置该Item什么时候显示（到顶部显示 / 到底部显示），可如下图：（左上角）
//     需求场景：到页面底部显示”一键到顶部“的按钮功能
//     */
//    private MyAdapter Adapter_ScrollFixLayout;//可选显示的固定布局
//    /**
//     * 可随意拖动，但最终会被吸边到两侧
//     不随页面滚动而移动
//     */
//    private MyAdapter Adapter_FloatLayout;//浮动布局
//    /**
//     * 可理解为只有一行的线性布局
//     */
//    private MyAdapter Adapter_ColumnLayout;//栏格布局
//    /**
//     * 布局只有一栏，该栏只有一个Item
//     */
//    private MyAdapter Adapter_SingleLayout;//通栏布局
//    /**
//     * 将布局分为不同比例，最多是1拖4
//     */
//    private MyAdapter Adapter_onePlusNLayout;//一拖N布局
//    /**
//     * 布局只有一个Item，显示逻辑如下：
//     * 当它包含的组件处于屏幕可见范围内时，像正常的组件一样随页面滚动而滚动
//     当组件将要被滑出屏幕返回的时候，可以吸到屏幕的顶部或者底部，实现一种吸住的效果
//     */
//    private MyAdapter Adapter_StickyLayout;//吸边布局
//    private MyAdapter Adapter_StaggeredGridLayout;//瀑布流布局
//    private ArrayList<HashMap<String, Object>> listItem;//存放的数据

    private MainActivity mContext;
    private Button btn01;
    private Button btn02;
    private Button btn03;
    private Button btn04;
    private Button btn05;
    private Button btn06;
    private Button btn07;
    private Button btn08;
    private Button btn09;
    private Button btn10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        setButton();
        btnSetOnclick();
    }

    private void btnSetOnclick() {
        btn01.setOnClickListener(this);
        btn02.setOnClickListener(this);
        btn03.setOnClickListener(this);
        btn04.setOnClickListener(this);
        btn05.setOnClickListener(this);
        btn06.setOnClickListener(this);
        btn07.setOnClickListener(this);
        btn08.setOnClickListener(this);
        btn09.setOnClickListener(this);
        btn10.setOnClickListener(this);
    }

    private void setButton() {
        btn01 = (Button) findViewById(R.id.btn01_main);
        btn02 = (Button) findViewById(R.id.btn02_main);
        btn03 = (Button) findViewById(R.id.btn03_main);
        btn04 = (Button) findViewById(R.id.btn04_main);
        btn05 = (Button) findViewById(R.id.btn05_main);
        btn06 = (Button) findViewById(R.id.btn06_main);
        btn07 = (Button) findViewById(R.id.btn07_main);
        btn08 = (Button) findViewById(R.id.btn08_main);
        btn09 = (Button) findViewById(R.id.btn09_main);
        btn10 = (Button) findViewById(R.id.btn10_main);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn01_main:
                toNextActivity(Btn01Activity.class);
                break;
            case R.id.btn02_main:
                toNextActivity(Btn02Activity.class);
                break;
            case R.id.btn03_main:
                toNextActivity(Btn03Activity.class);
                break;
            case R.id.btn04_main:
                toNextActivity(Btn04Activity.class);
                break;
            case R.id.btn05_main:
                toNextActivity(Btn05Activity.class);
                break;
            case R.id.btn06_main:
                toNextActivity(Btn06Activity.class);
                break;
            case R.id.btn07_main:
                toNextActivity(Btn07Activity.class);
                break;
            case R.id.btn08_main:
                toNextActivity(Btn08Activity.class);
                break;
            case R.id.btn09_main:
                toNextActivity(Btn09Activity.class);
                break;
            case R.id.btn10_main:
                toNextActivity(Btn10Activity.class);
                break;
        }
    }

    public void toNextActivity(Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(mContext, cls);
        startActivity(intent);
    }
}
