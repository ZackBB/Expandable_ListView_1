package com.example.expandable_listview_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private String[] menu_group = {"Main course","Drink"};
    private String[][] menu_child = {{"steak","Noodle","Rice"},{"Coffee","Black tea","Ice cream","Cola"}};
    private int[][] pic_child = {{R.drawable.hamburger,R.drawable.french_fries,R.drawable.pizza_icon},
                                 {R.drawable.latte,R.drawable.blacktea,R.drawable.icecream,R.drawable.cola_icon}};
    private String[][] pic_child_text = {{"steak","Noodle","Rice"},{"Coffee","Black tea","Ice cream","Cola"}};
    private ExpandableListView expandListView;
    private List<Map<String, String>> group_List;
    private List<List<Map<String, String>>> childList;


    private List<List<Map<String, Object>>> newchildList; //加圖片的



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String asd;

        expandListView = (ExpandableListView)findViewById(R.id.expanableLV_id);

        //主資料的List
        group_List = new ArrayList<Map<String,String>>();
        //存放第一層資料
        for(int i=0; i<menu_group.length;i++){
            Map<String, String> data = new HashMap<String, String>();
            data.put("groupkey",menu_group[i]);
            group_List.add(data);
        }

        // 子資料的List
        childList = new ArrayList<List<Map<String,String>>>();
        //存放第二層資料的選擇 (第二層的選擇數 配合第一層的選項數)
        for(int i=0;i<menu_group.length;i++){

            List<Map<String, String>> child = new ArrayList<Map<String, String>>();
            //存放第二層資料
            for(String food:menu_child[i]){
                Map<String, String> childData = new HashMap<String, String>();
                childData.put("childkey",food);
                child.add(childData);
            }
            childList.add(child);
        }

        //----------------------------------------------------------
        //加圖片的第二層資料
        newchildList = new ArrayList<List<Map<String,Object>>>();

        for(int i=0;i<menu_group.length;i++){
            List<Map<String, Object>> child = new ArrayList<Map<String, Object>>();
//            for(String food  : menu_child[i]){
//                Map<String, Object> childData = new HashMap<String, Object>();
//                childData.put("foodkey",food);
//                for(int pic : pic_child[i]){
//                    childData.put("pickey",pic);
//                }
//                child.add(childData);
//            }
            for(int j=0;j<menu_child[i].length;j++){
                Map<String, Object> childData = new HashMap<String, Object>();
                String name = menu_child[i][j];
                String pic = pic_child_text[i][j];
                childData.put("childname",name);
                childData.put("childpic",pic);
                child.add(childData);
            }
            newchildList.add(child);
        }



        // 傳送器的設定  無圖片
        SimpleExpandableListAdapter myAdapter = new SimpleExpandableListAdapter(MainActivity.this, group_List,
                R.layout.main_item, new String[]{"groupkey"}, new int[]{R.id.textView_mainitem},
                childList, R.layout.child_item, new String[]{"childkey"}, new int[]{R.id.textView_childItem});

        // 傳送器的設定  有圖片 (無法放圖片、改成另一個textview
        SimpleExpandableListAdapter newAdapter = new SimpleExpandableListAdapter(MainActivity.this, group_List,
                R.layout.main_item, new String[]{"groupkey"}, new int[]{R.id.textView_mainitem},
                newchildList, R.layout.child_item_pic, new String[]{"childname","childpic"}, new int[]{R.id.textView_child_pic,R.id.textView_child_pic_2});

        expandListView.setAdapter(newAdapter);
//        expandListView.setAdapter(myAdapter);


        // 監聽
        expandListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                String mainFood = menu_group[groupPosition];
                String childFood = menu_child[groupPosition][childPosition];
                Toast.makeText(MainActivity.this, mainFood+":"+childFood, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}