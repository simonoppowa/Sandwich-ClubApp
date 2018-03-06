package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView placeOfOriginTV;
    private TextView placeOfOriginLabelTV;
    private TextView descriptionTV;
    private TextView ingredientsTV;
    private TextView alsoKnownAsTV;
    private TextView alsoKnownAsLabelTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        placeOfOriginTV = findViewById(R.id.origin_tv);
        placeOfOriginLabelTV = findViewById(R.id.placeOfOriginLabel);
        descriptionTV = findViewById(R.id.description_tv);
        ingredientsTV = findViewById(R.id.ingredients_tv);
        alsoKnownAsTV = findViewById(R.id.also_known_tv);
        alsoKnownAsLabelTV = findViewById(R.id.alsoKnowAsLabel);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        //origin TextView
        if(sandwich.getPlaceOfOrigin().isEmpty()) {
            hideTextView(placeOfOriginTV);
            hideTextView(placeOfOriginLabelTV);
        }
        else {
            placeOfOriginTV.setText(sandwich.getPlaceOfOrigin());
        }

        //alsoKnownAs TextView
        if(sandwich.getAlsoKnownAs().isEmpty()) {
            hideTextView(alsoKnownAsTV);
            hideTextView(alsoKnownAsLabelTV);
        }
        else {
            List<String> alsoKnownAs = sandwich.getAlsoKnownAs();

            for(int i = 0; i < alsoKnownAs.size(); i++) {
                alsoKnownAsTV.append(alsoKnownAs.get(i) + "\n");
            }
        }

        //ingredients TextView
        List<String> ingredients = sandwich.getIngredients();

        for(int i = 0; i < ingredients.size(); i++) {
            ingredientsTV.append(ingredients.get(i) + "\n");
        }

        //description TextView
        descriptionTV.setText(sandwich.getDescription());
    }

    private void hideTextView(TextView textView) {
        textView.setVisibility(View.INVISIBLE);
    }
}
