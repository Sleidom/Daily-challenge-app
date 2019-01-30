package sleidom.com.dailychallengeapp;

import android.content.Context;

import java.util.List;

public class Controller {

    private DatabaseManager dm;

    public Controller(Context context) {
        this.dm = new DatabaseManager(context);
    }

    public Challenge getDailyChallenge() {
        Challenge challenge = this.dm.getDailyChallenge();
        return challenge;
    }

    public void registerVote(int vote, Challenge challenge) {
        this.dm.updateVote(vote, challenge);
    }

    public int getVoteOfChallenge(Challenge challenge, String date) {
        return this.dm.getVoteOfChallenge(challenge, date);
    }

    public List<CHistory> getHistory() {
        return this.dm.getHistory();
    }
}
