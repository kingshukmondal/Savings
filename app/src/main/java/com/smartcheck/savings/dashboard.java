package com.smartcheck.savings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class dashboard extends AppCompatActivity {

    PieChart pieChart;
    List<PieEntry> l1;


    TextView on,off;


    ArrayList<Details> arr;
     int online=0,offline=0;
    RecyclerView rv;
    LinearLayoutManager lm;
    AdapterAmount adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



        pieChart=findViewById(R.id.chart);
        rv=findViewById(R.id.rv1);
        on=findViewById(R.id.tv_online);
        off= findViewById(R.id.tv_offline);
        lm = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        rv.setLayoutManager(lm);


        arr=new ArrayList<>();
        l1= new ArrayList<>();


        ParseQuery<ParseObject> query = ParseQuery.getQuery("FirstClass");
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null)
                {
                    for(int i=0;i<objects.size();i++)
                    {
                        String name =objects.get(i).getString("name");
                        String date =objects.get(i).getString("date");
                        String amount =objects.get(i).getString("amount");
                        String type =objects.get(i).getString("type");
                        String id=objects.get(i).getString("objectId");
                        arr.add(new Details(amount,name,date,type,id));
                        if(type.equals("Online"))
                        {
                            online+=Integer.valueOf(amount);
                        }
                        else
                        {
                            offline+=Integer.valueOf(amount);
                        }
                    }

                    setValue(offline,online);
                    setChart();
                    off.setText(offline+"");
                    on.setText(online+"");
                    adapter=new AdapterAmount(arr,getApplicationContext());
                    adapter.notifyDataSetChanged();
                    rv.setAdapter(adapter);
                }
                else {
                    // handling error if we get any error.
                    Toast.makeText(getApplicationContext(), "Fail to get data..", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Toast.makeText(this, arr.size()+"", Toast.LENGTH_SHORT).show();












    }

    private void setChart() {
        PieDataSet pieDataSet=new PieDataSet(l1,"Expenses");
        PieData pieData=new PieData(pieDataSet);
        final int[] MY_COLORS = {
                Color. parseColor("#B00020"),
                Color.parseColor("#FFDE03"),
        };
        ArrayList<Integer> colors = new ArrayList<>();
        for(int c: MY_COLORS) colors.add(c);
        pieDataSet.setColors(colors);
        pieDataSet.setValueTextSize(12f);
        pieChart.setData(pieData);
        pieChart.invalidate();
        pieChart.getLegend().setEnabled(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(false);
//        pieChart.setCenterText("92" );
//        pieChart.setCenterTextSize(20f);
//        pieChart.setCenterTextColor(Color.BLUE);
        pieDataSet.setDrawValues(false);
    }
    private void setValue(int i, int j) {
        l1.add(new PieEntry(i,""));
        l1.add(new PieEntry(j,""));

    }
    private void getTodoList() {


    }


}

































//////////////////////////////////////////////////////////////////////////////////
class AdapterAmount extends RecyclerView.Adapter<viewholder>
{

    ArrayList<Details> arr;
    Context con;

    AdapterAmount(ArrayList<Details> arr,Context con)
    {
        this.arr=arr;
        this.con=con;
    }
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customrv, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        String name=arr.get(position).getDet();
        String amount=arr.get(position).getAmount();
        String date=arr.get(position).getDate();
        String type=arr.get(position).getType();
        //String id=arr.get(position).getId();

        holder.setData(name,amount,date,type,con);


    }

    @Override
    public int getItemCount() {
        return arr.size();
    }
}

class viewholder extends RecyclerView.ViewHolder
{

    TextView name,amount,date;
    ImageView type;


    public viewholder(@NonNull View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.name);
        amount=itemView.findViewById(R.id.amount);
        date=itemView.findViewById(R.id.date);
        type=itemView.findViewById(R.id.type);
    }

    public void setData(String n, String a, String d, String t, Context con) {
       name.setText(n);
       amount.setText("Rs "+a);
       date.setText(d);

       if(t.equals("Cash"))
           type.setColorFilter(con.getResources().getColor(R.color.blue1));
       else
           type.setColorFilter(con.getResources().getColor(R.color.yellow));

    }
}