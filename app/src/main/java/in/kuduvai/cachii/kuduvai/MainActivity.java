package in.kuduvai.cachii.kuduvai;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        boolean isFirstTime = MyPreferences.isFirst(MainActivity.this);
        if (isFirstTime){
            Intent intent=new Intent(MainActivity.this, StockUpdate.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        Fragment fragment=new FirstFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame, fragment);
        ft.commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_customer) {
            //Toast.makeText(getApplicationContext(),"Customers is selected",Toast.LENGTH_SHORT).show();

            Intent intent=new Intent(MainActivity.this,CustomerList.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.action_delete) {
            //Toast.makeText(getApplicationContext(),"Customers is selected",Toast.LENGTH_SHORT).show();

            exportDB();
        }

        return super.onOptionsItemSelected(item);
    }

    private void exportDB(){

        try {
            File DB =getDatabasePath(Can.DATABASE_NAME);
            if (DB.isFile()){
                Toast.makeText(getApplicationContext(), "File isFile: " +DB, Toast.LENGTH_SHORT).show();
                Log.w("myApp", "File:" + DB);
            } else if (DB.isDirectory()){
                Toast.makeText(getApplicationContext(), "Folder isDirectory: " +DB, Toast.LENGTH_SHORT).show();
                Log.w("myApp", "Folder:"+DB);
            }

            File sd = Environment.getExternalStorageDirectory();
            Toast.makeText(getApplicationContext(), "Path is: " +sd, Toast.LENGTH_SHORT).show();
            File backupDBFolder = new File(sd, "/com.example.nikesh/Backup/");
            File backupDBFile = new File(sd, "/com.example.nikesh/Backup/database.db");
            backupDBFolder.mkdirs();

            if (backupDBFolder.isDirectory()){
                Log.w("myApp","External folder isDirectory: "+backupDBFolder);
            } else{
                Log.w("myApp","Nothing found");
            }


            Log.w("myApp", "Start Copy");
            FileInputStream inStream = new FileInputStream(DB);
            FileOutputStream outStream = new FileOutputStream(backupDBFile);

            FileChannel inChannel = inStream.getChannel();
            FileChannel outChannel = outStream.getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
            inStream.close();
            outStream.close();
            Log.w("myApp", "End Copy");

        } catch (Exception e) {

            e.printStackTrace();
            //Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.new_customer) {
            // Handle the camera action
            Intent intent=new Intent(MainActivity.this,NewCustomer.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.new_purchase) {
            Intent intent=new Intent(MainActivity.this,NewPurchase.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.customer_list) {
            Intent intent=new Intent(MainActivity.this,CustomerList.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.purchase_list) {
            Intent intent=new Intent(MainActivity.this,PurchaseList.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.stock_update) {
            Intent intent=new Intent(MainActivity.this,StockUpdate.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.amount_table) {
            Intent intent=new Intent(MainActivity.this,AmountListDel.class);
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public static class MyPreferences {

        private static final String MY_PREFERENCES = "my_preferences";

        public static boolean isFirst(Context context){
            final SharedPreferences reader = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
            final boolean first = reader.getBoolean("is_first", true);
            if(first){
                final SharedPreferences.Editor editor = reader.edit();
                editor.putBoolean("is_first", false);
                editor.commit();
            }
            return first;
        }

    }
}
