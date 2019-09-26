package com.stac.hanghangtwo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.stac.hanghangtwo.R;

public class SubActivity extends AppCompatActivity {

    ImageView img_find, img_hang, img_cody, img_closet, img_module, img_new;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        // 걸린 옷 개수 받아와서 textView로 보여주기

        img_find = findViewById(R.id.img_find);
        img_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubActivity.this, FindActivity.class);
                startActivity(intent);
            }
        });

        img_hang = findViewById(R.id.img_hang);
        img_hang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubActivity.this, HangActivity.class);
                startActivity(intent);
            }
        });

        img_cody = findViewById(R.id.img_cody);
        img_cody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubActivity.this, MyCodyActivity.class);
                startActivity(intent);
            }
        });

        img_closet = findViewById(R.id.img_closet);
        img_closet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SubActivity.this, "추후 업데이트될 예정입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        img_module = findViewById(R.id.img_module);
        img_module.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SubActivity.this, "추후 업데이트될 예정입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        img_new = findViewById(R.id.img_new);
        img_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SubActivity.this, "추후 업데이트될 예정입니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
