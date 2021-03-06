package com.example.newtrackmyorder;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;


public class UpdateStatusDialog extends DialogFragment {
    private String[] orderList;
    private ArrayList<String> checkedItems ;

    public void setOrderList(String[] orderList){
        this.orderList = orderList;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        checkedItems = new ArrayList<>();
        AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());

        builder.setTitle("Order List").setMultiChoiceItems(orderList, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if(isChecked){
                    // If the user checked the item, add it to the selected items
                    checkedItems.add(orderList[which]);
                } else if(checkedItems.contains(orderList[which])){
                    checkedItems.remove(orderList[which]);
                }
            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User clicked OK, so save the mSelectedItems results somewhere
                // or return them to the component that opened the dialog
                // here we call do updation of data in database
                Toast.makeText(getActivity(), "Updated",Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //user cancel the selection
            }
        });

        return builder.create();
    }
}//UpdateDtatusDialog
