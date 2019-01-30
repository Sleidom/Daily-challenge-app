package sleidom.com.dailychallengeapp;

import android.provider.BaseColumns;

public final class ChallengeContract {

    private ChallengeContract() {
    }

    public static class ChallengeHistory implements BaseColumns {

        public static final String TABLE_NAME = "history";
        public static String TITLE = "title";
        public static String DESCRIPTION = "description";
        // True = like, false = dislike, null = not decided.
        public static final String LIKE = "like";
        public static final String ENTRY_DATE = "entry_date";
    }

    // LIKE FIELD: -1 means null, 0 false (dislike), 1 true (like)
    public static final String SQL_CREATE_HISTORY =
            " CREATE TABLE " + ChallengeHistory.TABLE_NAME + " (" +
                    ChallengeHistory._ID + " INTEGER PRIMARY KEY," +
                    ChallengeHistory.TITLE + " TEXT, " +
                    ChallengeHistory.DESCRIPTION + " TEXT, " +
                    ChallengeHistory.LIKE + " INTEGER, " +
                    ChallengeHistory.ENTRY_DATE + " DATE)";

    public static final String SQL_DELETE_HISTORY =
            "DROP TABLE IF EXISTS " + ChallengeHistory.TABLE_NAME;

}
