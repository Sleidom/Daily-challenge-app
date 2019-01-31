package sleidom.com.dailychallengeapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.HistoryViewHolder> {

    private List<CHistory> challenges;
    private static Context context;

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView title;
        TextView date;
        ImageView vote;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv_history);
            title = itemView.findViewById(R.id.titleChallenge);
            date = itemView.findViewById(R.id.date_challenge);
            vote = itemView.findViewById(R.id.challenge_vote);
        }

        public void bind(final CHistory challenge) {
            title.setText(challenge.getChallenge().getTitle());
            date.setText(challenge.getDate());
            int resId = R.drawable.like;
            if (challenge.getVote() == 0) {
                resId = R.drawable.dislikepressed;
            } else if (challenge.getVote() == 1){
                resId = R.drawable.likepressed;
            }
            vote.setImageResource(resId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChallengeHistoryActivity.class);
                    intent.putExtra("Title", challenge.getChallenge().getTitle());
                    intent.putExtra("Description", challenge.getChallenge().getDescription());
                    intent.putExtra("Date", challenge.getDate());
                    context.startActivity(intent);

                }
            });
        }
    }

    public RVAdapter(List<CHistory> challenges, Context context) {
        this.challenges = challenges;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_history, viewGroup, false);
        HistoryViewHolder hvh = new HistoryViewHolder(v);
        return hvh;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder historyViewHolder, int i) {
        historyViewHolder.bind(challenges.get(i));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return challenges.size();
    }


}
