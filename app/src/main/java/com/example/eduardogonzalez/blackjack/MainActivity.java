package com.example.eduardogonzalez.blackjack;

import android.content.Intent;
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
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    Deck deck;
    Player player1;
    Dealer dealer;
    private Player current = new Player();
    private static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);
        shuffleAPICall();

        final ImageView playerCard1 = findViewById(R.id.playerCard1);
        playerCard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                i.putExtra("Cards", current.getUrls());
                startActivity(i);
            }
        });

        final ImageView playerCard2 = findViewById(R.id.playerCard2);
        playerCard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                i.putExtra("Cards", current.getUrls());
                startActivity(i);
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
                drawAPICall();
            }
        });

        final Button reset = (Button) findViewById(R.id.resetBtn);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MainActivity.class);
                startActivity(i);
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
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void shuffleAPICall() {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=1",
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            getDeckId(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
        }
    }

    void getDeckId(JSONObject jsonObject) {
        try {
            String deckId = jsonObject.getString("deck_id");
            Log.d("this", deckId + "i am here");
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
        ImageView cardPlayer1 = findViewById(R.id.playerCard1);
        ImageView cardPlayer2 = findViewById(R.id.playerCard2);
        loadFromUrl(current.getCard(current.getCardLength() - 1).getUrl(), cardPlayer1);
        if (current.getCardLength() >= 2) {
            loadFromUrl(current.getCard(current.getCardLength() - 2).getUrl(), cardPlayer2);
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
}
