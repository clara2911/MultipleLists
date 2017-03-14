package com.example.clara.multiplelists;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.clara.multiplelists.R.id.todolistItems;

public class ViewList extends AppCompatActivity {
    private DBManager2 dbManager;
    EditText inputText;
    ListView lvItems;
    ContextMenu menu1;
    ViewList.TodoCursorAdapter todoAdapter;
    Cursor cursor;
    long listid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.deleteDatabase("TODOLISTDATABASE.db");
        inputText = (EditText) findViewById(R.id.inputText);
        menu1 = (ContextMenu) findViewById(R.id.menu1);

        Bundle extras = getIntent().getExtras();
        listid = extras.getLong("listid");
        String sName = extras.getString("listname");
        Log.d("sd","The title of this list: "+sName);
        TextView listname1 = (TextView) findViewById(R.id.empty_list_item);
        if(listname1==null) {
            Log.d("sd","the textview is null");
        }
        if(sName==null)  {
            Log.d("sd","the string for the text is null");
        }
        listname1.setText(sName);

        // initialize db
        dbManager = new DBManager2(this);
        dbManager.open();

        makeItemsAdapter();
        registerForContextMenu(lvItems);
        addExplanations();





    }

    public void makeItemsAdapter() {
        cursor = dbManager.fetchItemsFromList(listid);
        lvItems = (ListView) findViewById(todolistItems);
        todoAdapter = new ViewList.TodoCursorAdapter(this,cursor);
        assert lvItems != null;
        lvItems.setAdapter(todoAdapter);
        todoAdapter.notifyDataSetChanged();
    }

    public void fetchCursor() {
        cursor = dbManager.fetchItemsFromList(listid);
        todoAdapter.changeCursor(cursor);
    }

    public void addExplanations() {
        String expl1 = "Add an item on your to Do List below";
        String expl2 = "Long click on an item to Check off or delete";
        String expl3 = "Good luck!";
        if(!(cursor.moveToNext())) {
            dbManager.insert(expl1,1);
            dbManager.insert(expl2,1);
            dbManager.insert(expl3,1);
        }
        fetchCursor();
        todoAdapter.notifyDataSetChanged();
    }

    public void addItem(View view) {
        String itemToAdd = inputText.getText().toString();
        dbManager.insert(itemToAdd, listid);
        fetchCursor();
        todoAdapter.notifyDataSetChanged();
        if (inputText.length() > 0) {
            inputText.getText().clear();
        }
    }

    public void deleteItem() {
        long itemid = cursor.getInt(cursor.getColumnIndex("_id"));
        dbManager.delete(itemid);
        fetchCursor();
        todoAdapter.notifyDataSetChanged();
    }

    public void checkItem() {
        String prevString = cursor.getString(cursor.getColumnIndex("todoitemname"));
        String doneText;
        if(!prevString.startsWith("DONE: ")) {
            doneText = "DONE: " + cursor.getString(cursor.getColumnIndex("todoitemname"));
        } else {
            doneText = prevString;
        }
        long id = cursor.getInt(cursor.getColumnIndex("_id"));
        dbManager.update(id,doneText);
        fetchCursor();
        todoAdapter.notifyDataSetChanged();
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu2, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int menuItem = item.getItemId();
        switch (menuItem) {
            case R.id.check:
                checkItem();
                return true;
            case R.id.delete:
                deleteItem();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        inputText = (EditText) findViewById(R.id.inputText);

        registerForContextMenu(lvItems);
        menu1 = (ContextMenu) findViewById(R.id.menu1);
        makeItemsAdapter();
        fetchCursor();
        todoAdapter.notifyDataSetChanged();
    }


    public void onDestroy() {
        super.onDestroy();
        //if(dbManager != null) {
         //   dbManager.close();
        //}
    }

    public class TodoCursorAdapter extends CursorAdapter {
        public TodoCursorAdapter(Context context, Cursor cursor) {
            super(context,cursor,0);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            dbManager.open();
            TextView textViewTitle = (TextView) view.findViewById(R.id.listitem);
            String itemname = cursor.getString( cursor.getColumnIndex("todoitemname"));
            textViewTitle.setText(itemname);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }
    }
}
