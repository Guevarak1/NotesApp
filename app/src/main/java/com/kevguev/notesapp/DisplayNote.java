package com.kevguev.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewSwitcher;


public class DisplayNote extends ActionBarActivity {

    private NotesDBAdapter dbHelper;
    private TextView note, time;
    private String titleStr,noteStr, timeStr,position, newContent;
    private EditText hiddenEditContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_note);

        note = (TextView) findViewById(R.id.show_content);
        time = (TextView) findViewById(R.id.show_time);
        hiddenEditContent= (EditText) findViewById(R.id.hidden_edit_content);

        dbHelper = new NotesDBAdapter(this);
        dbHelper.open();

        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        titleStr = bundle.getString("Title Code");
        noteStr = bundle. getString("Content Code");
        timeStr = bundle.getString("Time Code");
        position = bundle.getString("Title ID");


        setTitle(titleStr);
        note.setText(noteStr);
        time.setText(timeStr);

    }

    public void edit() {
        ViewSwitcher switcher2 = (ViewSwitcher) findViewById(R.id.my_switcher2);
        switcher2.showNext();
        hiddenEditContent.setText(noteStr);
    }

    /**
     * if not edited it, and back is pressed, then its replaced by nothing
     */
    @Override
    protected void onStop() {
        super.onStop();

        newContent = hiddenEditContent.getText().toString();
        if(!newContent.equals("")){
            dbHelper.updateNote(Integer.parseInt(position), titleStr, newContent, timeStr);
            dbHelper.close();
            startActivity(new Intent(this,MainActivity.class));

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_display_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_delete){

            dbHelper.deleteNote(Integer.parseInt(position), titleStr);
            dbHelper.close();
            Intent i = new Intent(DisplayNote.this, MainActivity.class);
            startActivity(i);
            return true;
        }
        else if(id == R.id.action_edit){
            edit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
