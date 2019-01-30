package sleidom.com.dailychallengeapp;

public class Challenge {

    private String title;
    private String description;

    public Challenge(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

}
