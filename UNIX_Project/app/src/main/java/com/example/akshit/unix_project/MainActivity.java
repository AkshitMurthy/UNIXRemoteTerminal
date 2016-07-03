package com.example.akshit.unix_project;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.net.*;
import java.io.*;


public class MainActivity extends ActionBarActivity {

    Socket s;
    BufferedReader br;
    BufferedWriter bw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b1=(Button)findViewById(R.id.connectbut);
        Button b2=(Button)findViewById(R.id.runbut);
        b1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                try {
                    String IPAddress=findViewById(R.id.ip).toString();
                    if(IPAddress==null || IPAddress.trim().equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"Please enter IP address",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        s = new Socket(IPAddress, 5000);
                        br = new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF-8"));
                        bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), "UTF-8"));
                        Toast.makeText(getApplicationContext(),"Connected:)",Toast.LENGTH_SHORT).show();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Connection failed:(",Toast.LENGTH_SHORT).show();
                }

            }
        });

       b2.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {

               //Writing
               TextView text= (TextView) findViewById(R.id.command);
               String sen = text.getText().toString();
               if(sen==null || sen.trim().equals("")){
                   Toast.makeText(getApplicationContext(),"Enter a command",Toast.LENGTH_SHORT).show();
               }
               else {
                   try {
                       bw.write(sen);
                       bw.newLine();
                       bw.flush();
                       text.setText("");
                       Toast.makeText(getApplicationContext(), "Running..", Toast.LENGTH_SHORT).show();
                   } catch (IOException e) {
                       e.printStackTrace();
                       Toast.makeText(getApplicationContext(), "Error:(", Toast.LENGTH_SHORT).show();
                   }
               }

               //Reading
               String recmsg = null;
               try {
                   recmsg = br.readLine();
               } catch (IOException e) {
                   e.printStackTrace();
               }
               if (recmsg != null && recmsg != "") {
                   EditText et=(EditText)findViewById(R.id.result);
                   et.setText(recmsg);
                   }
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
