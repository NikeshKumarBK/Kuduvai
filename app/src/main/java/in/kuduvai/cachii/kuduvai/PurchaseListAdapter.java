package in.kuduvai.cachii.kuduvai;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cachiii on 7/26/2016.
 */
public class PurchaseListAdapter extends ArrayAdapter {

    List list=new ArrayList();

    public PurchaseListAdapter(Context context, int resource) {
        super(context, resource);
    }

    static class LayoutHandler
    {
        TextView Date,can_intake,can_given,amount_paid,balance;
    }

    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row=convertView;
        LayoutHandler layoutHandler;
        if (row==null)
        {

            LayoutInflater layoutInflater=(LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.view_purchase_details,parent,false);
            layoutHandler=new LayoutHandler();
            layoutHandler.Date=(TextView)row.findViewById(R.id.TextViewDatePurchase);
            layoutHandler.can_intake=(TextView)row.findViewById(R.id.TextViewCanIntakePurchase);
            layoutHandler.can_given=(TextView)row.findViewById(R.id.TextViewCanGivenPurchase);
            layoutHandler.amount_paid=(TextView)row.findViewById(R.id.TextViewAmountPaidPurchase);
            layoutHandler.balance=(TextView)row.findViewById(R.id.TextViewBalancePurchase);
            row.setTag(layoutHandler);

        }
        else {
            layoutHandler=(LayoutHandler)row.getTag();


        }

        PurchaseDataProvider dataProviderPurchase=(PurchaseDataProvider)this.getItem(position);
        layoutHandler.Date.setText(dataProviderPurchase.getDate());
        layoutHandler.can_intake.setText(dataProviderPurchase.getCan_intake());
        layoutHandler.can_given.setText(dataProviderPurchase.getCan_given());
        layoutHandler.amount_paid.setText(dataProviderPurchase.getAmount_paid());
        layoutHandler.balance.setText(dataProviderPurchase.getBalance());


        return row;
    }
}
