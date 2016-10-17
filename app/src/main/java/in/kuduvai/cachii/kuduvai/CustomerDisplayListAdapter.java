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
public class CustomerDisplayListAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public CustomerDisplayListAdapter(Context context, int resource) {
        super(context, resource);
    }

    static class LayoutHandler
    {
        TextView Date,Can_count,Paid,Due;
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
            row=layoutInflater.inflate(R.layout.view_customer_details_list,parent,false);
            layoutHandler=new LayoutHandler();
            layoutHandler.Date=(TextView)row.findViewById(R.id.TxtViewDate);
            layoutHandler.Can_count=(TextView)row.findViewById(R.id.TxtViewCanCount);
            layoutHandler.Paid=(TextView)row.findViewById(R.id.TxtViewPaid);
            layoutHandler.Due=(TextView)row.findViewById(R.id.TxtViewDue);
            row.setTag(layoutHandler);

        }
        else {
            layoutHandler=(LayoutHandler)row.getTag();
        }

        CustomerListDataProvider dataProviderCustomer=(CustomerListDataProvider)this.getItem(position);
        layoutHandler.Date.setText(dataProviderCustomer.getDate());
        layoutHandler.Can_count.setText(dataProviderCustomer.getCan_count());
        layoutHandler.Paid.setText(dataProviderCustomer.getPaid());
        layoutHandler.Due.setText(dataProviderCustomer.getDue());

        return row;
    }

}