package com.smartcheck.savings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.util.Calendar;
import com.parse.Parse;
import com.parse.ParseObject;


public class MainActivity extends AppCompatActivity {

    TextView submit;
    DatePicker dp;
    EditText name,amount;
    RadioGroup rd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build());



        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        submit=findViewById(R.id.submit);
        dp=findViewById(R.id.datePicker1);
        name=findViewById(R.id.name);
        amount=findViewById(R.id.amount);
        rd=findViewById(R.id.groupradio);


        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n=name.getText().toString();
                String a=amount.getText().toString();
                String t="";
                RadioButton rg1=findViewById(R.id.radia_id1);
                RadioButton rg2=findViewById(R.id.radia_id2);
                if(rg1.isChecked()) t="Online";
                else t="Cash";


                if(n.length()==0 || a.length()==0) {
                    Toast.makeText(MainActivity.this, "Fill all the details", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),dashboard.class));
                }



                else {
                    ParseObject firstObject = new ParseObject("FirstClass");
                    firstObject.put("name", n);
                    firstObject.put("amount", a);
                    firstObject.put("type", t);
                    firstObject.put("date", getCurrentDate());
                    firstObject.saveInBackground(e -> {
                        if (e != null) {
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "message saved", Toast.LENGTH_SHORT).show();
                        }
                    });
                    startActivity(new Intent(getApplicationContext(),dashboard.class));
                }

            }
        });





        }

    public String getCurrentDate(){
        StringBuilder builder=new StringBuilder();;
        builder.append((dp.getMonth() + 1)+"/");//month is 0 based
        builder.append(dp.getDayOfMonth()+"/");
        builder.append(dp.getYear());
        return builder.toString();

    }
}