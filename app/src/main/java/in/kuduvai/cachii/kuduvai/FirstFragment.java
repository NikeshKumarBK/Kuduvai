package in.kuduvai.cachii.kuduvai;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;


public class FirstFragment extends Fragment {


    private SessionManager session;

    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_first, container, false);

        TextView txthand_amt,txtstock_count,txtcan_deliver_count,txtempty_can;

        txthand_amt=(TextView)rootView.findViewById(R.id.hand_amount);
        txtstock_count=(TextView)rootView.findViewById(R.id.stock_count);
        txtcan_deliver_count=(TextView)rootView.findViewById(R.id.can_delivery_count);
        txtempty_can=(TextView)rootView.findViewById(R.id.can_delivery_empty_count);

        long date = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        final String dateString = sdf.format(date);


        SqlCommands repo = new SqlCommands(getActivity());

        int check=repo.checkValidity();
        Log.e("Check validity", String.valueOf(check));
        if (check==1)
        {
            session = new SessionManager(getActivity());
            session.setLogin(false);
            Intent intent=new Intent(getActivity(),OTP.class);
            startActivity(intent);
        }



        int i=repo.canstock(1);
        txtstock_count  .setText("" + i);

        int empty=repo.emptycan();
        txtempty_can  .setText("" + empty);

        int j=repo.delivery_can_count(dateString);
        txtcan_deliver_count.setText(""+j);

        Log.e("First","Before query");
        int k=repo.hand_amount();
        Log.e("First","After query");
        txthand_amt.setText("" + k);

        ImageButton btnPlaceOrder=(ImageButton)rootView.findViewById(R.id.btnPlaceOrder);
        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewBooking.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        ImageButton btnNewPurchase=(ImageButton)rootView.findViewById(R.id.btnNewPurchase);
        btnNewPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewPurchase.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        ImageButton btnDelivery=(ImageButton) rootView.findViewById(R.id.btndelivered);
        btnDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookingListDel.class);
                intent.putExtra("booking_date", dateString);
                startActivity(intent);
                getActivity().finish();
            }
        });


        ImageButton btnExport=(ImageButton)rootView.findViewById(R.id.btnExport);
        btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CustomerList.class);
                startActivity(intent);
                getActivity().finish();

                //new export().execute();
            }
        });



        // Inflate the layout for this fragment
        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}

