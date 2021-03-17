package com.fyp.womensafetyapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SendAlert extends Activity {

    TextView newTextView;
    StringBuffer smsBody;
    String str;

    SQLiteDatabase db;
    ListView contactList;
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> nums = new ArrayList<String>();
    Cursor c;


    LocationValue locationValue;

    public void setLocation() {
        locationValue = new LocationValue() {
            @Override
            public void getCurrentLocation(Location location) {
                // You will get location here if the GPS is enabled
                if (location != null) {
                    Toast.makeText(SendAlert.this, "Send Current Location to contacts?", Toast.LENGTH_LONG).show();
                    Log.d("LOCATION", location.getLatitude() + ", " + location.getLongitude());

                    str = ""+location.getLatitude()+","+location.getLongitude();

                    AlertDialog.Builder alertDialog = new  AlertDialog.Builder(SendAlert.this);
                    alertDialog.setTitle("Send Current Location through SMS?" );
                    alertDialog.setPositiveButton("Yes, send SMS", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sendLocationSMS();
                        }
                    });

                    alertDialog.show();



                }
            }
        };
    }
    private void getCurrentLocation() {
        CustomLocationManager.getCustomLocationManager().getCurrentLocation(this, locationValue);
    }











    private void sendSMS(String mobilenumber){
        smsBody = new StringBuffer();
        smsBody.append("http://maps.google.com?q=");
        smsBody.append(str);
        //mobilenumber="2908";
        SmsManager sms= SmsManager.getDefault();
        sms.sendTextMessage(mobilenumber, null, "Help I need assistance! My location is: "+smsBody, null,null);
        Toast.makeText(SendAlert.this,"Message Successfully Sent",Toast.LENGTH_SHORT).show();
    }



    public void sendLocationSMS() {
        for(int i=0; i<nums.size(); i++){
            sendSMS(nums.get(i));
        }
        return;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        newTextView = this.findViewById(R.id.textView);
        newTextView.setText("Sending Alert to following contacts:");
        contactList = (ListView) this.findViewById(R.id.contactsListView);


        db=openOrCreateDatabase("NumDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS details(name VARCHAR,number VARCHAR);");

        c=db.rawQuery("SELECT * FROM details", null);
        if(c.getCount()==0) {
            return;
        }

        while(c.moveToNext()) {
            names.add(c.getString(0));
            nums.add(c.getString(1));

        }
        showMessage(names, nums);






        setLocation();
        getCurrentLocation();

        //sendLocationSMS();
        //sendSMS();





        }


    public void showMessage(final ArrayList names, final ArrayList nums) {
        final String[] namesArray = {"1"};
        final String[] numsArray = {"2"};
        names.toArray(namesArray);
        nums.toArray(numsArray);
        //adapter = new ArrayAdapter<String>(this, R.layout.listviewfile, names);


        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.listviewfile, android.R.id.text1, names) {
            //@Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText((String) names.get(position));
                text2.setText((String) nums.get(position));
                return view;
            }
        };

        contactList.setAdapter(adapter);
    }

    public void back(View v) {
        finish();
        Intent i_back=new Intent(SendAlert.this,MainActivity.class);
        startActivity(i_back);
        }




}
