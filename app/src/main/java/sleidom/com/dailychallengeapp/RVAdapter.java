package sleidom.com.dailychallengeapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.HistoryViewHolder> {

    List<CHistory> challenges;

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
    }

    public RVAdapter(List<CHistory> challenges) {
        this.challenges = challenges;
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
        historyViewHolder.title.setText(challenges.get(i).getChallenge().getTitle());
        historyViewHolder.date.setText(challenges.get(i).getDate());
        int resId = R.drawable.like;
        if (challenges.get(i).getVote() == 0) {
            resId = R.drawable.dislikepressed;
        } else if (challenges.get(i).getVote() == 1){
            resId = R.drawable.likepressed;
        }
        historyViewHolder.vote.setImageResource(resId);
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
