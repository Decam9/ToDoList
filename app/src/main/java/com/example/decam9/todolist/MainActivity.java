package com.example.decam9.todolist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView ListView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        ListView1 = (ListView) findViewById(R.id.ListView1);
        ListView1.setAdapter(itemsAdapter);

        //mock input
        //items.add("First");
        //items.add("Second");

        setupListViewListener();

    }

    public void onAddItem(View v) {
        EditText Entry = (EditText) findViewById(R.id.Entry);
        String itemText = Entry.getText().toString();
        itemsAdapter.add(itemText);
        Entry.setText("");
        writeItems();
        Toast.makeText(getApplicationContext(), "Item was added to your list!", Toast.LENGTH_SHORT).show();
    }

    private void setupListViewListener() {
        Log.i("MainActivity", "Setting up listener On list view");
        ListView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("MainActivity", "Item removed from list: " + position);
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }
    private File getDatafile(){
        return new File(getFilesDir(), "todo.txt");
    }
    private void readItems(){
        try {
            items = new ArrayList<>(FileUtils.readLines(getDatafile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("Main Activity", "Error Reading file",e);
            items= new ArrayList<>();
        }
    }
    private void writeItems(){
        try {
            FileUtils.writeLines(getDatafile(), items);
        } catch (IOException e) {
            Log.e("Main Activity", "Error Writing file", e);
        }
    }
}
