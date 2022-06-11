package uz.gita.tosh15;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class AboutActivity extends AppCompatActivity {
    TextView textAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_about);
     //   textAbout = findViewById(R.id.textAbout);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            textAbout.setText(bundle.getString("Password"));
        }
       // findViewById(R.id.buttonYes).setOnClickListener(v -> clickYes());
        //findViewById(R.id.buttonNo).setOnClickListener(v -> clickNo());
    }

    private void clickYes() {
        Intent intent = new Intent();
        intent.putExtra("Response", "Salomga alik");
        setResult(7, intent);
        finish();
    }

    private void clickNo() {
        setResult(8);
        finish();
    }
}