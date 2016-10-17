package in.kuduvai.cachii.kuduvai;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Cachiii on 7/26/2016.
 */
public class AmountListDel extends ListActivity implements View.OnClickListener{

    ListAdapter adapter;


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
        setContentView(R.layout.amount_list);
        Log.e("Booking", "layout set");

        SqlCommands repo = new SqlCommands(this);
        Log.e("Booking", "Inside function");
        ArrayList<HashMap<String, String>> bookingList =  repo.getAmountList();
        Log.e("Booking", "Data got");
        if(bookingList.size()!=0) {

            ListView lv = getListView();

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                }
            });
            adapter = new SimpleAdapter( AmountListDel.this,bookingList, R.layout.view_amount_details, new String[] { "date_amount","amount_amount"}, new int[] {R.id.TextViewDateAmount,R.id.TextViewAmountAmount});

            setListAdapter(adapter);
        }else{

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
