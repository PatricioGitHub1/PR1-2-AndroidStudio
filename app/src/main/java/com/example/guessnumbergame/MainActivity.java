package com.example.guessnumbergame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private int hidden_number = 0;
    private int num_intentos;
    private String wholeText = "";
    // Crear ArrayList de los datos para introducir en el Ranking
    static ArrayList<Record> records = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Boton de generar y adivinar numero
        final Button button1 = findViewById(R.id.buttonTryGenerate);

        // Boton para acceder al Ranking
        final Button button_ranking =  findViewById(R.id.button2);

        // Texto de los intentos
        final TextView textcheck = (TextView) findViewById(R.id.textView3);

        // Objeto del pad numerico donde se adivina el numero
        final EditText insertNumber = (EditText) findViewById(R.id.editTextNumber);

        // TextView para mostrar la info relevante en partida
        final TextView generalInfo = (TextView) findViewById(R.id.textView4);
        generalInfo.setMovementMethod(new ScrollingMovementMethod());

        final EditText[] input = {new EditText(this.getApplicationContext())};

        // Añadir puntuacions al ArrayList si este esta vacio
        if (records.isEmpty()) {
            records.add(new Record("Juan", 7));
            records.add(new Record("Pepe", 14));
            records.add(new Record("Sarah", 3));
        }

        button1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            public void onClick(View view) {
                if (hidden_number == 0) {
                    hidden_number = (int) (1 + Math.random() * 100);
                    wholeText = "";
                    Toast.makeText(getApplicationContext(), "Numero secreto generado!", Toast.LENGTH_SHORT).show();
                    Log.i("info Secret number", Integer.toString(hidden_number));

                } else {
                    String guess = insertNumber.getText().toString();
                    if (guess.equals("")) {
                        generalInfo.setText("Porfavor, introduce un numero");
                    } else if (Integer.parseInt(guess) < 1 || Integer.parseInt(guess) > 100) {
                        wholeText += "El numero esta en el rango de 1-100\n";
                        generalInfo.setText(wholeText);
                    } else if (Integer.parseInt(guess) == hidden_number) {
                        num_intentos += 1;
                        textcheck.setText("Intentos: " + Integer.toString(num_intentos));
                        wholeText += guess + " | HAS GANADO!!!\n";
                        generalInfo.setText(wholeText);

                        // Guardamos los intentos en esta variable para que no sean sobreescritos por el reseteo del juego
                        int final_points = num_intentos;
                        // Dialogo de fin de partida
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Update Status")
                                .setMessage("Si deseas guardar aparecer en el Ranking escribe un nick y dale a 'Ok'")
                                .setView(input[0])

                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Editable value = input[0].getText();
                                        Log.i("info", "LE DIO QUE SI "+value.toString());
                                        records.add(new Record(value.toString(), final_points));

                                    }
                                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        // Do nothing.
                                        Log.i("info", "LE DIO QUE NO");

                                    }
                                }).show();

                        // Reseteo de datos
                        num_intentos = 0;
                        hidden_number = 0;
                    } else if (Integer.parseInt(guess) > hidden_number) {
                        num_intentos += 1;
                        textcheck.setText("Intentos: " + num_intentos);
                        wholeText += guess + " es mayor que el numero secreto\n";
                        generalInfo.setText(wholeText);
                    } else {
                        num_intentos += 1;
                        textcheck.setText("Intentos: " + num_intentos);
                        wholeText += guess + " es menor que el numero secreto\n";
                        generalInfo.setText(wholeText);
                    }

                    insertNumber.getText().clear();
                }

            }
        });

        button_ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ordenamos array para que este descendente
                Collections.sort(records);

                Intent in = new Intent(MainActivity.this, RankingActivity2.class);
                startActivity(in);
            }
        });
    }

};
class Record implements Comparable<Record> {
    String name;
    int intents;

    public Record(String name, int intents) {
        this.name = name;
        this.intents = intents;
    }

    @Override
    public int compareTo(Record record) {
        return this.intents-record.intents;
    }

}