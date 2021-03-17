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

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;



public class EmergencyContacts extends Activity{
    public TextView txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergencycontacts);
        txt = this.findViewById(R.id.txtBox);
        parseXML();
    }

    private void parseXML() {
        XmlPullParserFactory parserFactory;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream is = this.getResources().openRawResource(R.raw.pakistan);
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);

            processParsing(parser);

        } catch (XmlPullParserException e) {

        } catch (IOException e) {
        }
    }

    private void processParsing(XmlPullParser parser) throws IOException, XmlPullParserException{
        ArrayList<Entry> entries = new ArrayList<>();
        int eventType = parser.getEventType();
        Entry currentEntry = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String eltName = null;

            switch (eventType) {
                case XmlPullParser.START_TAG:
                    eltName = parser.getName();

                    if ("Name".equals(eltName)) {
                        currentEntry = new Entry();
                        entries.add(currentEntry);
                    } else if (currentEntry != null) {
                        if ("Name".equals(eltName)) {
                            currentEntry.name = parser.nextText();
                        } else if ("Number".equals(eltName)) {
                            currentEntry.number = parser.nextText();
                        }
                    }
                    break;
            }

            eventType = parser.next();
        }

        printPlayers(entries);
    }

    private void printPlayers(ArrayList<Entry> entries) {
        StringBuilder builder = new StringBuilder();

        for (Entry entry : entries) {
            builder.append(entry.name).append("\n").
                    append(entry.number).append("\n\n");
        }

        txt.setText(builder.toString());
    }

}
class Entry {
    public String name;
    public String number;

    public Entry(){
        this.name = null;
        this.number = null;
    }
    public Entry(String nam, String num) {
        this.name = nam;
        this.number = num;
    }
}



