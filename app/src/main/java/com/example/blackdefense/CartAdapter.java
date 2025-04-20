package com.example.blackdefense;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blackdefense.dataaccess.Cart;
import com.example.blackdefense.dataaccess.CartsController;
import com.example.blackdefense.dataaccess.Product;
import com.example.blackdefense.dataaccess.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends BaseAdapter {
    private Context context;
    private List<Product> productList;
    public CartAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
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
            view = inflater.inflate(R.layout.cart_item, parent, false);
        }

        ImageView productImage = view.findViewById(R.id.imageView);
        TextView productName = view.findViewById(R.id.productName);
        TextView priceTextView = view.findViewById(R.id.productPrice);

        Product product = productList.get(position);

        productImage.setImageResource(product.getImage());
        productName.setText(product.getName());
        priceTextView.setText("$" + product.getPrice());

        return view;
    }
}
