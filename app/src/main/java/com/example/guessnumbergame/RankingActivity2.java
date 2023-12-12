package com.example.guessnumbergame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class RankingActivity2 extends AppCompatActivity {
    TableLayout ranksTable;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking2);

        Button backBtn = (Button) findViewById(R.id.backBtn);
        ranksTable = (TableLayout) findViewById (R.id.ranksTable);
        ranksTable.removeAllViews();

        for (Record record : MainActivity.records) {
            addRowToLeaderboard(record);
        }
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(RankingActivity2.this, MainActivity.class);
                startActivity(in);
            }
        });
    }

    private void addRowToLeaderboard(Record record) {
        TableRow row = new TableRow(this);

        // Username column
        TextView usernameTextView = new TextView(this);
        usernameTextView.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        usernameTextView.setText(record.name);
        usernameTextView.setGravity(Gravity.CENTER);
        row.addView(usernameTextView);

        // Intents column
        TextView intentsTextView = new TextView(this);
        intentsTextView.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        intentsTextView.setText(String.valueOf(record.intents));
        intentsTextView.setGravity(Gravity.CENTER);
        row.addView(intentsTextView);

        ranksTable.addView(row);
    }

}