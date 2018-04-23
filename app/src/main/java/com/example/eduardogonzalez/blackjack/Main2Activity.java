package com.example.eduardogonzalez.blackjack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button goBack = (Button) findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
            }
        });
        Bundle extras = getIntent().getExtras();
        String[] Urls = null;
        if (extras != null) {
            Urls = extras.getStringArray("Cards");
        }

        if (Urls != null) {
            for (int i = 0; i < Urls.length; i++) {
                if (i > 10) {
                    break;
                }
                loadFromUrl(Urls[i], getImageView(i));
            }
        }
    }

    public void loadFromUrl(final String url, ImageView image) {
        Picasso.with(this).load(url).placeholder(R.mipmap.ic_launcher).into(image, new com.squareup.picasso.Callback(){
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });
    }

    public ImageView getImageView(int number) {
        switch (number) {
            case 0:
                return findViewById(R.id.PlayerCard1);
            case 1:
                return findViewById(R.id.PlayerCard2);
            case 2:
                return findViewById(R.id.PlayerCard3);
            case 3:
                return findViewById(R.id.PlayerCard4);
            case 4:
                return findViewById(R.id.PlayerCard5);
            case 5:
                return findViewById(R.id.PlayerCard6);
            case 6:
                return findViewById(R.id.PlayerCard7);
            case 7:
                return findViewById(R.id.PlayerCard8);
            case 8:
                return findViewById(R.id.PlayerCard9);
            case 9:
                return findViewById(R.id.PlayerCard10);
            case 10:
                return findViewById(R.id.PlayerCard11);
            default:
                return null;
        }
    }
}
