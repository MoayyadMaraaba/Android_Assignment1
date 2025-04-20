package com.example.blackdefense;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
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

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private Context context;
    private List<Product> productList;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Gson gson = new Gson();
    CartsController cartsController;

    Type cartsListType = new TypeToken<ArrayList<Cart>>() {
    }.getType();

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = prefs.edit();

        cartsController = new CartsController(gson.fromJson(prefs.getString("Carts", ""), cartsListType));
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
            view = inflater.inflate(R.layout.list_item, parent, false);
        }

        ImageView productImage = view.findViewById(R.id.imageView);
        TextView productName = view.findViewById(R.id.productName);
        TextView priceTextView = view.findViewById(R.id.productPrice);
        TextView productQty = view.findViewById(R.id.qty);
        Button buyButton = view.findViewById(R.id.addToCart);

        Product product = productList.get(position);

        productImage.setImageResource(product.getImage());
        productName.setText(product.getName());
        priceTextView.setText("$" + product.getPrice());
        productQty.setText("Qty: " + product.getQuantity());

        User loggedUser = gson.fromJson(prefs.getString("LoggedUser", ""), User.class);

        buyButton.setOnClickListener(v -> {

            if (product.getQuantity() > 0) {

                boolean isAdded = cartsController.addToCart(product, loggedUser.getId());

                if (isAdded) {
                    Toast.makeText(context, "Product: " + product.getName() + " Added to Cart", Toast.LENGTH_SHORT).show();

                    editor.putString("Carts", gson.toJson(cartsController.carts));
                    editor.commit();
                }
            } else {
                Toast.makeText(context, "Out of stock", Toast.LENGTH_SHORT).show();
            }

        });

        return view;
    }
}
