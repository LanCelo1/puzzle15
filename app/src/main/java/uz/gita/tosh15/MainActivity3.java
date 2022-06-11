package uz.gita.tosh15;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


public class MainActivity3 extends AppCompatActivity {
    private int Recordscore;
    private String Recordtime;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_congratulation);
        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.statusbar));

        LocalStorage storage = LocalStorage.getLocalStorage();
      //  Recordtime = storage.getRecordTime();



        findViewById(R.id.backCard).setOnClickListener(v -> {
            finishAffinity();
           startActivity(new Intent(this, MenuActivity.class));
        });

        Bundle bundle = getIntent().getExtras();
        int score = bundle.getInt("score");
        String time = bundle.getString("time");

        TextView natija = findViewById(R.id.natija);

        TextView scoretext = findViewById(R.id.score);
      //  TextView timetext = findViewById(R.id.time);
        TextView rScoretext = findViewById(R.id.recordScore);
        /*TextView Rtimetext = findViewById(R.id.recordtime);*/
        scoretext.setText(String.valueOf(score));
        TextView textScore = findViewById(R.id.textView10);
     //   timetext.setText(String.valueOf(time));
        if (Recordscore == 0){
            Recordscore = score;
        }else {
            scoretext.setVisibility(View.INVISIBLE);
            textScore.setVisibility(View.INVISIBLE);
        }
        if (/*Integer.parseInt(Recordtime) > Integer.parseInt(time) ||*/  score < Recordscore){
            natija.setText("Yangi Natija");
            Recordscore = score;
        }
        rScoretext.setText(String.valueOf(Recordscore));
        /*Rtimetext.setText(Recordtime
        );*/
        storage.setRecordScore(Recordscore);
      //  storage.setRecordTime(String.valueOf(Recordtime));
    }
}