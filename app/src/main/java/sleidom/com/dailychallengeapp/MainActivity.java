package sleidom.com.dailychallengeapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.TextView;
import sleidom.com.dailychallengeapp.notification.NotificationHelper;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends Activity {

    private Controller controller;
    private Challenge dailyChallenge;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        this.controller = new Controller(this);
        initDailyChallenge();
        registerListeners();
        activateNotifications();
    }

    private void initDailyChallenge() {
        this.dailyChallenge = this.controller.getDailyChallenge();
        TextView titleTV = findViewById(R.id.title_challenge);
        TextView descriptionTV = findViewById(R.id.description_challenge);
        titleTV.setText(dailyChallenge.getTitle());
        descriptionTV.setText(dailyChallenge.getDescription());

        GregorianCalendar today = new GregorianCalendar();
        String todayString = today.get(Calendar.YEAR) + "-" + (today.get(Calendar.MONTH) + 1) + "-" + today.get(Calendar.DATE);
        int vote = controller.getVoteOfChallenge(dailyChallenge, todayString);
        FloatingActionButton likeButton = findViewById(R.id.like);
        FloatingActionButton dislikeButton = findViewById(R.id.dislike);
        if (vote != -1) {
            if (vote == 1) {
                likeButton.setImageDrawable(getResources().getDrawable(R.drawable.likepressed));
                dislikeButton.setImageDrawable(getResources().getDrawable(R.drawable.dislike));
            } else {
                likeButton.setImageDrawable(getResources().getDrawable(R.drawable.like));
                dislikeButton.setImageDrawable(getResources().getDrawable(R.drawable.dislikepressed));
            }
        } else {
            controller.registerVote(-1, dailyChallenge);
        }
    }

    private void registerListeners() {
        final FloatingActionButton likeButton = findViewById(R.id.like);
        final FloatingActionButton dislikeButton = findViewById(R.id.dislike);
        final FloatingActionButton historyButton = findViewById(R.id.history);

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.registerVote(1, dailyChallenge);
                likeButton.setImageDrawable(getResources().getDrawable(R.drawable.likepressed));
                dislikeButton.setImageDrawable(getResources().getDrawable(R.drawable.dislike));
            }
        });

        dislikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.registerVote(0, dailyChallenge);
                likeButton.setImageDrawable(getResources().getDrawable(R.drawable.like));
                dislikeButton.setImageDrawable(getResources().getDrawable(R.drawable.dislikepressed));
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HistoryActivityRV.class);
                startActivity(intent);
            }
        });
    }

    private void activateNotifications() {
        NotificationHelper.scheduleRepeatingElapsedNotification(this);
        NotificationHelper.enableBootReceiver(this);
    }
}
