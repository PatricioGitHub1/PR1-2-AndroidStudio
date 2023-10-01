package com.example.guessnumbergame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private int hidden_number = 0;
    private int num_intentos;
    private String wholeText = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button1 = findViewById(R.id.buttonTryGenerate);

        // Texto de los intentos
        final TextView textcheck = (TextView) findViewById(R.id.textView3);

        // Objeto del pad numerico donde se adivina el numero
        final EditText insertNumber = (EditText) findViewById(R.id.editTextNumber);

        // TextView para mostrar la info relevante en partida
        final TextView generalInfo = (TextView) findViewById(R.id.textView4);
        generalInfo.setMovementMethod(new ScrollingMovementMethod());
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (hidden_number == 0) {
                    hidden_number = (int) (1 + Math.random() * 100);
                    wholeText = "";
                    Toast.makeText(getApplicationContext(), "Numero secreto generado!", Toast.LENGTH_SHORT).show();
                } else {
                    String guess = insertNumber.getText().toString();
                    if (guess.equals("")) {
                        generalInfo.setText("Porfavor, introduce un numero");
                    } else if(Integer.parseInt(guess) < 1 || Integer.parseInt(guess) > 100) {
                        wholeText += "El numero esta en el rango de 1-100\n";
                        generalInfo.setText(wholeText);
                    }
                    else if(Integer.parseInt(guess) == hidden_number) {
                        num_intentos += 1;
                        textcheck.setText("Intentos: "+Integer.toString(num_intentos));
                        wholeText += guess+" | HAS GANADO!!!\nAprete el boton para generar otro numero!\n";
                        generalInfo.setText(wholeText);
                        num_intentos = 0;
                        hidden_number = 0;
                    } else if (Integer.parseInt(guess) > hidden_number) {
                        num_intentos += 1;
                        textcheck.setText("Intentos: "+Integer.toString(num_intentos));
                        wholeText += guess+" es mayor que el numero secreto\n";
                        generalInfo.setText(wholeText);
                    } else {
                        num_intentos += 1;
                        textcheck.setText("Intentos: "+Integer.toString(num_intentos));
                        wholeText += guess+" es menor que el numero secreto\n";
                        generalInfo.setText(wholeText);
                    }
                }

            }
        });
    }
}