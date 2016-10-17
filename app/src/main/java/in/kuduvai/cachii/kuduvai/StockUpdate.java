package in.kuduvai.cachii.kuduvai;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StockUpdate extends Activity implements View.OnClickListener{

    EditText editTextCanStock,editTextEmptyCans,editTextTotalCans,editTextCansCust,editTextBasePrice;
    TextView txtStockId;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_update);

        editTextCanStock=(EditText)findViewById(R.id.editTextCansInStock);
        editTextEmptyCans=(EditText)findViewById(R.id.editTextEmptyCans);
        //editTextTotalCans=(EditText)findViewById(R.id.editTextTotalCans);
        editTextCansCust=(EditText)findViewById(R.id.editTextCansCust);
        editTextBasePrice=(EditText)findViewById(R.id.editTextBasePrice);

        txtStockId=(TextView)findViewById(R.id.txtStockId);

        btnSave=(Button)findViewById(R.id.btnSaveStock);

        btnSave.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(StockUpdate.this,MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        SqlCommands repo = new SqlCommands(this);
        Can student = new Can();

        student.stockid=Integer.parseInt(txtStockId.getText().toString());
        student.canstock= Integer.parseInt(editTextCanStock.getText().toString());
        student.emptycans= Integer.parseInt(editTextEmptyCans.getText().toString());
        student.totalcans= Integer.parseInt(editTextCanStock.getText().toString())+Integer.parseInt(editTextCansCust.getText().toString())+Integer.parseInt(editTextEmptyCans.getText().toString());
        student.canscust= Integer.parseInt(editTextCansCust.getText().toString());
        student.baseprice= Integer.parseInt(editTextBasePrice.getText().toString());

        repo.updateStock(student);

        Toast.makeText(this,"Stock updated",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(StockUpdate.this,MainActivity.class);
        startActivity(intent);
        finish();

    }
}
