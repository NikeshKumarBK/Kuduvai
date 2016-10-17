package in.kuduvai.cachii.kuduvai;

/**
 * Created by Cachiii on 7/26/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by User on 1/22/2016.
 */
public class SqlCommands {
    private DBHelper dbHelper;

    public SqlCommands(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int checkValidity()
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-dd");
        long hours=0;

        String query = "SELECT "+Can.KEY_RENEWAL_AT+" FROM " + Can.TABLE_USER;
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        String i = c.getString(0);
        Log.e("Check", String.valueOf(i));
        try {

            Date current_date = new Date();
            //Date cur_date=simpleDateFormat.parse(String.valueOf(current_date));
            Date renewal_date=simpleDateFormat.parse(i);
            Log.e("Check", String.valueOf(current_date));
            Log.e("Check", String.valueOf(renewal_date));
            long diff = renewal_date.getTime() - current_date.getTime();
            Log.e("Check", String.valueOf(diff));
            Log.e("Check", String.valueOf(current_date.getTime()));
            Log.e("Check", String.valueOf(renewal_date.getTime()));
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            hours = minutes / 60;
            long days = hours / 24;

            Log.e("Check", String.valueOf(hours));
            Log.e("Check", String.valueOf(days));

        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (hours<24)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }


    public int insert(Can student) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Can.KEY_contactno, student.contactno);
        values.put(Can.KEY_address, student.address);
        values.put(Can.KEY_name, student.name);
        values.put(Can.KEY_noofcans, student.noofcans);
        values.put(Can.KEY_price, student.price);

        // Inserting Row
        long student_Id = db.insert(Can.TABLE_CUSTOMER, null, values);
        db.close(); // Closing database connection
        return (int) student_Id;
    }

    public int insertPurchase(Can student) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.e("Check", "Db opened");
        values.put(Can.KEY_date_purchase, student.newPurchaseDate);
        values.put(Can.KEY_can_intake_purchase, student.canIntakePurchase);
        values.put(Can.KEY_can_given_purchase, student.canGivenPurchase);
        values.put(Can.KEY_amount_paid_purchase, student.amountPaidPurchase);
        values.put(Can.KEY_balance_purchase, student.balancePurchase);
        Log.e("Check", "values put");

        // Inserting Row
        long datePurchase = db.insert(Can.TABLE_PURCHASE, null, values);
        Log.e("Check", "New Purchase created");

        db.close(); // Closing database connection
        return (int) datePurchase;
    }

    public int insertBooking(Can student) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.e("Check", "Db opened");
        values.put(Can.KEY_duedate_booking, student.dueDateBooking);
        values.put(Can.KEY_customer_id_booking, student.customerIDBooking);
        values.put(Can.KEY_customer_name_booking, student.customerNameBooking);
        values.put(Can.KEY_noofcans_booking, student.numberOfCansBooking);

        Log.e("Check", "values put");

        // Inserting Row
        long datePurchase = db.insert(Can.TABLE_BOOKING, null, values);
        Log.e("Check", "New Booking created");

        db.close(); // Closing database connection
        return (int) datePurchase;
    }

    public int insertAmount(Can student) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.e("Check", "Db opened");
        values.put(Can.KEY_date_amount, student.date);
        values.put(Can.KEY_amount_amount, student.paid);

        Log.e("Check", "values put");

        // Inserting Row
        long datePurchase = db.insert(Can.TABLE_HAND_AMOUNT, null, values);

        db.close(); // Closing database connection
        return (int) datePurchase;
    }

    public int insertDelivery(Can student,String name,Integer id) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Can.KEY_date, student.date);
        values.put(Can.KEY_cans_ordered, student.cansordered);
        values.put(Can.KEY_paid, student.paid);
        values.put(Can.KEY_balance, student.balance);
        values.put(Can.KEY_emptyreturn, student.emptyReturn);
        Log.e("Delivery", "values put");

        String tablename=name+id;
        Log.e("Delivery","tablename got");
        // Inserting Row
        long student_Id = db.insert(tablename, null, values);
        Log.e("Delivery","inserted");
        db.close(); // Closing database connection
        return (int) student_Id;
    }

    public int emptycans(int ID){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "SELECT " + Can.KEY_empty_cans + " FROM " + Can.TABLE_STOCK + " WHERE " +
                Can.KEY_stock_id + "="+ID;
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        int i = c.getInt(0);

        return i;
    }

    public int canstock(int ID){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "SELECT " + Can.KEY_can_stock + " FROM " + Can.TABLE_STOCK + " WHERE " +
                Can.KEY_stock_id + "="+ID;
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        int i = c.getInt(0);

        return i;
    }

    public int emptycan(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "SELECT " + Can.KEY_empty_cans + " FROM " + Can.TABLE_STOCK + " WHERE " +
                Can.KEY_stock_id + "=1";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        int i = c.getInt(0);

        return i;
    }

    public int basePrice(int ID){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "SELECT " + Can.KEY_base_price + " FROM " + Can.TABLE_STOCK + " WHERE " +
                Can.KEY_stock_id + "="+ID;
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        int i = c.getInt(0);

        return i;
    }

    public int numberofcans(int ID){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "SELECT " + Can.KEY_noofcans + " FROM " + Can.TABLE_CUSTOMER + " WHERE " +
                Can.KEY_ID + "="+ID;
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        int i = c.getInt(0);

        return i;
    }

    public int canprice(int ID){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "SELECT " + Can.KEY_price + " FROM " + Can.TABLE_CUSTOMER + " WHERE " +
                Can.KEY_ID + "="+ID;
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        int i = c.getInt(0);

        return i;
    }

    public int delivery_can_count(String date) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Log.e("Check", "date "+date);
        String query = "SELECT SUM(" + Can.KEY_noofcans_booking + ") FROM " + Can.TABLE_BOOKING + " WHERE " +
                Can.KEY_status + "=0";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        int i = c.getInt(0);
        return i;
    }

    public int getHandAmount() {
        //Open connection to read only

        long date = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = sdf.format(date);

        Log.e("Check","before query");

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT " +
                Can.KEY_amount_amount+
                " FROM " + Can.TABLE_HAND_AMOUNT+
                " WHERE "+Can.KEY_date_amount+"=?";

        Log.e("Check","after query");
        //Student student = new Student();
        //ArrayList<HashMap<String, String>> studentList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, new String[]{dateString});
        Log.e("Check","query executed");
        // looping through all rows and adding to list
        int i=0;
        if (cursor.moveToFirst()) {
            do {

                //HashMap<String, String> student = new HashMap<String, String>();
                i=cursor.getInt(cursor.getColumnIndex(Can.KEY_amount_amount))+i;
                //student.put("amount", cursor.getString(cursor.getColumnIndex(Can.KEY_amount_amount)));
                Log.e("Check", "first value got");
                //student.put("date", cursor.getString(cursor.getColumnIndex(Can.KEY_date_amount)));
                Log.e("Check", "second value got");
                // studentList.add(student);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        Log.e("Check", "value "+i);
        return i;

    }

    public int hand_amount() {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long date = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = sdf.format(date);
        Log.e("Check", "before query");

        String query = "SELECT SUM(" + Can.KEY_amount_amount + "), "+Can.KEY_date_amount+" FROM " + Can.TABLE_HAND_AMOUNT + " WHERE " +
                Can.KEY_date_amount + "=?"+" GROUP BY "+Can.KEY_date_amount;
        Cursor c = db.rawQuery(query, new String[]{dateString});

        Log.e("Check","query executed");
        if (!(c.moveToFirst()))
        {
            int i=0;
            return i;
        }
        else {
            c.moveToFirst();
            Log.e("Check", "moved to first");
            int i = c.getInt(0);
            Log.e("Check", "values" + i);
            return i;
        }
    }

    public int tot_balance(String name,Integer id) {

        String tablename=name+id;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "SELECT SUM(" + Can.KEY_balance + ") FROM " + tablename;
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        int i = c.getInt(0);

        return i;
    }

    public void createTable(int student_id, String student_name) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String CREATE_QUERY = "CREATE TABLE " + student_name + student_id + "("
                + Can.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Can.KEY_date + " TEXT ,"
                + Can.KEY_cans_ordered + " INTEGER ,"
                + Can.KEY_paid + " INTEGER ,"
                + Can.KEY_balance + " INTEGER ,"
                + Can.KEY_emptyreturn + " INTEGER)";

        db.execSQL(CREATE_QUERY);
        //Toast.makeText(this,"Table created for"+student_name+student_id, LENGTH_SHORT).show();
        Log.e("CHECK", "Table created for " + student_name + student_id);


    }

    public void delete(int student_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Can.TABLE_CUSTOMER, Can.KEY_ID + "= ?", new String[]{String.valueOf(student_Id)});
        db.close(); // Closing database connection
    }

    public void updateStock(Can student) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.e("Check", "Db opened");
        values.put(Can.KEY_can_stock, student.canstock);
        values.put(Can.KEY_empty_cans, student.emptycans);
        values.put(Can.KEY_total_cans, student.totalcans);
        values.put(Can.KEY_cans_cust, student.canscust);
        values.put(Can.KEY_base_price, student.baseprice);
        Log.e("Check", "values put");

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Can.TABLE_STOCK, values, Can.KEY_stock_id + "= ?", new String[]{String.valueOf(student.stockid)});
        db.close(); // Closing database connection
    }

    public void updateBookStatus(Can student){

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Can.KEY_status, student.statusbooking);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Can.TABLE_BOOKING, values, Can.KEY_booking_ID + "= ?", new String[]{String.valueOf(student.booking_ID)});
        db.close(); // Closing database connection
    }

    public void update(Can student) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Can.KEY_contactno, student.contactno);
        values.put(Can.KEY_address,student.email);
        values.put(Can.KEY_name, student.name);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Can.TABLE_CUSTOMER, values, Can.KEY_ID + "= ?", new String[]{String.valueOf(student.student_ID)});
        db.close(); // Closing database connection
    }

    public void updateCanStock(Can student) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Can.KEY_can_stock, student.canstock);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Can.TABLE_STOCK, values, Can.KEY_stock_id + "= ?", new String[]{String.valueOf(student.stockid)});
        db.close(); // Closing database connection
    }

    public void updateEmptyCan(Can student) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Can.KEY_empty_cans, student.emptycans);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Can.TABLE_STOCK, values, Can.KEY_stock_id + "= ?", new String[]{String.valueOf(student.stockid)});
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String,String>> getStudentList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Can.KEY_ID + "," +
                Can.KEY_name + "," +
                Can.KEY_address + "," +
                Can.KEY_contactno +
                " FROM " + Can.TABLE_CUSTOMER;

        //Student student = new Student();
        ArrayList<HashMap<String, String>> studentList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> student = new HashMap<String, String>();
                student.put("id", cursor.getString(cursor.getColumnIndex(Can.KEY_ID)));
                student.put("name", cursor.getString(cursor.getColumnIndex(Can.KEY_name)));
                student.put("email", cursor.getString(cursor.getColumnIndex(Can.KEY_address)));
                student.put("contact", cursor.getString(cursor.getColumnIndex(Can.KEY_contactno)));
                studentList.add(student);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return studentList;

    }

    public ArrayList<HashMap<String,String>> getBookingList(String booking_date) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Can.KEY_booking_ID + "," +
                Can.KEY_customer_id_booking + "," +
                Can.KEY_customer_name_booking + "," +
                Can.KEY_noofcans_booking  +
                " FROM " + Can.TABLE_BOOKING +  " WHERE " +
                Can.KEY_duedate_booking + "=? AND "+
                Can.KEY_status + "=0";
        Log.e("Booking","After query");

        //Student student = new Student();
        ArrayList<HashMap<String, String>> bookingList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(booking_date) });
        Log.e("Booking","after query execution");
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> book = new HashMap<String, String>();
                book.put("id_booking", cursor.getString(cursor.getColumnIndex(Can.KEY_booking_ID)));
                book.put("customer_id_booking", cursor.getString(cursor.getColumnIndex(Can.KEY_customer_id_booking)));
                book.put("customer_name_booking", cursor.getString(cursor.getColumnIndex(Can.KEY_customer_name_booking)));
                book.put("noofcans_booking", cursor.getString(cursor.getColumnIndex(Can.KEY_noofcans_booking)));
                bookingList.add(book);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return bookingList;

    }


    public ArrayList<HashMap<String,String>> getAmountList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Can.KEY_date_amount + "," +
                Can.KEY_amount_amount+
                " FROM " + Can.TABLE_HAND_AMOUNT;
        Log.e("Booking","After query");

        //Student student = new Student();
        ArrayList<HashMap<String, String>> bookingList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery,null);
        Log.e("Booking","after query execution");
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> book = new HashMap<String, String>();
                book.put("date_amount", cursor.getString(cursor.getColumnIndex(Can.KEY_date_amount)));
                book.put("amount_amount", cursor.getString(cursor.getColumnIndex(Can.KEY_amount_amount)));
                bookingList.add(book);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return bookingList;

    }

    public Cursor getAllPurchase(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor res=db.rawQuery("select * from "+Can.TABLE_PURCHASE,null);
        return res;
    }


    public Cursor getPurchaseList(SQLiteDatabase db){

        Cursor cursor;
        String[] projections={Can.KEY_date_purchase,Can.KEY_can_intake_purchase,Can.KEY_can_given_purchase,
                Can.KEY_amount_paid_purchase,Can.KEY_balance_purchase};

        cursor=db.query(Can.TABLE_PURCHASE,projections,null,null,null,null,null);
        return cursor;

    }

    public Cursor getCustomerDisplay(SQLiteDatabase db,String name,Integer id){

        Cursor cursor;
        String[] projections={Can.KEY_date,Can.KEY_cans_ordered,Can.KEY_paid,
                Can.KEY_balance};

        cursor=db.query(name+id,projections,null,null,null,null,null);
        return cursor;

    }


    public Cursor getBookingList(SQLiteDatabase db,String booking_date){

        Cursor cursor;
        String[] book_date={booking_date};
        String[] projections={Can.KEY_customer_id_booking,Can.KEY_customer_name_booking,Can.KEY_noofcans_booking};

        cursor=db.query(Can.TABLE_BOOKING,projections,Can.KEY_duedate_booking+"=?",book_date,null,null,null);
        return cursor;

    }

    public Can getStudentById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Can.KEY_ID + "," +
                Can.KEY_name + "," +
                Can.KEY_address + "," +
                Can.KEY_contactno + ","+
                Can.KEY_noofcans+ ","+
                Can.KEY_price+
                " FROM " + Can.TABLE_CUSTOMER
                + " WHERE " +
                Can.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        Log.e("Check", "Query success");

        int iCount =0;
        Can student = new Can();

        Log.e("Check", "Student initialized");

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        Log.e("Check", "Cursor initialized");

        if (cursor.moveToFirst()) {
            do {
                student.student_ID =cursor.getInt(cursor.getColumnIndex(Can.KEY_ID));
                student.name =cursor.getString(cursor.getColumnIndex(Can.KEY_name));
                student.address  =cursor.getString(cursor.getColumnIndex(Can.KEY_address));
                student.contactno = cursor.getString(cursor.getColumnIndex(Can.KEY_contactno));
                student.noofcans =cursor.getInt(cursor.getColumnIndex(Can.KEY_noofcans));
                student.price =cursor.getInt(cursor.getColumnIndex(Can.KEY_price));

                Log.e("Check", "Data found");

            } while (cursor.moveToNext());
        }

        Log.e("Check", "Database checked");

        cursor.close();
        db.close();
        return student;
    }


    public void addUser(String uid, String name, String created_at, String renewal_date) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Can.KEY_UID, uid); // Name
        values.put(Can.KEY_NAME, name); // Email
        values.put(Can.KEY_CREATED_AT, created_at); // Email
        values.put(Can.KEY_RENEWAL_AT, renewal_date); // Created At

        // Inserting Row
        long id = db.insert(Can.TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d("Check", "New user inserted into sqlite: " + id);
    }



}