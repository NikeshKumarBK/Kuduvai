package in.kuduvai.cachii.kuduvai;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NewPurchase extends ActionBarActivity implements View.OnClickListener{

    EditText editTextDatePurchase,editTextCanIntakePurchase,editTextCanGivenPurchase,editTextAmountPaidPurchase,editTextBalancePurchase;
    Button btnSavePurchase,btnClosePurchase;

    private DatePickerDialog NewPurchaseDatePicker;

    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_purchase);

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        editTextDatePurchase=(EditText)findViewById(R.id.editTextDatePurchase);

        editTextCanIntakePurchase=(EditText)findViewById(R.id.editTextCanIntakePurchase);
        editTextCanGivenPurchase=(EditText)findViewById(R.id.editTextcanGivenPurchase);
        editTextAmountPaidPurchase=(EditText)findViewById(R.id.editTextAmountPaidPurchase);
        editTextBalancePurchase=(EditText)findViewById(R.id.editTextBalancePurchase);



        btnSavePurchase=(Button)findViewById(R.id.btnSavePurchase);
        btnClosePurchase=(Button)findViewById(R.id.btnClosePurchase);

        long date = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = sdf.format(date);
        editTextDatePurchase.setText(dateString);

        setDateTimeField();

        btnClosePurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NewPurchase.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSavePurchase.setOnClickListener(this);


    }

    private void setDateTimeField() {
        editTextDatePurchase.setOnClickListener(this);


        Calendar newCalendar = Calendar.getInstance();
        NewPurchaseDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editTextDatePurchase.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {

        if(v == editTextDatePurchase) {
            NewPurchaseDatePicker.show();
        }
        if (v== findViewById(R.id.btnSavePurchase))
        {
            SqlCommands repo = new SqlCommands(this);
            Can student = new Can();

            student.newPurchaseDate= String.valueOf(editTextDatePurchase.getText().toString());
            student.canIntakePurchase= Integer.parseInt(editTextCanIntakePurchase.getText().toString());
            student.canGivenPurchase= Integer.parseInt(editTextCanGivenPurchase.getText().toString());
            student.amountPaidPurchase= Integer.parseInt(editTextAmountPaidPurchase.getText().toString());
            student.balancePurchase= Integer.parseInt(editTextBalancePurchase.getText().toString());

            repo.insertPurchase(student);

            Toast.makeText(this, "New purchase made on " + student.newPurchaseDate, Toast.LENGTH_SHORT).show();

            int i=repo.canstock(1);

            int update=i+student.canIntakePurchase;
            student.stockid=1;
            student.canstock=update;

            repo.updateCanStock(student);

            i=repo.emptycans(1);
            update=i-student.canGivenPurchase;

            student.emptycans=update;

            repo.updateEmptyCan(student);

            Intent intent=new Intent(NewPurchase.this,MainActivity.class);
            startActivity(intent);
            finish();

        }
    }
}
