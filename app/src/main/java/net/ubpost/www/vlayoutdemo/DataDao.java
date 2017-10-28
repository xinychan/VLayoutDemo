package net.ubpost.www.vlayoutdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 数据源
 */

public class DataDao {

    //返回100个数据
    public static List<HashMap<String, Object>> getList100() {
        List<HashMap<String, Object>> listItem = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("ItemTitle", "第" + i + "行");
            map.put("ItemImage", R.mipmap.ic_launcher);
            listItem.add(map);
        }
        return listItem;
    }

    //返回20个数据
    public static List<HashMap<String, Object>> getList20() {
        List<HashMap<String, Object>> listItem = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("ItemTitle", "第" + i + "行");
            map.put("ItemImage", R.mipmap.ic_launcher);
            listItem.add(map);
        }
        return listItem;
    }
}
