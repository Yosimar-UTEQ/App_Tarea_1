package com.example.app_tarea;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import WebService.Asynchtask;
import WebService.WebService;

public class Activity2 extends AppCompatActivity implements Asynchtask {

    private TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        tv1=(TextView) findViewById(R.id.txt_contactos);
        tv1.setMovementMethod(new ScrollingMovementMethod());
        tv1.setVisibility(View.INVISIBLE);
        Map<String, String> datos = new HashMap<String, String>();
        WebService ws= new WebService("http://api.androidhive.info/contacts/",datos,Activity2.this,Activity2.this);
        ws.execute("");
    }

    @Override
    public void processFinish(String result) throws JSONException {
        Log.i("processFinish",result);
        JSONObject  jsonObj = new JSONObject(result);
        ArrayList<HashMap<String, String>> contactList = null;
        JSONArray contacts = jsonObj.getJSONArray("contacts");
        for (int i=0; i<contacts.length();i++){
            JSONObject obj= contacts.getJSONObject(i);
            String id =obj.getString("id");
            String name=obj.getString("name");
            String email=obj.getString("email");
            String address=obj.getString("address");
            String gender=obj.getString("gender");

            JSONObject phone= obj.getJSONObject("phone");
            String mobile= phone.getString("mobile");
            String home = phone.getString("home");
            String office=phone.getString("office");

            HashMap<String, String> contactos = new HashMap<String, String>();
            contactos.put("id",id);
            contactos.put("name",name);
            contactos.put("email",email);
            contactos.put("address",address);
            contactos.put("mobile",mobile);
            contactList.add(contactos);
        }
        for(int i=0;i<contactList.size();i++){
            tv1.setText(contactList.get(i).toString());
        }
        tv1.setVisibility(View.VISIBLE);
    }
}
