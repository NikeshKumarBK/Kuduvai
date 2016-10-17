package in.kuduvai.cachii.kuduvai;

/**
 * Created by Cachiii on 7/26/2016.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by User on 1/22/2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.


    private static final int DATABASE_VERSION = 1;

    private static String DB_PATH = "/data/data/com.example.user.can/databases/";

    // Database Name
    private static final String DATABASE_NAME = "can.db";

    private SQLiteDatabase sqlDB;
    Context mContext;


    public DBHelper(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here
        Cursor cursor;

        Log.e("Check", "Before");


        //sqlDB = SQLiteDatabase.openDatabase(DB_PATH , null, SQLiteDatabase.CREATE_IF_NECESSARY);
        Log.e("Check", "after");


        String CREATE_LOGIN_TABLE = "CREATE TABLE " + Can.TABLE_USER + "("
                + Can.KEY_UID + " INTEGER PRIMARY KEY," + Can.KEY_NAME + " TEXT,"
                + Can.KEY_CREATED_AT + " DATE,"
                + Can.KEY_RENEWAL_AT + " DATE" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);


        String CREATE_TABLE_STUDENT = "CREATE TABLE " + Can.TABLE_CUSTOMER  + "("
                + Can.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Can.KEY_name + " TEXT, "
                + Can.KEY_contactno + " TEXT, "
                + Can.KEY_address + " TEXT, "
                + Can.KEY_noofcans + " INTEGER, "
                + Can.KEY_price + " INTEGER)";

        db.execSQL(CREATE_TABLE_STUDENT);

        Log.e("Check", "Table Created Customer");

        String CREATE_TABLE_PURCHASE= "CREATE TABLE " + Can.TABLE_PURCHASE  + "("
                + Can.KEY_purchase_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Can.KEY_date_purchase  + " TEXT ,"
                + Can.KEY_can_intake_purchase + " INTEGER, "
                + Can.KEY_can_given_purchase + " INTEGER, "
                + Can.KEY_amount_paid_purchase + " INTEGER, "
                + Can.KEY_balance_purchase + " INTEGER)";
        //+ Student.KEY_cummulative_balance+ " INTEGER)";


        db.execSQL(CREATE_TABLE_PURCHASE);

        Log.e("Check", "Table Created New Purchase");

        String CREATE_TABLE_BOOKING= "CREATE TABLE " + Can.TABLE_BOOKING  + "("
                + Can.KEY_booking_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Can.KEY_duedate_booking  + " TEXT ,"
                + Can.KEY_customer_id_booking + " INTEGER, "
                + Can.KEY_customer_name_booking + " TEXT, "
                + Can.KEY_noofcans_booking + " INTEGER, "
                + Can.KEY_status + " INTEGER DEFAULT 0)";

        db.execSQL(CREATE_TABLE_BOOKING);

        Log.e("Check", "Table Created New Booking");

        String CREATE_TABLE_STOCK= "CREATE TABLE "+ Can.TABLE_STOCK + "("
                + Can.KEY_stock_id + " INTEGER,"
                + Can.KEY_can_stock + " INTEGER,"
                + Can.KEY_empty_cans + " INTEGER,"
                + Can.KEY_total_cans + " INTEGER,"
                + Can.KEY_cans_cust + " INTEGER,"
                + Can.KEY_base_price + " INTEGER)";

        db.execSQL(CREATE_TABLE_STOCK);

        Log.e("Check", "Table Created Stock");

        String INSERT_STOCK="INSERT INTO "+Can.TABLE_STOCK+"("
                +Can.KEY_stock_id+","+Can.KEY_can_stock+","+Can.KEY_empty_cans+","+Can.KEY_total_cans+","+Can.KEY_cans_cust+","+Can.KEY_base_price+
                ") VALUES (1,0,0,0,0,0)";

        db.execSQL(INSERT_STOCK);

        Log.e("Check", "Values inserted");

        String CREATE_TABLE_AMOUNT="CREATE TABLE "+Can.TABLE_HAND_AMOUNT+"("
                +Can.KEY_date_amount+" TEXT,"
                +Can.KEY_amount_amount+" INTEGER)";

        db.execSQL(CREATE_TABLE_AMOUNT);

        String CREATE_TABLE_RENEWAL="CREATE TABLE "+Can.TABLE_HAND_RENEWAL+"("
                +Can.KEY_RENEWAL_DATE+" DATE)";

        db.execSQL(CREATE_TABLE_RENEWAL);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + Can.TABLE_CUSTOMER);

        // Create tables again
        onCreate(db);

    }

}