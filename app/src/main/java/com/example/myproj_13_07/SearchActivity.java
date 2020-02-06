package com.example.myproj_13_07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

public class SearchActivity extends AppCompatActivity {

    ListView objList;
    DBHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    EditText searchText;

    Spinner spinnerSearch;

    String textForSearch;

    int spinnerSearhId = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        spinnerSearch = (Spinner) findViewById(R.id.spinnerSearch);

        ArrayAdapter<String> adapterAvailability = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, DBHelper.dataAvailability);
        adapterAvailability.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // адаптер


        spinnerSearch.setAdapter(adapterAvailability);
        spinnerSearch.setPrompt("Выдача");// заголовок
        spinnerSearch.setSelection(spinnerSearhId); // выделяем элемент
        // устанавливаем обработчик нажатия
        spinnerSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                spinnerSearhId = position;// показываем позиция нажатого элемента
                //Toast.makeText(getBaseContext(), "Position = " + spinnerAvailabilityId, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    public void Search(View view) {
        searchText = (EditText) findViewById(R.id.editSearchText);
        textForSearch = searchText.getText().toString();

        objList = (ListView) findViewById(R.id.listSearch);
        objList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), NewObjActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        databaseHelper = new DBHelper(getApplicationContext());

        db = databaseHelper.getReadableDatabase();// открываем подключение
        userCursor = db.rawQuery("select * from " + DBHelper.TABLE_OBJ + " where " +
                DBHelper.COLUMN_NAME + " LIKE '%" + textForSearch + "%' AND " +
                DBHelper.COLUMN_AVAILABILITY + " = " + spinnerSearhId, null); //получаем данные из бд в виде курсора
        String[] headers = new String[]{DBHelper.COLUMN_NAME, DBHelper.COLUMN_TYPE};// определяем, какие столбцы из курсора будут выводиться в ListView

        userAdapter = new MyAdapter(this, android.R.layout.two_line_list_item,
                userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0); // создаем адаптер, передаем в него курсор
        objList.setAdapter(userAdapter);
    }

   /* @Override
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
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        userCursor.close();
    }
}
