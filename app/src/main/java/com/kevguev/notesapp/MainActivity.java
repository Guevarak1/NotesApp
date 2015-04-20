package com.kevguev.notesapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private NotesDBAdapter dbHelper;
    private SimpleCursorAdapter dataAdapter;
    private Button addCountryBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new NotesDBAdapter(this);
        dbHelper.open();

        //dbHelper.deleteAllNotes();
        //dbHelper.hardCodeNotes();
        displayListView();
        addNoteToList();
    }

    @Override
    protected void onResume() {
        super.onResume();

        dbHelper = new NotesDBAdapter(this);
        dbHelper.open();
        displayListView();
    }

    private void addNoteToList(){
        addCountryBtn = (Button) findViewById(R.id.add_button);
        addCountryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddNote.class);
                startActivity(i);
                dbHelper.close();
            }
        });
    }

    private void displayListView() {

        Cursor cursor = dbHelper.fetchAllNotes();

        String[] columns = new String[]{
                NotesDBAdapter.KEY_TITLE,
                NotesDBAdapter.KEY_CONTENT,
                NotesDBAdapter.KEY_TIME
        };

        int[] to = new int[]{
                R.id.title,
                R.id.content,
                R.id.time,
        };

        dataAdapter = new SimpleCursorAdapter(this, R.layout.note_info,
                cursor,
                columns,
                to,
                0);

        ListView listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(dataAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);

                // bundle the strings from here to the display note activity with cursors!!
                String titleCode = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String contentCode = cursor.getString(cursor.getColumnIndexOrThrow("content"));
                String timeCode = cursor.getString(cursor.getColumnIndexOrThrow("time"));

                //bundle key value pairs
                Bundle bundle = new Bundle();
                bundle.putLong("Title ID", position);
                bundle.putString("Title Code", titleCode);
                bundle.putString("Content Code", contentCode);
                bundle.putString("Time Code", timeCode);

                //put them in the intent and start activity DisplayNote
                Intent i = new Intent(MainActivity.this, DisplayNote.class);
                i.putExtras(bundle);
                startActivity(i);

                Toast.makeText(getApplicationContext(),
                        titleCode, Toast.LENGTH_SHORT).show();

            }
        });

        EditText myFilter = (EditText) findViewById(R.id.myFilter);

        myFilter.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start,int before, int count) {
                dataAdapter.getFilter().filter(s.toString());
            }
        });

        dataAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint)  {
                return dbHelper.fetchNotesByName(constraint.toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
