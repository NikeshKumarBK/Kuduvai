package in.kuduvai.cachii.kuduvai;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewCustomer extends ActionBarActivity implements View.OnClickListener{

    Button btnSave;
    Button btnClose;
    EditText editTextName,editTextnoofcans,editTextprice;
    EditText editTextAddr;
    EditText editTextContactno;
    private int _Student_Id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer);

        btnSave = (Button) findViewById(R.id.btnSave);

        btnClose = (Button) findViewById(R.id.btnClose);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAddr = (EditText) findViewById(R.id.editTextAddr);
        editTextnoofcans = (EditText) findViewById(R.id.editTextnoofcans);
        editTextprice = (EditText) findViewById(R.id.editTextPrice);
        editTextContactno = (EditText) findViewById(R.id.editTextcanGiven);

        btnSave.setOnClickListener(this);

        btnClose.setOnClickListener(this);


        _Student_Id =0;
        Intent intent = getIntent();
        _Student_Id =intent.getIntExtra("student_Id", 0);
        SqlCommands repo = new SqlCommands(this);

        int i=repo.basePrice(1);
        editTextprice.setText("" + i);

    }


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

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClick(View view) {
        if (view == findViewById(R.id.btnSave)){
            SqlCommands repo = new SqlCommands(this);
            Can student = new Can();
            student.contactno=editTextContactno.getText().toString();
            student.noofcans= Integer.parseInt(editTextnoofcans.getText().toString());
            student.price= Integer.parseInt(editTextprice.getText().toString());
            student.address= editTextAddr.getText().toString();
            student.name=editTextName.getText().toString().trim();
            student.student_ID=_Student_Id;
            //Toast.makeText(this,"Long success",Toast.LENGTH_SHORT).show();

            if (_Student_Id==0){


                _Student_Id = repo.insert(student);
                repo.createTable(_Student_Id,student.name);
                Toast.makeText(this, "New Customer Inserted "+_Student_Id, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(NewCustomer.this,MainActivity.class);
                startActivity(intent);
                finish();

            }else{

                repo.update(student);
                Toast.makeText(this,"Student Record updated",Toast.LENGTH_SHORT).show();
            }
        }else if (view== findViewById(R.id.btnClose)){
            Intent intent=new Intent(NewCustomer.this, CustomerList.class);
            startActivity(intent);
            finish();
        }


    }

}