package in.kuduvai.cachii.kuduvai;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OTP extends AppCompatActivity {

    private static final String TAG_SUCCESS = "success";
    int success;

    EditText edtUser,edtPwd;
    Button btnLogin,btnContactUs;

    private static final String TAG = OTP.class.getSimpleName();

    String reg,pwd;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SqlCommands db;

    // Server user login url
    public static String URL_LOGIN = "http://dhamodharan.esy.es/can/otp.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        edtUser=(EditText)findViewById(R.id.customer_number);
        edtPwd=(EditText)findViewById(R.id.otp);

        btnLogin=(Button)findViewById(R.id.btnSend);
        btnContactUs=(Button)findViewById(R.id.btnContactUs);
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SqlCommands(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());


        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // Launch main activity


            Intent intent = new Intent(OTP.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reg = edtUser.getText().toString().trim();
                pwd = edtPwd.getText().toString().trim();

                if(CheckNetwork.isInternetAvailable(OTP.this)) //returns true if internet available
                {
                    // Check for empty data in the form
                    if (!reg.isEmpty() && !pwd.isEmpty()) {
                        // login user
                        checkLogin(reg, pwd);
                    } else {
                        // Prompt user to enter credentials
                        Toast.makeText(getApplicationContext(),"Please enter the credentials!", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(OTP.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OTP.this,ContactUs.class);
                startActivity(intent);
                finish();
            }
        });

    }
    /**
     * function to verify login details in mysql db
     * */
    private void checkLogin(final String regid, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {

                    JSONObject jObj = new JSONObject(response);

                    success = jObj.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite

                        //String uid = jObj.getString("RegNo");

                        JSONObject user = jObj.getJSONObject("user");
                        String uid=user.getString("customer_id");
                        String name = user.getString("customer_name");
                        String created_at = user.getString("created_at");
                        String renewal_date = user.getString("renewal_date");


                        /*String uid = jObj.getString("customer_id");
                        String name = jObj.getString("customer_name");
                        String created_at = jObj.getString("created_at");
                        String renewal_date = jObj.getString("renewal_date");*/

                        // Inserting row in users table
                        db.addUser(uid, name, created_at, renewal_date);

                        //Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        session.setLogin(true);
                        Intent intent = new Intent(OTP.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(OTP.this,"OTP is Invalid",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),"No Internet connection", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("customer_id", regid);
                params.put("otp", password);

                Log.e("Check",regid+"  "+password);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
