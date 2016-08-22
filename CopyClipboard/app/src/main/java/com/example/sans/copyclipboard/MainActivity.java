package com.example.sans.copyclipboard;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String KEY ="Clip Key" ;

    ArrayList<String> clipList;
    ClipAdapter mAdapter;
    TextView mTextView;
    Button addToList;
    private  String text;
    ListView mListView;

    ClipboardManager clip;
    Clip mClip = new Clip();
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG, "on Strta");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v(TAG, "on Restart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "on Resume");
        clip = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        text = clip.getText().toString();
        mTextView.setText(text);

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG, "on Stop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "on Destroy");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "on Pause" + clipList.size());
        int size;


            mEditor.putInt("clipSize", clipList.size());
            for (int i=0; i< clipList.size(); i++){
                   // Log.d(TAG, "Clips: " + clipList.get(0).toString());
                    mEditor.putString(KEY + i, clipList.get(i).toString());

            }

            mEditor.apply();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView)findViewById(R.id.listView);
        addToList = (Button)findViewById(R.id.addButton);
        mTextView = (TextView) findViewById(R.id.textView);
         clipList = new ArrayList<>();

       // Toast.makeText(getApplicationContext(), "Not null" ,Toast.LENGTH_SHORT).show();

        Log.v(TAG, "on Create");

       mSharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
       mEditor=mSharedPreferences.edit();

        for(int i=0; i<mSharedPreferences.getInt("clipSize",0);i++){
            String text = mSharedPreferences.getString(KEY+i, null);
            mClip.setText(text);
            //clipList.add(new Clip(text));
            clipList.add(mClip.getText());
//            mAdapter.notifyDataSetChanged();
        }

        mAdapter = new ClipAdapter(getApplicationContext(), clipList);
        mListView.setAdapter(mAdapter);


      addToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mTextView.getText().toString();
                mClip.setText(text);
                // clipList.add(mClip.getText());
                //clipList.add(new Clip(text));
                //Toast.makeText(getApplicationContext(), "Size : "+ clipList.size(), Toast.LENGTH_SHORT).show();
                int size = clipList.size();
                Log.v(TAG, "Count: " + mListView.getCount());

                for (int i = 0; i < mListView.getCount(); i++) {
                    if (text.equals(clipList.get(i))) {
                        //Toast.makeText(getApplicationContext(), "Text already exists", Toast.LENGTH_SHORT).show();
                        Snackbar.make(findViewById(android.R.id.content), "Text already exists", Snackbar.LENGTH_SHORT).show();
                        break;
                    } else if (i == mListView.getCount() - 1) {
                        if (mTextView.getText().toString().isEmpty()) {
                            //Toast.makeText(getApplicationContext(), "It is null", Toast.LENGTH_SHORT).show();
                            Snackbar.make(findViewById(android.R.id.content), "It is null", Snackbar.LENGTH_SHORT).show();

                        } else {
                            Snackbar.make(findViewById(android.R.id.content), "Added", Snackbar.LENGTH_SHORT).show();
                            clipList.add(mClip.getText());
                            mAdapter.notifyDataSetChanged();
                            // Toast.makeText(getApplicationContext(), "Size : "+ mClip.getText(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "list: " + clipList);
                            break;
                        }
                    }

                }
                if (mListView.getCount() == 0) {
                    if (mTextView.getText().toString().isEmpty()) {
                        //Toast.makeText(getApplicationContext(), "It is null", Toast.LENGTH_SHORT).show();
                        Snackbar.make(findViewById(android.R.id.content), "It is null", Snackbar.LENGTH_SHORT).show();
                    } else {
                        //Toast.makeText(getApplicationContext(), "Text does not  exists, first text", Toast.LENGTH_SHORT).show();
                        clipList.add(mClip.getText());
                        mAdapter.notifyDataSetChanged();
                        // Toast.makeText(getApplicationContext(), "Size : "+ mClip.getText(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "list: " + clipList);
                    }

                }
                 /**/
                //Toast.makeText(getApplicationContext(), "Text does not  exists", Toast.LENGTH_SHORT).show();

           }
        });

    }



    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if(item.getItemId() == R.id.refresh){
            Toast.makeText(getApplicationContext(), "Refreshing" ,Toast.LENGTH_SHORT).show();
            clip = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            text = clip.getText().toString();
            mTextView.setText(text);
        }
        else if (item.getItemId() == R.id.clear){
            Toast.makeText(getApplicationContext(), "Clearing" ,Toast.LENGTH_SHORT).show();
            mTextView.setText(null);
            //clipList.clear();
            clip.setText(null);
        }
        else if(item.getItemId() == R.id.clearAll){
            Toast.makeText(getApplicationContext(), "All cleared" ,Toast.LENGTH_SHORT).show();
            mTextView.setText(null);
            clipList.clear();
            mAdapter = new ClipAdapter(getApplicationContext(), clipList);
            mListView.setAdapter(mAdapter);
            clip.setText(null);
        }
        return true;
    }



}
