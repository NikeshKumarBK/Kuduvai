package in.kuduvai.cachii.kuduvai;

/**
 * Created by Cachiii on 7/26/2016.
 */
public class Can {

    public static final String DATABASE_NAME = "can.db";

    // Login table name
    public static final String TABLE_USER = "users";

    // Login Table Columns names

    public static final String KEY_UID = "uid";
    public static final String KEY_NAME = "name";
    public static final String KEY_RENEWAL_AT = "renewal_date";
    public static final String KEY_CREATED_AT = "created_at";

    // Labels table name
    public static final String TABLE_CUSTOMER = "NewCustomer";
    public static final String TABLE_PURCHASE = "NewPurchase";
    public static final String TABLE_BOOKING = "NewBooking";
    public static final String TABLE_STOCK = "Stock";
    public static final String TABLE_HAND_AMOUNT = "Amount";
    public static final String TABLE_HAND_RENEWAL= "Renewal";

    public static final String KEY_RENEWAL_DATE = "renewalDate";

    // Labels Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_name = "name";
    public static final String KEY_email = "email";
    public static final String KEY_contactno = "contact";
    public static final String KEY_noofcans = "numberOfCans";
    public static final String KEY_price = "price";
    public static final String KEY_address = "address";

    public static final String KEY_cans_ordered = "cansOrdered";
    public static final String KEY_date= "date";
    public static final String KEY_paid= "paid";
    public static final String KEY_balance= "balance";
    public static final String KEY_totbalance= "totalBalance";
    public static final String KEY_emptyreturn= "emptyReturned";

    public static final String KEY_purchase_ID= "idPurchase";
    public static final String KEY_date_purchase= "newPurchaseDate";
    public static final String KEY_can_intake_purchase= "canIntakePurchase";
    public static final String KEY_can_given_purchase= "canGivenPurchase";
    public static final String KEY_amount_paid_purchase= "amountPaidPurchase";
    public static final String KEY_balance_purchase= "balancePurchase";
    public static final String KEY_cummulative_balance= "cummulativebalancePurchase";

    public static final String KEY_booking_ID= "idBooking";
    public static final String KEY_duedate_booking= "dueDateBooking";
    public static final String KEY_customer_id_booking= "customerIDBooking";
    public static final String KEY_customer_name_booking= "customerNameBooking";
    public static final String KEY_noofcans_booking= "numberOfCansBooking";
    public static final String KEY_status= "statusbooking";

    public static final String KEY_stock_id= "stockid";
    public static final String KEY_can_stock= "canstock";
    public static final String KEY_empty_cans= "emptycans";
    public static final String KEY_total_cans= "totalcans";
    public static final String KEY_cans_cust= "canscust";
    public static final String KEY_base_price= "baseprice";

    public static final String KEY_amount_amount= "amountAmount";
    public static final String KEY_total_amount= "totalAmount";
    public static final String KEY_date_amount= "dateAmount";


    // property help us to keep data
    public int student_ID;
    public String name;
    public String email;
    public String contactno;
    public int noofcans;
    public int price;
    public String address;

    public String date;
    public int cansordered;
    public int paid;
    public int balance;
    public int totalBalance;
    public int emptyReturn;

    public int purchase_ID;
    public String newPurchaseDate;
    public int canIntakePurchase;
    public int canGivenPurchase;
    public int amountPaidPurchase;
    public int balancePurchase;
    public int cumulativebalancePurchase;

    public int booking_ID;
    public String dueDateBooking;
    public int customerIDBooking;
    public String customerNameBooking;
    public int numberOfCansBooking;
    public int statusbooking;

    public int stockid;
    public int canstock;
    public int emptycans;
    public int totalcans;
    public int canscust;
    public int baseprice;

    public int amountAmount;
    public int dateAmount;
}
