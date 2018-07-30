package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.List;

import java.util.ArrayList;

public class JsonUtils {

    private static final String LOG_TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String json) {

        String mainName = null;
        String placeOfOrigin = null;
        String description = null;
        String image = null;
        List<String> ingredients = new ArrayList<>();
        List<String> alsoKnownAs = new ArrayList<>();

        try{
            JSONObject jsonObject = new JSONObject(json);
            JSONObject jsonObjectName = jsonObject.getJSONObject("name");

            mainName = jsonObjectName.optString("mainName");
            placeOfOrigin = jsonObject.optString("placeOfOrigin");
            description = jsonObject.optString("description");
            image = jsonObject.optString("image");

            alsoKnownAs = jsonArrayList(jsonObjectName.getJSONArray("alsoKnownAs"));
            ingredients = jsonArrayList(jsonObject.getJSONArray("ingredients"));
        }
        catch (JSONException e){
            Log.e(LOG_TAG, "Problems with parse", e);
        }

        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

    }

    public static List<String> jsonArrayList(JSONArray jsonArray) throws JSONException {
        List<String> jsonList = new ArrayList<>(0);

        if(jsonArray != null) {
            for(int i=0; i < jsonArray.length(); i++) {
                jsonList.add(jsonArray.getString(i));
            }
        }

        return jsonList;
    }
}
