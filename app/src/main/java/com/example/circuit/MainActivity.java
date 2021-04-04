package com.example.circuit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import static com.example.circuit.sharedFunction.sha256;

public class MainActivity extends AppCompatActivity {



    EditText _txtLogin,_txtPassword;
    Button _btnConnection, _btnInscription;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _txtLogin = (EditText) findViewById(R.id.txtLogin);
        _txtPassword = (EditText) findViewById(R.id.txtPassword);
        _btnConnection = (Button) findViewById(R.id.btnConnection);
        _btnInscription = (Button) findViewById(R.id.btnInscription);
        db = openOrCreateDatabase("dbCircuits",MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS USERS (login varchar primary key, password varchar);");

        SQLiteStatement s = db.compileStatement("select count(*) from users;");
        long c = s.simpleQueryForLong();
        if (c==0){
            db.execSQL("insert into users (login, password) values (?,?)", new String[] {"admin", sha256("123")});
        }
        _btnConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strLogin = _txtLogin.getText().toString();
                String strPwd = _txtPassword.getText().toString();

                Cursor cur = db.rawQuery("select password from users where login =?", new String[] {strLogin});
                try {
                    cur.moveToFirst();
                    String p = cur.getString(cur.getColumnIndex("password"));
                    if (p.equals(sha256(strPwd))){
                        Toast.makeText(getApplicationContext(),"Bienvenue " + strLogin, Toast.LENGTH_LONG).show();
                        if (strLogin.equals("admin")){
                            Intent i = new Intent(getApplicationContext(),CircuitActivity.class);
                            startActivity(i);
                        } else
                        {
                            Intent a = new Intent(getApplicationContext(),ConsulteActivity.class);
                            startActivity(a);

                        }

                    }else{
                        _txtLogin.setText("");
                        _txtPassword.setText("");
                        Toast.makeText(getApplicationContext(),"Echec de connexion",Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    _txtLogin.setText("");
                    _txtPassword.setText("");
                    Toast.makeText(getApplicationContext(),"Utilisateur Inexistant",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        _btnInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strLogin = _txtLogin.getText().toString();
                String strPwd = _txtPassword.getText().toString();

                Cursor cur = db.rawQuery("select login from users where login =?", new String[] {strLogin});
                try {
                    cur.moveToFirst();
                    String p = cur.getString(cur.getColumnIndex("login"));
                    if (p.equals(strLogin)){
                        _txtLogin.setText("");
                        _txtPassword.setText("");
                        Toast.makeText(getApplicationContext(),"utilisateur existant",Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    db.execSQL("insert into users (login, password) values (?,?)", new String[] {strLogin, sha256(strPwd)});
                    _txtLogin.setText("");
                    _txtPassword.setText("");
                    Toast.makeText(getApplicationContext(),"Utilisateur creer avec succes vous pouvez se connecter",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }
}