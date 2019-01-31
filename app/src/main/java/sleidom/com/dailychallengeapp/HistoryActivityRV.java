package sleidom.com.dailychallengeapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class HistoryActivityRV extends AppCompatActivity {

    private RecyclerView rv;
    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_rv);

        getIntent();

        this.controller = new Controller(this);
        this.rv = findViewById(R.id.rvh);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        this.rv.setLayoutManager(llm);

        RVAdapter adapter = new RVAdapter(this.controller.getHistory(), this);
        rv.setAdapter(adapter);
    }

}
