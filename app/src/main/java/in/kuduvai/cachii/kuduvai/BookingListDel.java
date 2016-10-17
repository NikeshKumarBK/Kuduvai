package in.kuduvai.cachii.kuduvai;

import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

/**
 * Created by User on 1/22/2016.
 */
public class BookingListDel extends ListActivity implements View.OnClickListener{

    // Button btnAdd,btnGetAll;
    TextView customerId,customerName,noofCans,booking_id;
    EditText editTextBookingDateList;
    Button btnReloadBook;
    ListAdapter adapter;
    private String book_date;

    private DatePickerDialog NewPurchaseDatePicker;

    private SimpleDateFormat dateFormatter;

    @Override
    public void onClick(View view) {
        if(view == editTextBookingDateList) {
            NewPurchaseDatePicker.show();
        }
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
        setContentView(R.layout.activity_booking_list_del);
        Log.e("Booking", "layout set");

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        editTextBookingDateList=(EditText)findViewById(R.id.edittextBookingListDate);
        btnReloadBook=(Button)findViewById(R.id.btnReloadBook);

        long date = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = sdf.format(date);
        editTextBookingDateList.setText(dateString);

        setDateTimeField();
        Log.e("Booking", "date set");

        Intent intent = getIntent();
        book_date = intent.getStringExtra("booking_date");

        btnReloadBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BookingListDel.this,BookingListDel.class);
                intent.putExtra("booking_date",editTextBookingDateList.getText().toString());
                startActivity(intent);
                finish();
                /*Log.e("Booking", "Before function call");
                SqlCommands repo = new SqlCommands(getApplicationContext());
                Log.e("Booking", "Inside function");
                ArrayList<HashMap<String, String>> bookingList =  repo.getBookingList(editTextBookingDateList.getText().toString());
                Log.e("Booking", "Data got");
                if(bookingList.size()!=0) {

                    ListView lv = getListView();

                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                            Log.e("Booking","Inside onitemclicklistener");
                            booking_id=(TextView)view.findViewById(R.id.id_booking);
                            customerId=(TextView)view.findViewById(R.id.customer_id_booking);
                            customerName=(TextView)view.findViewById(R.id.TextViewCustNameBooking);
                            noofCans=(TextView)view.findViewById(R.id.TextViewNoofcansBooking);
                            Log.e("Booking","After declaration");
                            Intent intent=new Intent(BookingListDel.this,DeliveryStatus.class);
                            intent.putExtra("book_id",Integer.parseInt(booking_id.getText().toString()));
                            intent.putExtra("id",Integer.parseInt(customerId.getText().toString()));
                            intent.putExtra("name",customerName.getText().toString());
                            intent.putExtra("noofcans",Integer.parseInt(noofCans.getText().toString()));
                            intent.putExtra("date", editTextBookingDateList.getText().toString());

                            Log.e("put", customerId.getText().toString());
                            Log.e("put", customerName.getText().toString());
                            Log.e("put", noofCans.getText().toString());
                            Log.e("put", editTextBookingDateList.getText().toString());
                            Log.e("Booking", "values put in Intent");
                            startActivity(intent);
                            finish();

                        }
                    });
                    adapter = new SimpleAdapter( BookingListDel.this,bookingList, R.layout.view_booking_details, new String[] { "id_booking","customer_id_booking","customer_name_booking","noofcans_booking"}, new int[] {R.id.id_booking,R.id.customer_id_booking,R.id.TextViewCustNameBooking, R.id.TextViewNoofcansBooking});

                    setListAdapter(adapter);
                }else{
                    Toast.makeText(getApplicationContext(), "No Customer!", Toast.LENGTH_SHORT).show();
                }*/
            }
        });


        SqlCommands repo = new SqlCommands(this);
        Log.e("Booking", "Inside function");
        ArrayList<HashMap<String, String>> bookingList =  repo.getBookingList(book_date);
        Log.e("Booking", "Data got");
        if(bookingList.size()!=0) {

            ListView lv = getListView();

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                    Log.e("Booking","Inside onitemclicklistener");
                    booking_id=(TextView)view.findViewById(R.id.id_booking);
                    customerId=(TextView)view.findViewById(R.id.customer_id_booking);
                    customerName=(TextView)view.findViewById(R.id.TextViewCustNameBooking);
                    noofCans=(TextView)view.findViewById(R.id.TextViewNoofcansBooking);
                    Log.e("Booking","After declaration");
                    Intent intent=new Intent(BookingListDel.this,DeliveryStatus.class);
                    intent.putExtra("book_id",Integer.parseInt(booking_id.getText().toString()));
                    intent.putExtra("id",Integer.parseInt(customerId.getText().toString()));
                    intent.putExtra("name",customerName.getText().toString());
                    intent.putExtra("noofcans",Integer.parseInt(noofCans.getText().toString()));
                    intent.putExtra("date", editTextBookingDateList.getText().toString());

                    Log.e("put", customerId.getText().toString());
                    Log.e("put", customerName.getText().toString());
                    Log.e("put", noofCans.getText().toString());
                    Log.e("put", editTextBookingDateList.getText().toString());
                    Log.e("Booking", "values put in Intent");
                    startActivity(intent);
                    finish();

                }
            });
            adapter = new SimpleAdapter( BookingListDel.this,bookingList, R.layout.view_booking_details, new String[] { "id_booking","customer_id_booking","customer_name_booking","noofcans_booking"}, new int[] {R.id.id_booking,R.id.customer_id_booking,R.id.TextViewCustNameBooking, R.id.TextViewNoofcansBooking});

            setListAdapter(adapter);
        }else{
            Toast.makeText(this, "No Customer!", Toast.LENGTH_SHORT).show();
        }

    }

    private void setDateTimeField() {
        editTextBookingDateList.setOnClickListener(this);


        Calendar newCalendar = Calendar.getInstance();
        NewPurchaseDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editTextBookingDateList.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }    /*public boolean onCreateOptionsMenu(Menu menu) {

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
