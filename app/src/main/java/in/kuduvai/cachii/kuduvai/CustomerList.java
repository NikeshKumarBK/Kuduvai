package in.kuduvai.cachii.kuduvai;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerList extends ListActivity implements View.OnClickListener{

    // Button btnAdd,btnGetAll;
    TextView student_Id;
    Button btnNewCust;

    @Override
    public void onClick(View view) {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        btnNewCust=(Button)findViewById(R.id.btnNewCust);
        btnNewCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CustomerList.this,NewCustomer.class);
                startActivity(intent);
                finish();
            }
        });
      /*  btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnGetAll = (Button) findViewById(R.id.btnGetAll);
        btnGetAll.setOnClickListener(this);*/

        SqlCommands repo = new SqlCommands(this);

        ArrayList<HashMap<String, String>> studentList =  repo.getStudentList();
        if(studentList.size()!=0) {
            ListView lv = getListView();
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                    student_Id = (TextView) view.findViewById(R.id.customer_id);
                    String studentId = student_Id.getText().toString();
                    Intent objIndent = new Intent(getApplicationContext(),CustomerDisplay.class);
                    objIndent.putExtra("student_Id", Integer.parseInt( studentId));
                    startActivity(objIndent);
                    finish();
                    Log.e("Check", "StudentDetail started");
                }
            });
            ListAdapter adapter = new SimpleAdapter( CustomerList.this,studentList, R.layout.view_customer, new String[] { "id","name"}, new int[] {R.id.customer_id, R.id.customer_name});

            setListAdapter(adapter);
        }else{
            Toast.makeText(this, "No Customer!", Toast.LENGTH_SHORT).show();
         }

    }

    /*public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_customer) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
