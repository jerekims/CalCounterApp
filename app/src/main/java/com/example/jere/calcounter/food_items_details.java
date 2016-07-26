package com.example.jere.calcounter;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import model.Food;

public class food_items_details extends AppCompatActivity {
    private TextView mtxtname,mtxtcalories,mtxtdate;
    private Button msharbtn;
    int Foodid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_items_details);
        mtxtname =(TextView)findViewById(R.id.detFoodName);
        mtxtcalories=(TextView)findViewById(R.id.detCaloriesValue);
        mtxtdate=(TextView)findViewById(R.id.detCaloriesDate);
        msharbtn=(Button)findViewById(R.id.detbtnshare);

        Food food =(Food)getIntent().getSerializableExtra("userobj");

        mtxtname.setText(food.getFoodName());
        mtxtcalories.setText(String.valueOf(food.getCalories()));
        mtxtdate.setText(food.getRecordDate());

        Foodid=food.getFoodId();

        mtxtcalories.setTextSize(29.6f);
        msharbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });


    }

    public  void share(){
        StringBuilder datastring =new StringBuilder();

        String name=mtxtname.getText().toString();
        String cals=mtxtcalories.getText().toString();
        String date =mtxtdate.getText().toString();

        datastring.append("Food" +name+ "\n");
        datastring.append("Calories" + cals + "\n");
        datastring.append("Eaten on" + date);

        Intent i =new Intent(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_SUBJECT,"Calories intake");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"kirikak19@gmail.com","orregumathi@gmail.com"});
        i.putExtra(Intent.EXTRA_TEXT,datastring.toString());

        try {
            startActivity(Intent.createChooser(i,"Send Mail..."));
        }catch (ActivityNotFoundException e){
            Toast.makeText(getApplicationContext(),"Email has not been successfully sent",Toast.LENGTH_LONG).show();
        }
    }

}
