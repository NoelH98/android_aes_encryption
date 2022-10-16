package com.noel.bar_hub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/*  @Author Noel.Eugene.Haaba */

public class MainActivity extends AppCompatActivity {

    // Variable declaration
    EditText et,ps;
    TextView textOutput;
    Button  btn,btn2;

    String outputString;
    String AES = "AES";

  @Override
  public void onCreate(Bundle savedInstanceState){
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      et = (EditText) findViewById(R.id.et);
      ps = (EditText) findViewById(R.id.ps);

      textOutput = (TextView) findViewById(R.id.textOutput);
      btn = (Button) findViewById(R.id.btn);
      btn2 = (Button) findViewById(R.id.btn2);

      btn.setOnClickListener(new View.OnClickListener(){
          @Override
          public void onClick(View v){
              try {
                  outputString = encrypt(et.getText().toString(), ps.getText().toString());
                  Toast.makeText(MainActivity.this, "Successful encryption",Toast.LENGTH_SHORT).show();
              }catch (Exception e){
                  Toast.makeText(MainActivity.this, "Cannot Encrypt. Something went wrong!!!",Toast.LENGTH_SHORT).show();
                  e.printStackTrace();
              }
              textOutput.setText(outputString);
          }

      });

      btn2.setOnClickListener(new View.OnClickListener(){
          @Override
          public void onClick(View v){
              try {
                  outputString = decrypt(outputString, ps.getText().toString());
                  Toast.makeText(MainActivity.this, "Successful decryption",Toast.LENGTH_SHORT).show();
              } catch (Exception e) {
                  Toast.makeText(MainActivity.this, "Wrong Password",Toast.LENGTH_SHORT).show();
                  e.printStackTrace();
              }
              textOutput.setText(outputString);
          }
      });
  }

    private String decrypt(String outputString, String s) throws Exception {
        SecretKeySpec key = generateKey(s);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE,key);
        byte[] dv = Base64.decode(outputString,Base64.DEFAULT);
        byte[] dVal = c.doFinal(dv);
        String dec = new String(dVal);
        return dec;
    }

    public String encrypt (String s , String p)throws Exception{
      SecretKeySpec key = generateKey(p);
      Cipher c = Cipher.getInstance(AES);
      c.init(Cipher.ENCRYPT_MODE,key);
      byte[] eVal = c.doFinal(s.getBytes());
      String e = Base64.encodeToString(eVal,Base64.DEFAULT);
      return e;
  }

    private SecretKeySpec generateKey(String p)throws Exception {
      final MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte [] bytes = p.getBytes("UTF-8");
      digest.update(bytes, 0, bytes.length);
      byte[] key = digest.digest();
      SecretKeySpec sks = new SecretKeySpec(key,"AES");
      return sks;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
      super.onActivityResult(requestCode,resultCode,data);

  }

  @Override
    public void onDestroy(){
      super.onDestroy();
  }

}
