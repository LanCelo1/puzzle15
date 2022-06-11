package uz.gita.tosh15;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        findViewById(R.id.imageView2).setOnClickListener(this::onClick);
        findViewById(R.id.imageView4).setOnClickListener(v -> {
            Toast.makeText(this, "Thanks, for using app :) !", Toast.LENGTH_SHORT).show();
            finishAffinity();
        });
        findViewById(R.id.imageView3).setOnClickListener(v -> {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setView(R.layout.layout_about)
                    .setPositiveButton("Menuga qaytish",(dialog1, which) -> {
                        dialog1.dismiss();
                    })
                    .setCancelable(true)
                    .create();
            dialog.show();
        });
    }

    private void onClick(View view) {
        startActivity(new Intent(view.getContext(), MainActivity.class));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 7) {
            Bundle bundle = data.getExtras();
            String s = bundle.getString("Response","error");
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        } else if (resultCode == 8) {
            finish();
        }
    }
}