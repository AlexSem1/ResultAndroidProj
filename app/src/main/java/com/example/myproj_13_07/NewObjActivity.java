package com.example.myproj_13_07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class NewObjActivity extends AppCompatActivity {
    EditText nameBox;
    EditText rackBox;
    EditText shelfBox;
    EditText autorBox;
    EditText notesBox;

    Spinner spinner;  //dataKeepForm
    Spinner spinnerType; //dataType
    Spinner spinnerAvailability; //dataAvailability

    Button delButton;
    Button saveButton;

    DBHelper sqlHelper;
    SQLiteDatabase db;
    Cursor ObjCursor;

    long ObjId = 0;
    int spinnerId = 0;
    int spinnerTypeId = 0;
    int spinnerAvailabilityId = 0;

    String[] dataKeepForm = {"На полке", "В ящике", "В коробке"};
    String[] dataType = {"Книга", "Журнал", "Диск", "Прочее"};
    String[] dataAvailability = {"Дома", "Выдано"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_obj);

        nameBox = (EditText) findViewById(R.id.editTextName);
        rackBox = (EditText) findViewById(R.id.editTextRack);
        shelfBox = (EditText) findViewById(R.id.editTextShelf);
        autorBox = (EditText) findViewById(R.id.editTextAutor);
        notesBox = (EditText) findViewById(R.id.editTextNotes);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinnerType = (Spinner) findViewById(R.id.spinnerType);
        spinnerAvailability = (Spinner) findViewById(R.id.spinnerAvailability);

        delButton = (Button) findViewById(R.id.searchButton);
        saveButton = (Button) findViewById(R.id.saveButton);

        sqlHelper = new DBHelper(this);
        db = sqlHelper.getWritableDatabase();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ObjId = extras.getLong("id");
        }

        if (ObjId > 0) {
            // получаем элемент по id из бд
            ObjCursor = db.rawQuery("select * from " + DBHelper.TABLE_OBJ + " where " +
                    "_id" + "=?", new String[]{String.valueOf(ObjId)});
            ObjCursor.moveToFirst();
            nameBox.setText(ObjCursor.getString(1));
            rackBox.setText(String.valueOf(ObjCursor.getInt(2)));
            shelfBox.setText(String.valueOf(ObjCursor.getInt(3)));
            spinnerId = ObjCursor.getInt(4);
            spinnerTypeId = ObjCursor.getInt(5);
            autorBox.setText(ObjCursor.getString(6));
            spinnerAvailabilityId = ObjCursor.getInt(7);
            notesBox.setText(ObjCursor.getString(8));
            ObjCursor.close();
        } else {
            // скрываем кнопку удаления
            delButton.setVisibility(View.GONE);
        }

//SPINNER - Ы
        //Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dataKeepForm);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// адаптер

        //spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Форма хранения"); // заголовок
        spinner.setSelection(spinnerId);// выделяем элемент
        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                spinnerId = position;
                //Toast.makeText(getBaseContext(), "Position = " + spinnerId, Toast.LENGTH_SHORT).show();// показываем позиция нажатого элемента
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        //SpinnerType
        ArrayAdapter<String> adapterType = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, dataType);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // адаптер

        //spinnerType = (Spinner) findViewById(R.id.spinnerType);
        spinnerType.setAdapter(adapterType);
        spinnerType.setPrompt("Тип объекта");// заголовок
        spinnerType.setSelection(spinnerTypeId); // выделяем элемент
        // устанавливаем обработчик нажатия
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                spinnerTypeId = position;// показываем позиция нажатого элемента
                //Toast.makeText(getBaseContext(), "Position = " + spinnerTypeId, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //SpinnerAVAILABILITY
        ArrayAdapter<String> adapterAvailability = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, dataAvailability);
        adapterAvailability.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // адаптер


        spinnerAvailability.setAdapter(adapterAvailability);
        spinnerAvailability.setPrompt("Выдача");// заголовок
        spinnerAvailability.setSelection(spinnerAvailabilityId); // выделяем элемент
        // устанавливаем обработчик нажатия
        spinnerAvailability.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                spinnerAvailabilityId = position;// показываем позиция нажатого элемента
                //Toast.makeText(getBaseContext(), "Position = " + spinnerAvailabilityId, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    public void save(View view){
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_NAME, nameBox.getText().toString());
        cv.put(DBHelper.COLUMN_RACKSNUMBER, Integer.parseInt(rackBox.getText().toString()));
        cv.put(DBHelper.COLUMN_SHELFSNUMBER, Integer.parseInt(shelfBox.getText().toString()));
        cv.put(DBHelper.COLUMN_KEEPFORM, spinnerId);
        cv.put(DBHelper.COLUMN_TYPE, spinnerTypeId);
        cv.put(DBHelper.COLUMN_AUTOR, autorBox.getText().toString());
        cv.put(DBHelper.COLUMN_AVAILABILITY, spinnerAvailabilityId);
        cv.put(DBHelper.COLUMN_NOTES, notesBox.getText().toString());

        if (ObjId > 0) {
            db.update(DBHelper.TABLE_OBJ, cv, "_id" + "=" + String.valueOf(ObjId), null);
        } else {
            db.insert(DBHelper.TABLE_OBJ, null, cv);
        }
        goHome();
    }

    public void delete(View view){
        db.delete(DBHelper.TABLE_OBJ, "_id = ?", new String[]{String.valueOf(ObjId)});
        goHome();
    }

    private void goHome(){

        db.close();// закрываем подключение

        Intent intent = new Intent(this, BasicActivity.class); // переход к главной activity
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
