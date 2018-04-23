package com.example.eduardogonzalez.blackjack;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import org.json.*;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lib.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    Deck deck;
    Player player1;
    Dealer dealer;
    Player current = new Player();
    final ImageView playerCard1 = findViewById(R.id.playerCard1);
    final ImageView playerCard2 = findViewById(R.id.playerCard2);
    private static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);
        shuffleAPICall();


        playerCard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
            }
        });

        playerCard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
            }
        });

        final Button standBtn = (Button) findViewById(R.id.standBtn);
        standBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        final Button draw = (Button) findViewById(R.id.drawBtn);
        draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("thes", "i was called");
                drawAPICall();
            }
        });
    }

    void drawAPICall() {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "https://deckofcardsapi.com/api/deck/"+ deck.getDeckID() + "/draw/?count=1",
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                        getCard(response);
                        drawImage();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("that", "htere is an eeroro");
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void shuffleAPICall() {
        Log.d("this", "shuffle");
        try {
            Log.d("this", "i was called");
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=1",
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("this", "i got an object");
                            getDeckId(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("that", "htere is an eeroro");
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            Log.d("that", "im here");
        }
    }

    void getDeckId(JSONObject jsonObject) {
        Log.d("this", "getDeckId");
        try {
            String deckId = jsonObject.getString("deck_id");
            deck = new Deck(deckId);

        } catch (JSONException ignored) {
        }
    }

    void getCard(JSONObject json) {
        String code = null;
        String imageUrl = null;
        try {
            JSONArray array = json.getJSONArray("cards");
            for (int i = 0; i < array.length(); i++ ) {
                if (code == null) {
                    code = array.getJSONObject(i).getString("code");
                }
                if (imageUrl == null) {
                    imageUrl = array.getJSONObject(i).getString("image");
                }
                if (imageUrl != null && code != null) {
                    int val = Card.values.get(code.substring(0,1));
                    Card card = new Card(imageUrl, val);
                    current.addCard(card);
                }
            }
        } catch (JSONException ignored) {
        }
    }
    void drawImage() {
        try {

            Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(current.getCard(current.getCardLength() - 1).getUrl()).getContent());
            playerCard1.setImageBitmap(bitmap);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
