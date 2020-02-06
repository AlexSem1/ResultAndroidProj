package com.example.myproj_13_07;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class NewRackActivity extends AppCompatActivity {


    public static final String KEY_RACKS = "RacksNumber";
    public static final String KEY_SHELFS = "Shelfs";

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_rack);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DBHelper(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("mLog", "Hello");
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();

                EditText editTextRack = (EditText) findViewById(R.id.editTextNumberRack);
                int rack = Integer.parseInt(editTextRack.getText().toString());
                EditText editTextShelf = (EditText) findViewById(R.id.editTextValueShelf);
                int shelf = Integer.parseInt(editTextShelf.getText().toString());

                contentValues.put(KEY_RACKS, rack);
                contentValues.put(KEY_SHELFS, shelf);

                try {
                    database.insert(DBHelper.TABLE_RACKS, null, contentValues);
                    Snackbar.make(view, "Стеллаж №" + rack + " был успешно добавлен", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } catch (SQLiteConstraintException e) {
                    Snackbar.make(view, "Ошибка. Скорее всего стеллаж №" + rack + " был добавлен ранее", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }


                editTextRack.setText(null);
                editTextShelf.setText(null);
                dbHelper.close();
            }
        });

    }

    public void onDeletRack(View view) {

        dbHelper = new DBHelper(this);

        EditText editTextRack = (EditText) findViewById(R.id.editTextNumberRack);
        int rack = Integer.parseInt(editTextRack.getText().toString());
        EditText editTextShelf = (EditText) findViewById(R.id.editTextValueShelf);

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(DBHelper.TABLE_RACKS, "RacksNumber = " + rack, null);

        Snackbar.make(view, "Стеллаж №" + rack + " был успешно удален", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();


        Cursor c = database.query(DBHelper.TABLE_RACKS, null, null, null, null, null, null);

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("_id");
            int nameColIndex = c.getColumnIndex(KEY_RACKS);
            int emailColIndex = c.getColumnIndex(KEY_SHELFS);

            do {
                // получаем значения по номерам столбцов и пишем все в лог
                Log.d("mLog",
                        "ID = " + c.getInt(idColIndex) + ", name = "
                                + c.getString(nameColIndex) + ", email = "
                                + c.getString(emailColIndex));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false -
                // выходим из цикла
            } while (c.moveToNext());
        } else {
            Log.d("mLog", "o rows");
        }

        editTextRack.setText(null);
        editTextShelf.setText(null);
    }

}
