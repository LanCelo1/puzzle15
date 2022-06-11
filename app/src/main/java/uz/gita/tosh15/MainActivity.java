package uz.gita.tosh15;

import static java.lang.Math.abs;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private Button[][] buttons;
    private Coordinate empty;
    private ArrayList<String> numbers;
    private TextView textScore;
    private TextView game;
    private int score = 0;
    private Chronometer chronometer;
    private LocalStorage localStorage;
    private ImageView sound;
    private boolean soundEffency = true;
    private MediaPlayer mediaplayer;
    private int pos ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main2);
        localStorage = LocalStorage.getLocalStorage();
        /*
         * MediaPlayer
         * */
        // localStorage.setRecordScore(0);
        mediaplayer = MediaPlayer.create(this, R.raw.music);
        mediaplayer.setLooping(true);
        mediaplayer.start();

        initButtons();
        initNumbers();
        loadNumbers();
    }


    private void initButtons() {
        //ViewGroup group = findViewById(R.id.container);
        textScore = findViewById(R.id.score);
        chronometer = findViewById(R.id.chronometer);
        //game = findViewById(R.id.game);
        /*
         *
         * */
        ImageView backHome = findViewById(R.id.imageView8);
        ImageView retry = findViewById(R.id.imageView9);
        ImageView soundEffect = findViewById(R.id.imageView10);
        sound = findViewById(R.id.sound);
        retry.setOnClickListener(
                v -> {
                    loadNumbers();
                    //  Toast.makeText(this, "Refresh Game", Toast.LENGTH_SHORT).show();
                }
        );
        backHome.setOnClickListener(
                v -> {
                    /* Intent intent = new Intent(this,MainActivity3.class);
                    intent.putExtra("score",score);
                    intent.putExtra("time",chronometer.getBase());
                    startActivity(intent);*/
                    finish();

                    //Intent intent = new Intent(this, AboutActivity.class);
                    //  intent.putExtra("Password", "" + score);
                    //startActivity(intent);
                    //  clickRestart(button);

                /*   dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                               @SuppressLint("ResourceAsColor")
                               @Override
                               public void onShow(DialogInterface arg0) {
                                  dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(R.color.white);
                                  dialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(R.color.material_on_primary_emphasis_high_type);
                               }
                           });*/
                  /*  AlertDialog dialog = new AlertDialog.Builder(this)
                            .setView(R.layout.activity_about)
                            .setPositiveButton("New Game",(dialog1, which) -> {
                                dialog1.dismiss();
                                loadNumbers();
                            })
                            .setNegativeButton("Back Menu",(dialog1, which) -> {
                                finish();
                            })
                            .setCancelable(true)
                            .create();
                    dialog.show();*/
                }
        );
        soundEffect.setOnClickListener(
                v -> {
                    if (soundEffency) {
                        sound.setImageResource(R.drawable.ic_music_off);
                        mediaplayer.pause();
                        Toast.makeText(this, "Ovozsiz", Toast.LENGTH_SHORT).show();
                        soundEffency = false;
                    } else {
                        sound.setImageResource(R.drawable.ic_music_on);
                        Toast.makeText(this, "Ovozli", Toast.LENGTH_SHORT).show();
                        mediaplayer.start();
                        soundEffency = true;
                    }

                }
        );
        /*
         *
         * */
        int count = 16;
        buttons = new Button[4][4];
        empty = new Coordinate(3, 3);
        for (int i = 0; i < count; i++) {
            int id = getResources().getIdentifier("btn" + (i + 1), "id", getPackageName());
            Button button = (Button) findViewById(id);
            int x = i / 4;
            int y = i % 4;
            buttons[x][y] = button;
            Coordinate coordinate = new Coordinate(x, y);
            button.setTag(coordinate);
            button.setOnClickListener(v -> clickGameItem(button));
        }
    }

    private void initNumbers() {
        numbers = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            numbers.add(String.valueOf(i));
        }
        numbers.add(String.valueOf(0));
    }

    private void loadNumbers() {
        boolean isPause = localStorage.isPause();
        Collections.shuffle(numbers);
        while (!(validate(numbers))){
            Collections.shuffle(numbers);
        }
        Log.d("TTT","validate ->>"+validate(numbers));
        List<String> _numbers = null;
        if (isPause) {
            _numbers = localStorage.getStringList();
        } else {
            _numbers = numbers;
        }
        for (int i = 0; i < 16; i++) {
            int x = i / 4;
            int y = i % 4;
            Button button = buttons[x][y];
            button.setText(_numbers.get(i));
            if (Integer.parseInt(String.valueOf(button.getText())) == (i + 1)) {
                button.setBackgroundResource(R.drawable.bg_home2);
            } else {
                button.setBackgroundResource(R.drawable.bg_home1);
            }
        }
        if (isPause) {
            Button button = buttons[3][3];
            button.setText(_numbers.get(15));
            button.setBackgroundResource(R.drawable.bg_home1);
            empty = localStorage.getCoordinate();
            buttons[empty.getX()][empty.getY()].setBackgroundResource(R.color.element_empty);
            score = localStorage.getScore();
        } else {
            setDefaultValues();
        }
        textScore.setText(String.valueOf(score));
    }

    private void setDefaultValues(){
        buttons[empty.getX()][empty.getY()].setBackgroundResource(R.color.element_empty);
        buttons[empty.getX()][empty.getY()].setText("");
        textScore.setText("0");
        score = 0;
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
       /* ArrayList data = new ArrayList<Integer>();
        data.add(R.color.teal_200);
        data.add(R.color.teal_700);
        data.add(R.color.purple_200);
        data.add(R.color.purple_500);
        data.add(R.color.purple_700);
        if (chronometer.){
            Collections.shuffle(data);
            Log.d("TTT","AAA");
            game.setTextColor((Integer) data.get(2));
        }*/
    }

    private void clickGameItem(Button button) {
        Coordinate c = (Coordinate) button.getTag();
        int dx = abs(empty.getX() - c.getX());
        int dy = abs(empty.getY() - c.getY());
        if (dx + dy == 1) {
            score++;
            Button emptyButton = buttons[empty.getX()][empty.getY()];
            emptyButton.setText(button.getText());
            if (Integer.parseInt(String.valueOf(button.getText())) == (empty.getX() * 4 + empty.getY() + 1)) {
                emptyButton.setBackgroundResource(R.drawable.bg_home2);
            } else {
                emptyButton.setBackgroundResource(R.drawable.bg_home1);
            }
            button.setBackgroundResource(R.color.element_empty);
            button.setText("");
            empty.setX(c.getX());
            empty.setY(c.getY());
            textScore.setText(String.valueOf(score));
            if (isWin()) {
                //Toast.makeText(this, "You win!", Toast.LENGTH_SHORT).show();
               /* Intent intent = new Intent(this, AboutActivity.class);
                intent.putExtra("Password", "" + score);
                startActivity(intent);
                clickRestart(button);*/
//                AlertDialog dialog = new AlertDialog.Builder(this)
//                        .setView(R.layout.activity_about)
//                        /*.setPositiveButton("New Game",(dialog1, which) -> {
//                            dialog1.dismiss();
//                            loadNumbers();
//                        })*/
//                        .setNegativeButton("Back Menu",(dialog1, which) -> {
//                            finish();
//                        })
//
//                        .setCancelable(false)
//                        .create();
//                dialog.show();
             /*   Intent intent = new Intent(this,MainActivity3.class);
                intent.putExtra("score",textScore.toString());
                intent.putExtra("time",chronometer.getText());
                startActivity(intent);*/
                mediaplayer.pause();
                View view1 = LayoutInflater.from(this).inflate(R.layout.layoput_c, null, false);
                int record = localStorage.getRecordScore();
               // Toast.makeText(this, "" + record, Toast.LENGTH_SHORT).show();
                ((TextView) view1.findViewById(R.id.score)).setText(textScore.getText());
                if (record == 0) {
                    record = score;
                    localStorage.setRecordScore(record);
                }else
                 if (score < record) {
                    record = score;
                    localStorage.setRecordScore(record);
                    view1.findViewById(R.id.cont).setVisibility(View.VISIBLE);
                    view1.findViewById(R.id.newrecord).setVisibility(View.VISIBLE);
                }
                ((TextView) view1.findViewById(R.id.recordScore)).setText(String.valueOf(record));

                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setView(view1)

                        .setPositiveButton("Menuga qaytish", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setCancelable(false)
                        .create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.purple_500));
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(getResources().getColor(R.color.statusbar));
                chronometer.stop();
            }
        }
    }

    public void trueble() {

    }

    public void clickRestart(View view) {
        loadNumbers();
    }

    public void clickFinish(View view) {
        finish();
    }

    private long pauseTime;

    @Override
    protected void onStop() {
        super.onStop();
        mediaplayer.pause();
        pauseTime = chronometer.getBase() - SystemClock.elapsedRealtime();
        chronometer.stop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (pauseTime != 0) {
            if (soundEffency) {
                mediaplayer.start();
            }
            chronometer.setBase(SystemClock.elapsedRealtime() + pauseTime);
            chronometer.start();
        }
    }

    private boolean isWin() {
        if (empty.getX() + empty.getY() != 6) return false;
        for (int i = 0; i < 15; i++) {
            int x = i / 4;
            int y = i % 4;
            Button button = buttons[x][y];
            String s = button.getText().toString();
            if (!s.equals(String.valueOf(i + 1))) return false;
        }
        return true;
    }

    private void saveToStorage() {
        localStorage.setPause(true);
        localStorage.setScore(score);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            int x = i / 4;
            int y = i % 4;
            String text = buttons[x][y].getText().toString();
            list.add(text);
        }
        localStorage.setStringList(list);
        localStorage.setCoordinate(empty);
    }

    private void clearToStorage() {
        localStorage.setPause(false);

    }

    private boolean validate(List<String> list) {
        int result = 0;
        int next = 0, prev = 0;
        for (int i = 0; i <= 15; i++) {
            prev = Integer.parseInt(list.get(i));
            if (prev == 0) {
                empty.setX(i / 4);
                empty.setY(i % 4);
                pos =  4 - i / 4  ;
                continue;
            }
            for (int j = i + 1; j < 16; j++) {
                next = Integer.parseInt(list.get(j));
                if (next == 0) continue;
                    Log.d("TTT",""+prev + " == " + next);
                if ( next < prev){
                    result ++;
                }
            }
        }
        if ((pos % 2 == 0 && result % 2 !=0) || (pos % 2 != 0 && result % 2== 0)){
            return true;
        }
       else return false;
    }
}