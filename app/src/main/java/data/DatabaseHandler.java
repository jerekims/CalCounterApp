package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.Food;

/**
 * Created by jere on 7/23/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private  final ArrayList<Food> foodlist =new ArrayList<>();

    public  DatabaseHandler (Context context){
        super(context,Constants.DATABASE_NAME,null,Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_FOOD="CREATE TABLE "+ Constants.TABLE_NAME + "("
                + Constants.KEY_ID +" INTEGER PRIMARY KEY," + Constants.FOOD_NAME +
                " TEXT," + Constants.FOOD_CALORIES_NAME + " INTEGER," + Constants.DATE_NAME +
                " Long);";
        db.execSQL(CREATE_TABLE_FOOD);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF EXISTS" + Constants.TABLE_NAME);
        onCreate(db);
    }

    //gettin total fod items
    public int getTotalItems(){
        int totalItems=0;
        String query="SELECT * FROM " +Constants.TABLE_NAME;
        SQLiteDatabase dba =this.getReadableDatabase();
        Cursor cursor =dba.rawQuery(query,null);
        totalItems =cursor.getCount();

        cursor.close();
        dba.close();
        return totalItems;
    }

    //getting total calories consumed
    public int getCalories(){
        int totalCalories=0;
        String query= "SELECT  SUM("+ Constants.FOOD_CALORIES_NAME + ")" +
                "FROM " + Constants.TABLE_NAME;
        SQLiteDatabase dba=this.getReadableDatabase();
        Cursor cursor=dba.rawQuery(query,null);
        if (cursor.moveToFirst()){
            totalCalories=cursor.getCount();
        }
        cursor.close();
        dba.close();
        return totalCalories;
    }
    //deleting food items
    public void delete (int id){
        SQLiteDatabase dba =this.getWritableDatabase();
        dba.delete(Constants.TABLE_NAME, Constants.KEY_ID + "=?",
                new String[]{String.valueOf(id)});

        dba.close();
    }

    //adding food items
    public void addfood(Food food){
        SQLiteDatabase dba =this.getWritableDatabase();
        ContentValues contentvalues =new ContentValues();
        contentvalues.put(Constants.FOOD_NAME,food.getFoodName());
        contentvalues.put(Constants.FOOD_CALORIES_NAME,food.getCalories());
        contentvalues.put(Constants.DATE_NAME,System.currentTimeMillis());

        dba.insert(Constants.TABLE_NAME, null, contentvalues);
        Log.v("Added","successful");
        dba.close();
    }

//    getting all food items
    public ArrayList<Food> getFoods(){
        SQLiteDatabase dba =this.getReadableDatabase();
        Cursor cursor=dba.query(Constants.TABLE_NAME,
                        new String[]
                                {Constants.KEY_ID,Constants.FOOD_NAME,
                                        Constants.FOOD_CALORIES_NAME,
                                        Constants.DATE_NAME},null,null,null,null,Constants.DATE_NAME +" DESC");
//        lopping through the products
        if(cursor.moveToFirst()){
            do {
                Food food =new Food();
                food.setFoodId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));
                food.setFoodName(cursor.getString(cursor.getColumnIndex(Constants.FOOD_NAME)));
                food.setCalories(cursor.getInt(cursor.getColumnIndex(Constants.FOOD_CALORIES_NAME)));

                DateFormat dateformat =DateFormat.getDateInstance();
                String date=dateformat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.DATE_NAME))).getTime());

                food.setRecordDate(date);
                foodlist.add(food);

            }while(cursor.moveToNext());
        }
        cursor.close();
        dba.close();

        return foodlist;
    }
}
