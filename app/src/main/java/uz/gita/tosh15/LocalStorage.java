package uz.gita.tosh15;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class LocalStorage {
    private static LocalStorage localStorage;

    public LocalStorage(Context context) {
        preferences = context.getSharedPreferences("LocalStorage", Context.MODE_PRIVATE);
    }

    public static void init(Context context) {
        localStorage = new LocalStorage(context);
    }

    public static LocalStorage getLocalStorage() {
        return localStorage;
    }

    private SharedPreferences preferences;
    private static final String KEY_PAUSE = "KEY_PAUSE";
    private static final String KEY_SCORE = "KEY_SCORE";
    private static final String KEY_Record_SCORE = "KEY_Record_SCORE";
    private static final String KEY_Record_time = "KEY_Record_time";
    private static final String KEY_COORDINATE_X = "KEY_COORDINATE_X";
    private static final String KEY_COORDINATE_Y = "KEY_COORDINATE_Y";

    public void setPause(boolean state) {
        preferences.edit().putBoolean(KEY_PAUSE, state).apply();
    }

    public boolean isPause() {
        return preferences.getBoolean(KEY_PAUSE, false);
    }

    public void setScore(int score) {
        preferences.edit().putInt(KEY_SCORE, score).apply();
    }

    public int getScore() {
        return preferences.getInt(KEY_SCORE, 0);
    }

    public void setRecordScore(int score) {
//        Log.d("TTT","installed"+score );
        preferences.edit().putInt(KEY_Record_SCORE, score).apply();
    }

    public int getRecordScore() {
       // Log.d("TTT","get installed" + preferences.getInt(KEY_Record_SCORE, 0));
        return preferences.getInt(KEY_Record_SCORE, 0);
    }
    public void setRecordTime(String score) {
        preferences.edit().putString(KEY_Record_time,score).apply();
    }

    public String getRecordTime() {
        return preferences.getString(KEY_Record_time, "00:00");
    }
    public void setCoordinate(Coordinate coordinate) {
        preferences.edit()
                .putInt(KEY_COORDINATE_X, coordinate.getX())
                .putInt(KEY_COORDINATE_Y, coordinate.getY())
                .apply();
    }

    public Coordinate getCoordinate() {
        return new Coordinate(
                preferences.getInt(KEY_COORDINATE_X, 0),
                preferences.getInt(KEY_COORDINATE_Y, 0)
        );
    }


    //------------------------------


    public void setStringList(List<String> list) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("LIST_STRING_COUNT", list.size());
        for (int i = 0; i < list.size(); i++) {
            editor.putString("LIST_STRING_" + i, list.get(i));
        }
        editor.apply();
    }

    public List<String> getStringList() {
        ArrayList<String> list = new ArrayList<>();
        int count = preferences.getInt("LIST_STRING_COUNT", 0);
        for (int i = 0; i < count; i++) {
            String s = preferences.getString("LIST_STRING_" + i, "");
            list.add(s);
        }
        return list;
    }

}
