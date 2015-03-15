package com.indahouser.ruben.myfirstapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.support.v4.view.MotionEventCompat;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.net.URI;


public class MyActivity extends ActionBarActivity {
    public final static String EXTRA_MESSAGE = "com.indahouser.ruben.myfirstapp.MESSAGE";
    public final static String DEBUG_TAG = "MyApp";
    private int numberOfTouches = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
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

    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void callNumber(View view){
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:654249140"));
        startActivity(intent);
    }

    public void showContacts(View view) {
        ContentResolver cr = getContentResolver();
        Cursor cu = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        StringBuffer output = new StringBuffer();


        if (cu.getCount() > 0) {
            while (cu.moveToNext()) {
                String contactName = cu.getString(cu.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                output.append("\n First Name:" + contactName);
                Log.d(DEBUG_TAG, "test");
            }
        }
        TextView textViewAgenda = (TextView) findViewById(R.id.textViewAgenda);
        textViewAgenda.setText(output);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                Log.d(DEBUG_TAG, "Action was DOWN");
                this.numberOfTouches+=1;
                Log.d(DEBUG_TAG, String.valueOf(numberOfTouches));
                TextView counter = (TextView) findViewById(R.id.textView_counter);
                counter.setText(String.valueOf(numberOfTouches));
                return true;
            case (MotionEvent.ACTION_MOVE):
                Log.d(DEBUG_TAG, "Action was MOVE");
                return true;
            case (MotionEvent.ACTION_UP):
                Log.d(DEBUG_TAG, "Action was UP");
                return true;
            case (MotionEvent.ACTION_CANCEL):
                Log.d(DEBUG_TAG, "Action was CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE):
                Log.d(DEBUG_TAG, "Movement occurred outside bounds " +
                        "of current screen element");
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }
}
