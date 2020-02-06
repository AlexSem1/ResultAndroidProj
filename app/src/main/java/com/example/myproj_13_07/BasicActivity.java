package com.example.myproj_13_07;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class BasicActivity extends AppCompatActivity {

    ListView objList;
    DBHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        objList = (ListView) findViewById(R.id.list);
        objList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), NewObjActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        databaseHelper = new DBHelper(getApplicationContext());

    }

    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение
        db = databaseHelper.getReadableDatabase();

        //получаем данные из бд в виде курсора
        userCursor = db.rawQuery("select * from " + DBHelper.TABLE_OBJ, null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        //int idType = userCursor.getInt(5);
        String[] headers = new String[]{DBHelper.COLUMN_NAME, DBHelper.COLUMN_TYPE};
        // создаем адаптер, передаем в него курсор
        userAdapter = new MyAdapter(this, android.R.layout.two_line_list_item,
                userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        objList.setAdapter(userAdapter);
    }

    public void onFAButtonClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                startActivity(new Intent(this, NewObjActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        userCursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_basic, menu);
        return true;
    }

    public void onMenuButtonClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_rack:
                startActivity(new Intent(this, NewRackActivity.class));
                break;
            case R.id.app_bar_search:
                startActivity(new Intent(this, SearchActivity.class));
                break;
            default:
                break;
        }
    }
}

class MyAdapter extends SimpleCursorAdapter {

    public MyAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    @Override
    public void setViewText(TextView v, String text) {
        super.setViewText(v, text);
        if (v.getId() == android.R.id.text2) {
            int i = Integer.parseInt(text);
            v.setText(DBHelper.dataType[i]);
        }
    }
}


//Говно
 /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.fab:
                        startActivity(new Intent(this,NewObjActivity.class));
                        break;
                    default:
                        break;
                }

                //startActivity(new Intent(,NewObjActivity.class));
                *//*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*//*
            }
        });*/