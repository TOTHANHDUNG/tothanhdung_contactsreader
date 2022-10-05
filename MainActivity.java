package com.example.read_contact;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import kotlinx.coroutines.selects.WhileSelectKt;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getContactList(View view) {
        String strID = "";
        String strName = "";
        String strPhone = "";

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (cursor!=null && cursor.moveToNext()){
            int IdIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            int hasPhone = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
                strID = cursor.getString(IdIndex);
                strName = cursor.getString(nameIndex);
                strPhone = cursor.getString(hasPhone);
                if (cursor.getInt(hasPhone)>0){
                    Cursor pCur = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? ",
                            new String[]{strID},null
                    );
                    while (pCur.moveToNext()){
                        int numberPhone = pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                            strPhone = pCur.getString(numberPhone);
                    }
                }
                Log.i("//============","ID:" + strID);
                Log.i("//============","ID:" + strName);
                Log.i("//============","ID:" + strPhone);
        }
    }
}