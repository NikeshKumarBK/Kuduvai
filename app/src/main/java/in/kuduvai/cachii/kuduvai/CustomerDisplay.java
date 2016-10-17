package in.kuduvai.cachii.kuduvai;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomerDisplay extends ActionBarActivity implements View.OnClickListener{

    TextView editTextName;
    TextView editTextAddr;
    TextView editTextContactno;
    TextView txtTotalBal;
    private int _Student_Id=0;
    ListView listView;
    SQLiteDatabase sqLiteDatabase;
    SqlCommands repo;
    DBHelper dbHelper;
    Cursor cursor;
    CustomerDisplayListAdapter listDataAdapterCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_display);

        editTextName = (TextView) findViewById(R.id.TxtViewDisplayName);
        editTextAddr = (TextView) findViewById(R.id.TxtViewDisplayAddr);
        editTextContactno = (TextView) findViewById(R.id.TxtViewDisplayContact);
        txtTotalBal=(TextView)findViewById(R.id.TextViewDisplayBalance);

        _Student_Id =0;
        Intent intent = getIntent();
        _Student_Id =intent.getIntExtra("student_Id", 0);

        SqlCommands repo = new SqlCommands(this);
        Can student = new Can();
        student = repo.getStudentById(_Student_Id);

        editTextContactno.setText(String.valueOf(student.contactno));
        editTextName.setText(student.name);
        editTextAddr.setText(student.address);

        listView=(ListView)findViewById(R.id.CustDisplayList);
        dbHelper=new DBHelper(getApplicationContext());
        repo=new SqlCommands(getApplicationContext());
        sqLiteDatabase = dbHelper.getReadableDatabase();

        int tot_balance=repo.tot_balance(student.name,_Student_Id);
        txtTotalBal.setText(""+tot_balance);

        cursor=repo.getCustomerDisplay(sqLiteDatabase, student.name, _Student_Id);
        listDataAdapterCustomer=new CustomerDisplayListAdapter(getApplicationContext(),R.layout.view_customer_details_list);
        listView.setAdapter(listDataAdapterCustomer);
        if (cursor.moveToFirst())
        {
            do{

                String date,can_count,paid,due;
                date=cursor.getString(0);
                can_count=cursor.getString(1);
                paid=cursor.getString(2);
                due=cursor.getString(3);

                Log.e("Display",date);
                Log.e("Display",can_count);
                Log.e("Display",paid);
                Log.e("Display", due);

                CustomerListDataProvider dataProviderCustomer=new CustomerListDataProvider(date,can_count,paid,due);
                listDataAdapterCustomer.add(dataProviderCustomer);

            }while (cursor.moveToNext());
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.customer_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_delete) {
            Toast.makeText(getApplicationContext(),"Customer is Deleted",Toast.LENGTH_SHORT).show();
            SqlCommands repo = new SqlCommands(this);
            repo.delete(_Student_Id);
            Intent intent=new Intent(CustomerDisplay.this,MainActivity.class);
            startActivity(intent);
            finish();
            //Toast.makeText(this, "Customer Deleted", Toast.LENGTH_SHORT);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClick(View view) {


    }

}