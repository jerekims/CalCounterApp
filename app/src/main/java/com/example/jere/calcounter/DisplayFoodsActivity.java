package com.example.jere.calcounter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import data.CustomListViewAdapter;
import data.DatabaseHandler;
import model.Food;
import utils.Utils;

public class DisplayFoodsActivity extends AppCompatActivity {
    private DatabaseHandler dba;
    private ArrayList<Food> dbFoods = new ArrayList<>();
    private CustomListViewAdapter foodAdapter;
    private ListView mlistview;
    private Food myfood;
    private TextView mdcalories,mdfooditems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_foods);
        mlistview=(ListView)findViewById(R.id.list);
        mdcalories=(TextView)findViewById(R.id.totalcalories);
        mdfooditems=(TextView)findViewById(R.id.fooditems);

        refreshData();

    }

    private void refreshData() {
        dbFoods.clear();
        dba= new DatabaseHandler(getApplicationContext());

        ArrayList<Food> foodsFromDB = dba.getFoods();
        int calsvalue=dba.getCalories();
        int totalitems=dba.getTotalItems();

        String formattedvalue= Utils.formatNumber(calsvalue);
        String formattedItems =Utils.formatNumber(totalitems);

        mdcalories.setText("Total Calories"+formattedvalue);
        mdfooditems.setText("Total Food items" + formattedItems);

        for(int i =0;i <foodsFromDB.size();i++){
            String fname=foodsFromDB.get(i).getFoodName();
            int cal =foodsFromDB.get(i).getCalories();
            int foodid =foodsFromDB.get(i).getFoodId();
            String datetext=foodsFromDB.get(i).getRecordDate();


            myfood =new Food();
            myfood.setFoodName(fname);
            myfood.setCalories(cal);
            myfood.setFoodId(foodid);
            myfood.setRecordDate(datetext);

            dbFoods.add(myfood);
        }
        dba.close();
        //setting  the adapater
        foodAdapter= new CustomListViewAdapter(DisplayFoodsActivity.this,R.layout.list_row,dbFoods);
        mlistview.setAdapter(foodAdapter);
        foodAdapter.notifyDataSetChanged();
    }

}
