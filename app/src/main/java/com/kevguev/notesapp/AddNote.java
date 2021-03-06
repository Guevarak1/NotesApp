package com.kevguev.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class AddNote extends ActionBarActivity {

    EditText title, content;
    String titleStr, contentStr;
    Button addBtn;
    private NotesDBAdapter dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);


        dbHelper = new NotesDBAdapter(this);
        dbHelper.open();

        title = (EditText) findViewById(R.id.edit_code);
        title.requestFocus();
        content = (EditText) findViewById(R.id.edit_name);

        addBtn = (Button) findViewById(R.id.add_country_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleStr = title.getText().toString();
                contentStr = content.getText().toString();

                dbHelper.createNote(titleStr, contentStr);

                Intent i = new Intent(AddNote.this,MainActivity.class);
                startActivity(i);
                dbHelper.close();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
