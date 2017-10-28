package net.ubpost.www.vlayoutdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;

import java.util.HashMap;
import java.util.List;

/**
 * V - Layout的Adapter
 * 继承 自 DelegateAdapter （此处用法）
 * 或者 继承 自 VirtualLayoutAdapter
 */

public class MyAdapter extends DelegateAdapter.Adapter<MyAdapter.MainViewHolder> {

    // 使用DelegateAdapter首先就是要自定义一个它的内部类Adapter，让LayoutHelper和需要绑定的数据传进去
    // 此处的Adapter和普通RecyclerView定义的Adapter只相差了一个onCreateLayoutHelper()方法，其他的都是一样的做法.

    private List<HashMap<String,Object>> listItem;// 用于存放数据列表

    private Context context;
    private LayoutHelper layoutHelper;
    private RecyclerView.LayoutParams layoutParams;
    private int count = 0;

    private MyItemClickListener myItemClickListener;// 用于设置Item点击事件

    //构造函数(传入每个的数据列表 & 展示的Item数量)
    public MyAdapter(Context context, LayoutHelper layoutHelper, int count, List<HashMap<String, Object>> listItem) {
        this(context,layoutHelper,count,new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,300),listItem);
    }

    public MyAdapter(Context context, LayoutHelper layoutHelper, int count,
                     @NonNull RecyclerView.LayoutParams layoutParams, List<HashMap<String,Object>> listItem) {
        this.context = context;
        this.layoutHelper = layoutHelper;
        this.count = count;
        this.layoutParams = layoutParams;
        this.listItem = listItem;
    }

    // 此处的Adapter和普通RecyclerView定义的Adapter只相差了一个onCreateLayoutHelper()方法
    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        MainViewHolder mainViewHolder = new MainViewHolder(itemView);
        return mainViewHolder;
    }

    // 绑定Item的数据
    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
            holder.tv.setText((String)listItem.get(position).get("ItemTitle"));
            holder.iv.setImageResource((Integer) listItem.get(position).get("ItemImage"));
    }

    // 返回Item数目
    @Override
    public int getItemCount() {
        return count;
    }

    // 设置Item的点击事件
    // 绑定MainActivity传进来的点击监听器
    public void setOnItemClickListener(MyItemClickListener listener) {
        myItemClickListener = listener;
    }

    //定义Viewholder
    class MainViewHolder extends RecyclerView.ViewHolder{

        public TextView tv;
        public ImageView iv;

        public MainViewHolder(View itemView) {
            super(itemView);
            // 绑定视图
            tv = (TextView) itemView.findViewById(R.id.Item);
            iv = (ImageView) itemView.findViewById(R.id.Image);
            //监听到点击就回调MainActivity的onItemClick函数
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (myItemClickListener != null) {
                        myItemClickListener.onItemClick(view,getPosition());
                    }
                }
            });
        }

        public TextView getText(){
            return tv;
        }
    }

}
