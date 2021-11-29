package com.example.newtrackmyorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newtrackmyorder.util.Constants;

public class OrderDetailActivity extends AppCompatActivity {

    TextView orderIdText, orderDelBoy, orderUser, locationText;
    TextView itemInOrderText;
    TextView statusText;
    ImageView staticUserLocationImage;
    String orderId, item, status, userId, delBoyId, mapType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        if(getIntent().hasExtra(Constants.ORDER_ID)) {
            orderId = getIntent().getStringExtra(Constants.ORDER_ID);
            mapType = getIntent().getStringExtra(Constants.MAPS_TYPE);
            if(orderId != null) {
                //Realm realm = Realm.getDefaultInstance();
                //Order order = realm.where(Order.class).equalTo("orderId", orderId).findFirst();
                //final User user = realm.where(User.class).equalTo("currentOrderId", orderId).findFirst();

                orderIdText = (TextView) orderIdText.findViewById();
                itemInOrderText = (TextView) orderIdText.findViewById();
                statusText = (TextView) orderIdText.findViewById();
                staticUserLocationImage = (ImageView) locationText.findViewById();
                orderDelBoy = (TextView) orderDelBoy.findViewById();
                orderUser = (TextView) orderUser.findViewById();
                locationText = (TextView) locationText.findViewById();

                Firebase currentOrderRef = Constants.orderRef.child("/" + orderId);
                currentOrderRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        item = (String) dataSnapshot.child("item").getValue();
                        status = (String) dataSnapshot.child("status").getValue();
                        userId = (String) dataSnapshot.child("userId").getValue();
                        delBoyId = (String) dataSnapshot.child("delBoyId").getValue();

                        orderIdText.setText(orderId);
                        itemInOrderText.setText(item);
                        if(status.equals(Constants.STATUS_GOING_TO_PICKUP)) {
                            statusText.setText("Going to pickup");
                        } else if (status.equals(Constants.STATUS_PICKEDUP)) {
                            statusText.setText("Item picked up");
                        } else if(status.equals(Constants.STATUS_DELIVERED)) {
                            statusText.setText("Item Delivered");
                        }
                        orderDelBoy.setText(delBoyId);
                        orderUser.setText(userId);

                        if (!status.equals(Constants.STATUS_DELIVERED)) {
                            locationText.setVisibility(View.VISIBLE);
                            if (mapType.equals("D")) {
                                Firebase currentUserRef = Constants.userRef.child("/" + userId);
                                currentUserRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String latLan = (String) dataSnapshot.child("currentLocation").getValue();
                                        String placeUrl = "https://maps.googleapis.com/maps/api/staticmap?center=" + latLan + "&zoom=17&size=1200x250&maptype=roadmap&markers=color:red%7C" + latLan + "&key=AIzaSyBP_hEMi4Pu0VGFRRzeFH4Podfkf2qGEks";
                                        Picasso.with(OrderDetailActivity.this).load(placeUrl).resize(staticUserLocationImage.getWidth(), 300).centerCrop().into(staticUserLocationImage);
                                    }

                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {
                                        Toast.makeText(OrderDetailActivity.this, "Check network connection", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else if (mapType.equals("U")) {
                                Firebase currentDelBoyRef = Constants.delboyRef.child("/" + delBoyId);
                                currentDelBoyRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String latLan = (String) dataSnapshot.child("currentLocation").getValue();
                                        String placeUrl = "https://maps.googleapis.com/maps/api/staticmap?center=" + latLan + "&zoom=17&size=1200x250&maptype=roadmap&markers=color:red%7C" + latLan + "&key=AIzaSyBP_hEMi4Pu0VGFRRzeFH4Podfkf2qGEks";
                                        Picasso.with(OrderDetailActivity.this).load(placeUrl).resize(staticUserLocationImage.getWidth(), 300).centerCrop().into(staticUserLocationImage);
                                    }

                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {
                                        Toast.makeText(OrderDetailActivity.this, "Check network connection", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            staticUserLocationImage.setOnClickListener(new View.OnClickListener() {
                                class MAPS_TYPE {
                                }

                                @Override
                                public void onClick(View view) {
                                    //Go to Map Activity
                                    Intent intent = new Intent(OrderDetailActivity.this, MapsActivity.class);
                                    intent.putExtra(MAPS_TYPE, mapType);
                                    intent.putExtra(Constants.CURRENT_USER, userId);
                                    intent.putExtra(Constants.CURRENT_DELBOY, delBoyId);
                                    startActivity(intent);
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        Toast.makeText(OrderDetailActivity.this, "Check network connection", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            Toast.makeText(this, "Invalid Order", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void finish() {
    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, DboyActivity.class);
        intent.putExtra(Constants.CURRENT_DELBOY, Prefs.getString(Constants.CURRENT_DELBOY, null));
        startActivity(intent);
    }*/
}
