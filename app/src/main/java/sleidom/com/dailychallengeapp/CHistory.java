package sleidom.com.dailychallengeapp;

public class CHistory {

    private Challenge challenge;
    private String date;
    private int vote;

    public CHistory(Challenge challenge, String date, int vote) {
        this.challenge = challenge;
        this.date = date;
        this.vote = vote;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }
}
