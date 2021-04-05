package com.example.circuit;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ConsulteActivity extends AppCompatActivity {

    Cursor cur;
    SQLiteDatabase db;
    LinearLayout layNaviguer, layRecherche, layR;
    EditText _txtRechercheV, _txtVilleD,_txtVilleS,_txtPrix,_txtDuree, _txtPinf, _txtPsup;
    ImageButton _btnRechercheV, _btnRPrix;
    Button _btnFirst,_btnPrevious,_btnNext,_btnLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulte);


        layNaviguer = (LinearLayout) findViewById(R.id.layNaviguer);
        layRecherche= (LinearLayout) findViewById(R.id.layRecherche);
        layR= (LinearLayout) findViewById(R.id.LayR);
        _txtRechercheV = (EditText) findViewById(R.id.txtRechercheV);
        _txtVilleD = (EditText) findViewById(R.id.txtVilleD);
        _txtVilleS = (EditText) findViewById(R.id.txtVilleS);
        _txtPrix = (EditText) findViewById(R.id.txtPrix);
        _txtDuree = (EditText) findViewById(R.id.txtDuree);
        _txtPinf = (EditText) findViewById(R.id.txtPinf);
        _txtPsup = (EditText) findViewById(R.id.txtPsup);


        _btnFirst = (Button) findViewById(R.id.btnFirst);
        _btnPrevious = (Button) findViewById(R.id.btnPrevious);
        _btnNext = (Button) findViewById(R.id.btnNext);
        _btnLast = (Button) findViewById(R.id.btnLast);


        _btnRechercheV = (ImageButton) findViewById(R.id.btnRechercheV);
        _btnRPrix = (ImageButton) findViewById(R.id.btnRPrix);
        db = openOrCreateDatabase("dbCircuits",MODE_PRIVATE,null);
        // Création de la table comptes
        db.execSQL("CREATE TABLE IF NOT EXISTS CIRCUITs (id integer primary key autoincrement, villeDepart VARCHAR, villeArrive VARCHAR, prix REAL, duree INTEGER);");


        _btnRechercheV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cur = db.rawQuery("select * from circuits where villeDepart like ?", new String[]{ _txtRechercheV.getText().toString()});
                try {
                    cur.moveToFirst();
                    _txtVilleD.setText(cur.getString(1));
                    _txtVilleS.setText(cur.getString(2));
                    _txtPrix.setText(cur.getString(3));
                    _txtDuree.setText(cur.getString(4));
                    if (cur.getCount() == 1){
                        layNaviguer.setVisibility(View.INVISIBLE);
                    } else {
                        layNaviguer.setVisibility(View.VISIBLE);
                        _btnPrevious.setEnabled(false);
                        _btnNext.setEnabled(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"aucune résultat.",Toast.LENGTH_SHORT).show();
                    _txtVilleD.setText("");
                    _txtVilleS.setText("");
                    _txtPrix.setText("");
                    _txtDuree.setText("");
                    layNaviguer.setVisibility(View.INVISIBLE);
                }
            }
        });

        _btnRPrix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cur = db.rawQuery("select * from circuits where prix between ? and ? ", new String[]{_txtPinf.getText().toString(),_txtPsup.getText().toString()});

                try {
                    cur.moveToFirst();
                    _txtVilleD.setText(cur.getString(1));
                    _txtVilleS.setText(cur.getString(2));
                    _txtPrix.setText(cur.getString(3));
                    _txtDuree.setText(cur.getString(4));
                    if (cur.getCount() == 1){
                        layNaviguer.setVisibility(View.INVISIBLE);
                    } else {
                        layNaviguer.setVisibility(View.VISIBLE);
                        _btnPrevious.setEnabled(false);
                        _btnNext.setEnabled(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"aucune résultat.",Toast.LENGTH_SHORT).show();
                    _txtVilleD.setText("");
                    _txtVilleS.setText("");
                    _txtPrix.setText("");
                    _txtDuree.setText("");
                    layNaviguer.setVisibility(View.INVISIBLE);
                }
            }
        });
        _btnFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    cur.moveToFirst();
                    _txtVilleD.setText(cur.getString(1));
                    _txtVilleS.setText(cur.getString(2));
                    _txtPrix.setText(cur.getString(3));
                    _txtDuree.setText(cur.getString(4));
                    _btnPrevious.setEnabled(false);
                    _btnNext.setEnabled(true);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"aucune resultat.",Toast.LENGTH_SHORT).show();

                }
            }
        });
        _btnLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    cur.moveToLast();
                    _txtVilleD.setText(cur.getString(1));
                    _txtVilleS.setText(cur.getString(2));
                    _txtPrix.setText(cur.getString(3));
                    _txtDuree.setText(cur.getString(4));
                    _btnPrevious.setEnabled(true);
                    _btnNext.setEnabled(false);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"aucune resultat.",Toast.LENGTH_SHORT).show();

                }
            }
        });

        _btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    cur.moveToNext();
                    _txtVilleD.setText(cur.getString(1));
                    _txtVilleS.setText(cur.getString(2));
                    _txtPrix.setText(cur.getString(3));
                    _txtDuree.setText(cur.getString(4));
                    _btnPrevious.setEnabled(true);
                    if (cur.isLast()){
                        _btnNext.setEnabled(false);
                    }


                } catch (Exception e) {
                    e.printStackTrace();


                }
            }
        });

        _btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    cur.moveToPrevious();
                    _txtVilleD.setText(cur.getString(1));
                    _txtVilleS.setText(cur.getString(2));
                    _txtPrix.setText(cur.getString(3));
                    _txtDuree.setText(cur.getString(4));
                    _btnNext.setEnabled(true);
                    if (cur.isFirst()){
                        _btnPrevious.setEnabled(false);
                    }


                } catch (Exception e) {
                    e.printStackTrace();


                }
            }
        });

    }
}