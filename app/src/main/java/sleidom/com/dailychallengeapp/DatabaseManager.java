package sleidom.com.dailychallengeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import static sleidom.com.dailychallengeapp.ChallengeContract.ChallengeHistory;
import static sleidom.com.dailychallengeapp.ChallengeContract.SQL_CREATE_HISTORY;
import static sleidom.com.dailychallengeapp.ChallengeContract.SQL_DELETE_HISTORY;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "ChallengesDB.db";
    private Context context;


    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_HISTORY);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public Challenge getDailyChallenge() {
        Challenge challenge = getHistoryDailyChallenge();
        if (challenge != null) return challenge;
        int randInd = new Random().nextInt(11);
        String str = "challenge_" + randInd;
        int resId = context.getResources().getIdentifier(str, "string", context.getPackageName());

        System.out.println(str + " , " + resId);

        String randomChallenge = context.getString(resId);
        challenge = new Challenge(randomChallenge.split(";")[0], randomChallenge.split(";")[1]);
        return challenge;
    }

    private Challenge getHistoryDailyChallenge() {
        SQLiteDatabase db = this.getReadableDatabase();
        GregorianCalendar today = new GregorianCalendar();
        String todayString = today.get(Calendar.YEAR) + "-" + (today.get(Calendar.MONTH) + 1) + "-" + today.get(Calendar.DATE);
        String query = "SELECT " + ChallengeHistory.TITLE + ", " + ChallengeHistory.DESCRIPTION + " FROM " + ChallengeHistory.TABLE_NAME
                + " h WHERE h." + ChallengeHistory.ENTRY_DATE + " = '" + todayString + "'";
        System.out.println(query);

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() == 0) return null;

        Challenge challenge = null;
        String title = null, description = null;
        if (cursor.moveToFirst()) {
            title = cursor.getString(cursor.getColumnIndexOrThrow(ChallengeHistory.TITLE));
            description = cursor.getString(cursor.getColumnIndexOrThrow(ChallengeHistory.DESCRIPTION));
            challenge = new Challenge(title, description);
        }
        return challenge;
    }

    public void updateVote(int vote, Challenge challenge) {
        SQLiteDatabase db = this.getWritableDatabase();
        int historyId = getChallengeHistoryId(challenge);
        GregorianCalendar today = new GregorianCalendar();
        String todayString = today.get(Calendar.YEAR) + "-" + (today.get(Calendar.MONTH) + 1) + "-" + today.get(Calendar.DATE);
        // Insert
        if (historyId == -1) {
            ContentValues cv = new ContentValues();
            cv.put(ChallengeHistory.TITLE, challenge.getTitle());
            cv.put(ChallengeHistory.DESCRIPTION, challenge.getDescription());
            cv.put(ChallengeHistory.LIKE, vote);
            cv.put(ChallengeHistory.ENTRY_DATE, todayString);
            db.insert(ChallengeHistory.TABLE_NAME, null, cv);
        // Update
        } else {
            ContentValues cv = new ContentValues();
            cv.put(ChallengeContract.ChallengeHistory.LIKE, vote);
            String selection = ChallengeHistory._ID+ " = ?";
            String[] selectionArgs = new String[] { String.valueOf(historyId) };
            db.update(ChallengeContract.ChallengeHistory.TABLE_NAME, cv, selection, selectionArgs);
        }
        selectAllHistory();
    }

    private int getChallengeHistoryId(Challenge challenge) {
        SQLiteDatabase db = this.getReadableDatabase();
        int historyId = -1;
        GregorianCalendar today = new GregorianCalendar();
        String todayString = today.get(Calendar.YEAR) + "-" + (today.get(Calendar.MONTH) + 1) + "-" + today.get(Calendar.DATE);
        String query = "SELECT " + ChallengeHistory._ID
            + " FROM " + ChallengeContract.ChallengeHistory.TABLE_NAME + " WHERE " + ChallengeHistory.TITLE + " = " + "\"" + challenge.getTitle() + "\""
                + " AND " + ChallengeHistory.DESCRIPTION + " = " + "\"" + challenge.getDescription() + "\""
                + " AND " + ChallengeHistory.ENTRY_DATE + " = '" + todayString + "'";
        System.out.println(query);

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            historyId = cursor.getInt(cursor.getColumnIndexOrThrow(ChallengeContract.ChallengeHistory._ID));
        }
        return historyId;
    }

    public int getVoteOfChallenge(Challenge challenge, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + ChallengeHistory.LIKE + " FROM " + ChallengeHistory.TABLE_NAME
                + " WHERE " + ChallengeHistory.TITLE + " = " + "\"" + challenge.getTitle() + "\"" + " AND " + ChallengeHistory.ENTRY_DATE + " = '" + date + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            int vote = cursor.getInt(cursor.getColumnIndexOrThrow(ChallengeHistory.LIKE));
            return vote;
        }
        return -1;
    }

    public List<CHistory> getHistory() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CHistory> list = new ArrayList<>();
        String query = "SELECT " + ChallengeHistory.TITLE + "," + ChallengeHistory.DESCRIPTION
                + "," + ChallengeHistory.LIKE + "," + ChallengeHistory.ENTRY_DATE
                + " FROM " + ChallengeHistory.TABLE_NAME + " ORDER BY 4 DESC";

        System.out.println(query);

        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow(ChallengeHistory.TITLE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(ChallengeHistory.DESCRIPTION));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(ChallengeHistory.ENTRY_DATE));
            int vote = cursor.getInt(cursor.getColumnIndexOrThrow(ChallengeHistory.LIKE));

            System.out.println(title + "\t" + description + "\t" + date + "\t" + vote);

            CHistory ch = new CHistory(new Challenge(title, description), date, vote);
            list.add(ch);
        }
        return list;
    }

    private void selectAllHistory() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = " SELECT * FROM " + ChallengeHistory.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        System.out.println("Cursor count: " + cursor.getCount());
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(ChallengeHistory._ID));
            int like = cursor.getInt(cursor.getColumnIndexOrThrow(ChallengeHistory.LIKE));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(ChallengeHistory.ENTRY_DATE));
            System.out.println(id + "\t" + "\t" + like + "\t" + date);
        }
    }

}
