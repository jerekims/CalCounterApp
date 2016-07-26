package com.example.jere.calcounter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import data.DatabaseHandler;
import model.Food;

public class MainActivity extends AppCompatActivity {
    private EditText mFoodName,mCalories;
    private Button mbtnsave;
    private  Button nbtnone;
    private Button mbtntwo;
    private Button mbtnthree;
    private DatabaseHandler dba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFoodName =(EditText)findViewById(R.id.foodET);
        mCalories=(EditText)findViewById(R.id.caloriesET);
        mbtnsave=(Button)findViewById(R.id.submit);
        dba =new DatabaseHandler(getApplicationContext());

        mbtnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToDatabase();
            }
        });

    }

    private void saveToDatabase() {
        String fname=mFoodName.getText().toString().trim();
        String calstring=mCalories.getText().toString().trim();
        int cals=Integer.parseInt(calstring);
        if((fname.equals(""))||(calstring.equals(""))){
            Toast.makeText(getApplicationContext(),"No empty field allowed",Toast.LENGTH_LONG).show();
        }
        else{
            Food food =new Food();
            food.setFoodName(fname);
            food.setCalories(cals);

            dba.addfood(food);
            dba.close();

            //clearing inout fields
            mFoodName.setText("");
            mCalories.setText("");

//           move the user to another screen
            Intent intent=new Intent(getApplicationContext(),DisplayFoodsActivity.class);
            startActivity(intent);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
