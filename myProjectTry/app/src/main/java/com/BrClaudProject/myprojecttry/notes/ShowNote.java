package com.BrClaudProject.myprojecttry.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.BrClaudProject.myprojecttry.R;
import com.squareup.picasso.Picasso;

public class ShowNote extends AppCompatActivity {

    TextView tvHeader, tvText;
    ImageView ivShowImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);

        init();
    }

    @Override
    protected void onStart() {
        super.onStart();



        String text = getIntent().getStringExtra("text");
        String header = getIntent().getStringExtra("header");
        String uri = getIntent().getStringExtra("uri");
        if(!uri.equals(""))
        {
            Picasso.get().load(uri).into(ivShowImage);
        }
        tvHeader.setText(header);
        tvText.setText(text);
    }

    private void init(){
        tvHeader = findViewById(R.id.tvHeaderShowNote);
        tvText = findViewById(R.id.tvTextShowNote);
        ivShowImage = findViewById(R.id.ivShowImage);
    }
}