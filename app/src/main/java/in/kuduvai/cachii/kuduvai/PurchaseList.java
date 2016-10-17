package in.kuduvai.cachii.kuduvai;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class PurchaseList extends Activity {

    ListView listView;
    SQLiteDatabase sqLiteDatabase;
    SqlCommands repo;
    DBHelper dbHelper;
    Cursor cursor;
    PurchaseListAdapter listDataAdapterPurchase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_list);
        Log.e("Check","PurchaseDetailsCopy Started");

        listView=(ListView)findViewById(R.id.PurchaseList);
        dbHelper=new DBHelper(getApplicationContext());
        repo=new SqlCommands(getApplicationContext());
        sqLiteDatabase = dbHelper.getReadableDatabase();
        cursor=repo.getPurchaseList(sqLiteDatabase);
        listDataAdapterPurchase=new PurchaseListAdapter(getApplicationContext(),R.layout.view_purchase_details);
        listView.setAdapter(listDataAdapterPurchase);
        if (cursor.moveToFirst())
        {
            do{

                String date,can_intake,can_given,amount_paid,balance;
                date=cursor.getString(0);
                can_intake=cursor.getString(1);
                can_given=cursor.getString(2);
                amount_paid=cursor.getString(3);
                balance=cursor.getString(4);
                PurchaseDataProvider dataProviderPurchase=new PurchaseDataProvider(date,can_intake,can_given,amount_paid,balance);
                listDataAdapterPurchase.add(dataProviderPurchase);

            }while (cursor.moveToNext());
        }


    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(PurchaseList.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
