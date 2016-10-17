package in.kuduvai.cachii.kuduvai;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ContactUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(ContactUs.this,OTP.class);
        startActivity(intent);
        finish();
    }
}
