package com.kevguev.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;


public class DisplayNote extends ActionBarActivity {

    private NotesDBAdapter dbHelper;
    private TextView note, time;
    private String titleStr,noteStr, timeStr;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_note);

        note = (TextView) findViewById(R.id.show_content);
        time = (TextView) findViewById(R.id.show_time);

        dbHelper = new NotesDBAdapter(this);
        dbHelper.open();

        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        titleStr = bundle.getString("Title Code");
        noteStr = bundle. getString("Content Code");
        timeStr = bundle.getString("Time Code");
        position = bundle.getInt("Title ID");

        setTitle(titleStr);
        note.setText(noteStr);
        time.setText(timeStr);

        /*
        //very surprised this works lol, next step is to put the new strings in the db
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editBtn.getText().toString().trim().equals("done")){
                    newTitle = hiddenEditTitle.getText().toString();
                    newContent = hiddentEditContent.getText().toString();
                    myTV.setText(newTitle);
                    myTV2.setText(newContent);

                    dbHelper.updateNote(position, newTitle, newContent);
                    Toast.makeText(getApplicationContext(), position + newTitle + newContent, Toast.LENGTH_SHORT).show();
                    dbHelper.close();
                    editBtn.setText("Edit");
                }

                else{
                    editBtn.setText("done");
                }
                textViewClicked();
            }
        });*/
    }

    public void textViewClicked() {
        ViewSwitcher switcher2 = (ViewSwitcher) findViewById(R.id.my_switcher2);
        switcher2.showNext();

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
            dbHelper.deleteNote(position, titleStr);
            dbHelper.close();
            Intent i = new Intent(DisplayNote.this, MainActivity.class);
            startActivity(i);
            return true;
        }
        else if(id == R.id.action_edit){
            Toast.makeText(getApplicationContext(), position, Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
