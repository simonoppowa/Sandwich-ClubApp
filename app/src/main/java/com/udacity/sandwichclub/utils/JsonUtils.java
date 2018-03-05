package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        //creating sandwichJSON Object
        JSONObject sandwichJSON = new JSONObject((json));

        //getting nameJSON Object
        JSONObject nameJSON = sandwichJSON.getJSONObject("name");

        //getting mainName
        String mainName = nameJSON.getString("mainName");

        //getting alsoKnownAs
        List<String> alsoKnownAs = new ArrayList<>();

        JSONArray alsoKnownAsJSON = nameJSON.getJSONArray("alsoKnownAs");

        for(int i = 0; !alsoKnownAsJSON.isNull(i); i++) {
            alsoKnownAs.add(alsoKnownAsJSON.get(i).toString());
        }

        //getting placeOfOrigin
        String placeOfOrigin = sandwichJSON.getString("placeOfOrigin");

        //getting description
        String description = sandwichJSON.getString("description");

        //getting image
        String image = sandwichJSON.getString("image");

        //getting ingredients
        List<String> ingredients = new ArrayList<>();

        JSONArray ingredientsJSON = sandwichJSON.getJSONArray("ingredients");

        for(int i = 0; !ingredientsJSON.isNull(i); i++) {
            ingredients.add(ingredientsJSON.get(i).toString());
        }

        //creating Sandwich Object
        Sandwich newSandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        return newSandwich;
    }
}
