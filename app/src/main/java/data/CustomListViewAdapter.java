package data;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jere.calcounter.R;
import com.example.jere.calcounter.food_items_details;

import java.util.ArrayList;

import model.Food;

/**
 * Created by jere on 7/23/2016.
 */
public class CustomListViewAdapter extends ArrayAdapter<Food> {
    private Activity activity;
    private int layoutresource;
    private ArrayList<Food> foodlist =new ArrayList<>();

    public CustomListViewAdapter(Activity act, int resource, ArrayList<Food> data) {
        super(act, resource, data);
        layoutresource=resource;
        activity=act;
        foodlist=data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return foodlist.size();
    }

    @Override
    public Food getItem(int position) {
        return foodlist.get(position);
    }

    @Override
    public int getPosition(Food item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row =convertView;
        ViewHolder holder= null;
        if(row==null ||(row.getTag()==null)){
            LayoutInflater inflater =LayoutInflater.from(activity);
            row=inflater.inflate(layoutresource,null);
            holder = new ViewHolder();
            holder.foodname =(TextView)row.findViewById(R.id.name);
            holder.foodcalories=(TextView)row.findViewById(R.id.calories);
            holder.fooddate=(TextView)row.findViewById(R.id.dateText);

            row.setTag(holder);
        }else{
            holder= (ViewHolder)row.getTag();
        }
        holder.food=getItem(position);
        holder.foodname.setText(holder.food.getFoodName());
        holder.fooddate.setText(holder.food.getRecordDate());
        holder.foodcalories.setText(String.valueOf(holder.food.getCalories()));

        final ViewHolder finalHolder = holder;
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(activity, food_items_details.class);
                Bundle mbundle =new Bundle();
                mbundle.putSerializable("userobj", finalHolder.food);
                i.putExtras(mbundle);

                activity.startActivity(i);
            }
        });
        return row;
    }

    public class ViewHolder{
        Food food;
        TextView foodname;
        TextView foodcalories;
        TextView fooddate;
    }
}
