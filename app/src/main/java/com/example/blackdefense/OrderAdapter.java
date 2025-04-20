package com.example.blackdefense;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.blackdefense.dataaccess.Order;

import java.util.List;

public class OrderAdapter extends BaseAdapter {
    private Context context;
    private List<Order> ordersList;
    public OrderAdapter(Context context, List<Order> ordersList) {
        this.context = context;
        this.ordersList = ordersList;
    }

    @Override
    public int getCount() {
        return ordersList.size();
    }

    @Override
    public Object getItem(int position) {
        return ordersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.order_card, parent, false);
        }

        TextView orderId = view.findViewById(R.id.orderNumber);
        TextView orderCost = view.findViewById(R.id.orderSum);

        Order order = ordersList.get(position);

        orderId.setText(String.valueOf(order.getId()));
        orderCost.setText("$" + order.getCost());

        return view;
    }
}
