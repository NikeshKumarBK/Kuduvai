package in.kuduvai.cachii.kuduvai;

import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class NewBooking extends ListActivity implements View.OnClickListener{

    // Button btnAdd,btnGetAll;
    TextView student_Id,student_name;
    Button btnBookNow;
    EditText editTextDateBooking,editTextNumberofcansBooking;
    int i;

    private DatePickerDialog NewPurchaseDatePicker;

    private SimpleDateFormat dateFormatter;

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
        setContentView(R.layout.activity_new_booking);

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        editTextDateBooking=(EditText)findViewById(R.id.editTextDateBooking);
        editTextNumberofcansBooking=(EditText)findViewById(R.id.editTextnoofcansBooking);
        btnBookNow=(Button)findViewById(R.id.btnBookNow);

        btnBookNow.setOnClickListener(this);

        long date = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = sdf.format(date);
        editTextDateBooking.setText(dateString);

        setDateTimeField();

        SqlCommands repo = new SqlCommands(this);

        ArrayList<HashMap<String, String>> studentList =  repo.getStudentList();
        if(studentList.size()!=0) {
            ListView lv = getListView();
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                    student_Id = (TextView) view.findViewById(R.id.customer_id);
                    student_name = (TextView) view.findViewById(R.id.customer_name);
                    SqlCommands repo = new SqlCommands(getApplicationContext());
                    i=repo.numberofcans(Integer.parseInt(student_Id.getText().toString()));
                    editTextNumberofcansBooking.setText(""+i);
                }
            });
            ListAdapter adapter = new SimpleAdapter( NewBooking.this,studentList, R.layout.view_customer, new String[] { "id","name"}, new int[] {R.id.customer_id, R.id.customer_name});

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

    private void setDateTimeField() {
        editTextDateBooking.setOnClickListener(this);


        Calendar newCalendar = Calendar.getInstance();
        NewPurchaseDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editTextDateBooking.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public void onClick(View view) {

        if(view == editTextDateBooking) {
            NewPurchaseDatePicker.show();
        }

        if (view==findViewById(R.id.btnBookNow)){
            final SqlCommands repo = new SqlCommands(this);
            final Can student = new Can();
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(NewBooking.this);

            // Setting Dialog Title
            alertDialog.setTitle("Confirm Booking...");

            // Setting Dialog Message
            alertDialog.setMessage("Due Date: " + editTextDateBooking.getText() + "\nCustomer Name: " +student_name.getText()+
                    "\nNumber of cans: "+editTextNumberofcansBooking.getText());

            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    // Write your code here to invoke YES event
                    student.dueDateBooking=String.valueOf(editTextDateBooking.getText().toString());
                    student.customerIDBooking=Integer.parseInt(student_Id.getText().toString());
                    student.customerNameBooking=String.valueOf(student_name.getText().toString());
                    student.numberOfCansBooking=Integer.parseInt(editTextNumberofcansBooking.getText().toString());

                    repo.insertBooking(student);

                    Toast.makeText(getApplicationContext(), "New Booking made for "+student.customerNameBooking
                            , Toast.LENGTH_SHORT).show();

                }
            });

            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to invoke NO event
                    Toast.makeText(getApplicationContext(), "Booking cancelled", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            });

            // Showing Alert Message
            alertDialog.show();
        }
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

}
