package in.sayes.readsms;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ListView lViewSMS = (ListView) findViewById(R.id.listViewSMS);

        if(fetchInbox()!=null)
        {
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, fetchInbox());
            lViewSMS.setAdapter(adapter);
        }
    }

    public ArrayList fetchInbox()
    {
        ArrayList sms = new ArrayList();

        Uri uriSms = Uri.parse("content://sms/inbox");
        Cursor cursor = getContentResolver().query(uriSms, new String[]{"_id", "address", "date", "body"},null,null,null);

        ArrayList<SMSBean.SMSLISTBean> SMSLIST= new ArrayList<>();
        cursor.moveToFirst();
        while  (cursor.moveToNext())
        {
            String address = cursor.getString(1);
            String body = cursor.getString(3);

           /* System.out.println("======&gt; Mobile number =&gt; "+address);
            System.out.println("=====&gt; SMS Text =&gt; "+body);*/

            SMSBean.SMSLISTBean smsBean = new SMSBean.SMSLISTBean();
            smsBean.setPhone_No(address);
            smsBean.setSMS_Body(body);
            smsBean.setDate(cursor.getString(2));
            SMSLIST.add(smsBean);

            sms.add("Address=&gt; "+address+"n SMS =&gt; "+body);


        }
        SMSBean smsBean = new SMSBean();
        smsBean.setSMS_LIST(SMSLIST);
        Gson gson = new Gson();
        String s= gson.toJson(smsBean);
        generateNoteOnSD(MainActivity.this,s);



       /* for (int i= 10; i<100;i++){
            cursor.moveToNext();
            String address = cursor.getString(1);
            String body = cursor.getString(3);

            System.out.println("======&gt; Mobile number =&gt; "+address);
            System.out.println("=====&gt; SMS Text =&gt; "+body);

            sms.add("Address=&gt; "+address+"n SMS =&gt; "+body);

        }*/
        return sms;

    }


    public void generateNoteOnSD(Context context, String sBody) {

        System.out.println("ALLSMS "+sBody);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd");
        Date now = new Date();
        String fileName = formatter.format(now) + "Sourav_SMS.txt";//like 2016_01_12.txt


        try {
            File root = new File(Environment.getExternalStorageDirectory(), "Sourav");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, fileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
