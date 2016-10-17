package in.kuduvai.cachii.kuduvai;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by User on 1/22/2016.
 */
public class DeliveryStatus extends ActionBarActivity implements View.OnClickListener{

    Button btnDoneDelivery,btnCloseDelivery;
    TextView textViewName,textViewcansOrdered,textViewAmountPaidDelivery,textViewpastBalanceDelivery;
    EditText editTextPaid,editTextEmpty;
    private int id,noofcans,book_id;
    private String name,date;
    int canprice,amt,bal,tot_balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_status);

        Log.e("Check", "Layout started");

        btnDoneDelivery = (Button) findViewById(R.id.btnDoneDelivery);
        btnCloseDelivery = (Button) findViewById(R.id.btnCloseDelivery);

        textViewName = (TextView) findViewById(R.id.delivery_name);
        textViewcansOrdered = (TextView) findViewById(R.id.delivery_can_count);
        textViewAmountPaidDelivery = (TextView) findViewById(R.id.textViewAmountPaidDelivery);
        textViewpastBalanceDelivery=(TextView)findViewById(R.id.textViewpastBalanceDelivery);

        editTextPaid=(EditText)findViewById(R.id.editTextPaidDelivery);
        editTextEmpty=(EditText)findViewById(R.id.editTextEmpty);

        btnDoneDelivery.setOnClickListener(this);
        btnCloseDelivery.setOnClickListener(this);

        Log.e("Check", "Declaration finished");

        Intent intent = getIntent();
        book_id=intent.getIntExtra("book_id",0);
        id =intent.getIntExtra("id", 0);
        name = intent.getStringExtra("name");
        noofcans=intent.getIntExtra("noofcans", 2);
        date=intent.getStringExtra("date");

        Log.e("Check", "Intent received");

        new Calculation().execute();


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

    public void onClick(View view) {
        if (view == findViewById(R.id.btnDoneDelivery)){

            SqlCommands repo = new SqlCommands(this);
            Can student = new Can();
            student.date=date;
            student.cansordered=noofcans;

            student.paid= Integer.parseInt(editTextPaid.getText().toString());

            bal=amt-student.paid;

            student.balance= bal;
            student.totalBalance= bal;

            student.emptyReturn= Integer.parseInt(editTextEmpty.getText().toString());

            repo.insertDelivery(student, name, id);

            repo.insertAmount(student);

            int status=1;
            Can bookupdate=new Can();
            bookupdate.booking_ID=book_id;
            bookupdate.statusbooking=status;

            repo.updateBookStatus(bookupdate);

            Toast.makeText(this, "Delivery successful!", Toast.LENGTH_SHORT).show();

            int i=repo.canstock(1);
            student.stockid=1;
            student.canstock=i-noofcans;
            repo.updateCanStock(student);

            i=repo.emptycans(1);
            student.stockid=1;
            student.emptycans=i+student.emptyReturn;
            repo.updateEmptyCan(student);

            Log.e("Check", String.valueOf(student.emptycans));



            Intent intent=new Intent(DeliveryStatus.this,MainActivity.class);
            startActivity(intent);
            finish();

        }else if (view== findViewById(R.id.btnCloseDelivery)){
            Intent intent=new Intent(DeliveryStatus.this,MainActivity.class);
            startActivity(intent);
            finish();
        }


    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    class Calculation extends AsyncTask<String,Void,Boolean>{

        private ProgressDialog progress;
        @Override
        protected Boolean doInBackground(String... params) {
            SqlCommands repo = new SqlCommands(getApplicationContext());
            Can student = new Can();
            //student = repo.getStudentById(_Student_Id);

            tot_balance=repo.tot_balance(name, id);

            canprice=repo.canprice(id);
            return null;


        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            this.progress.setMessage("Loading...");
            //this.progress.show();

        }

        public Calculation() {
            progress = new ProgressDialog(DeliveryStatus.this);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            textViewpastBalanceDelivery.setText("" + tot_balance);

            Log.e("Check", "Data sent to Student Repo");

            textViewName.setText(name);
            textViewcansOrdered.setText(String.valueOf(noofcans));


            amt=canprice*noofcans;

            textViewAmountPaidDelivery.setText(String.valueOf(amt));
        }
    }

}