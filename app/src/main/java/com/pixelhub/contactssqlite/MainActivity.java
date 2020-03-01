package com.pixelhub.contactssqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DbAdapter db;
    SimpleCursorAdapter adapter;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //calling DbAdapter
        db = new DbAdapter(this);
        db.open();

        db.insert("Color Picker","+80885654","info@icyarena.com","US");
        db.insert("Mike Helen","+80883437","halen@icyarena.com","UK");
        db.insert("Tarek Pink","+80881234","robart@icyarena.com","AUS");
        db.insert("Hasan ","+80881233","robart@icyarena.com","AUS");
        db.insert("Sumon ","+8088122","robart@icyarena.com","AUS");
        db.insert("Robart","+80881225","robart@icyarena.com","AUS");

        //display data
       lv = (ListView) findViewById(R.id.listView1);
        int layoutstyle=R.layout.liststyle;
        int[] xml_id = new int[] {
                R.id.txtname,
                R.id.txtnumber
        };
        String[] column = new String[] {
                "name",
                "number"
        };
        Cursor row = db.fetchAllData();
        adapter = new SimpleCursorAdapter(this, layoutstyle,row,column, xml_id, 0);
        lv.setAdapter(adapter);

        //onClick function
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterview, View view, int position, long id) {
                Cursor cursor = (Cursor) adapterview.getItemAtPosition(position);
                String ID = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                Toast.makeText(getApplicationContext(),ID, Toast.LENGTH_SHORT).show();
            }
        });

        //dispay data by filter
//        EditText et = (EditText) findViewById(R.id.myFilter);
////        et.addTextChangedListener(new TextWatcher() {
////            public void afterTextChanged(Editable s) {
////            }
////            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
////            }
////            public void onTextChanged(CharSequence s, int start, int before, int count) {
////                if(s.length()==0){
////
////                    lv.setVisibility(View.GONE);
////                }
////                else {
////                    adapter.getFilter().filter(s.toString());
////                    lv.setVisibility(View.VISIBLE);
////                }
////                lv.setAdapter(adapter);
////            }
////        });
////        adapter.setFilterQueryProvider(new FilterQueryProvider() {
////            public Cursor runQuery(CharSequence constraint) {
////                return db.fetchdatabyfilter(constraint.toString(),"name");
////            }
////        });

        //dispay data by filter
        EditText et = (EditText) findViewById(R.id.myFilter);
        et.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }
        });
        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                return db.fetchdatabyfilter(constraint.toString(),"name");
            }
        });

    }

    //code for add button
    public void addContact(View v){
        //
    }
}
