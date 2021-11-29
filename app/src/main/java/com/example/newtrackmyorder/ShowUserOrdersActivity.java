package com.example.newtrackmyorder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newtrackmyorder.util.Constants;
import com.example.newtrackmyorder.util.Prefs;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

//import android.support.v7.app.AppCompatActivity;

public class ShowUserOrdersActivity extends AppCompatActivity {

    String[] orders;
    ListView l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_orders);
        setTitle("My Orders");

        if(getIntent().hasExtra(Constants.CURRENT_USER)) {
            String username = getIntent().getStringExtra(Constants.CURRENT_USER);
            Prefs.putString(Constants.CURRENT_USER, username);

            Firebase currentUserRef = Constants.userRef.child("/" + username);
            currentUserRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String ordersWithUser = (String) dataSnapshot.child("currentOrders").getValue();
                    orders = ordersWithUser.split(Constants.LOCATION_DELIMITER);
                    if (orders.length > 0) {
                        l = (ListView) findViewById(R.id.listView);
                        CustomUserOrderArrayAdapter customUserOrderArrayAdapter = new CustomUserOrderArrayAdapter(ShowUserOrdersActivity.this, orders);
                        l.setAdapter(customUserOrderArrayAdapter);
                        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent = new Intent(ShowUserOrdersActivity.this, OrderDetailActivity.class);
                                intent.putExtra(Constants.ORDER_ID, orders[i]);
                                intent.putExtra(Constants.MAPS_TYPE, "U");
                                startActivity(intent);
                            }

                            private void startActivity(Intent intent) {
                            }
                        });
                    } else {
                        Toast.makeText(ShowUserOrdersActivity.this, "No Active Orders available", Toast.LENGTH_SHORT).show();
                    }
                }

                private Object findViewById(int listView) {
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Toast.makeText(ShowUserOrdersActivity.this, "Check network connection", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(ShowUserOrdersActivity.this, "Invalid User", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void finish() {
    }

    private void setTitle(String my_orders) {
    }

    private Intent getIntent() {
    }

    private void setContentView(int activity_show_user_orders) {
    }

    private class CustomUserOrderArrayAdapter extends  ArrayAdapter<String> {
        Context ctx;
        String orders[];
        public CustomUserOrderArrayAdapter(Context ctx,String[] ordersList) {

            super(ctx,R.layout.user_order_list_item,R.id.text1,ordersList);
            this.ctx = ctx;
            this.orders = ordersList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            if(row == null) {
                LayoutInflater layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = layoutInflater.inflate(R.layout.user_order_list_item, parent, false);
            }
            TextView textView = (TextView) row.findViewById(R.id.text1);
            textView.setText(orders[position]);
            return row;
        }
    }
}