package com.stac.hanghangtwo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.stac.hanghangtwo.R;

public class MyCodyActivity extends AppCompatActivity {

    ImageView btn_make_cody;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cody);

        btn_make_cody = findViewById(R.id.btn_make_cody);
        btn_make_cody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyCodyActivity.this, MakeCodyActivity.class);
                startActivity(intent);
            }
        });
    }
}
