package sleidom.com.dailychallengeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ChallengeHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_history);

        Intent intent = getIntent();
        String title = intent.getStringExtra("Title");
        String description = intent.getStringExtra("Description");
        String date = intent.getStringExtra("Date");

        TextView tvTitle = findViewById(R.id.title_challenge_history);
        TextView tvDesc = findViewById(R.id.description_challenge_history);
        TextView tvDate = findViewById(R.id.date_challenge_history);

        tvTitle.setText(title);
        tvDesc.setText(description);
        tvDate.setText(getString(R.string.accomplished_text) + " " + date);
    }

}
