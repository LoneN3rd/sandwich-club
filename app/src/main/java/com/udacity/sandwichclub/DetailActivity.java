package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private Sandwich sandwich;

    private TextView tv_also_known, tv_origin, tv_description, tv_ingredients;

    String placeOfOrigin, description;
    StringBuilder ingredients, otherNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        tv_also_known = findViewById(R.id.also_known_tv);
        tv_origin = findViewById(R.id.origin_tv);
        tv_description= findViewById(R.id.description_tv);
        tv_ingredients= findViewById(R.id.ingredients_tv);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        //Sandwich Details
        placeOfOrigin = sandwich.getPlaceOfOrigin();
        description = sandwich.getDescription();
        otherNames = listModel(sandwich.getAlsoKnownAs());
        ingredients = listModel(sandwich.getIngredients());

        populateUI();

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        //Populate place of origin tv
        if (placeOfOrigin.isEmpty()){
            tv_origin.setText(R.string.no_data_message);
        }else {
            tv_origin.setText(placeOfOrigin);
        }

        //Populate also known as tv
        if (otherNames.length() == 0){
            tv_also_known.setText(R.string.no_data_message);
        }else {
            tv_also_known.setText(otherNames);
        }

        //Populate ingredients tv
        if(ingredients.length() == 0) {
            tv_ingredients.setText(R.string.no_data_message);
        }else {
            tv_ingredients.setText(ingredients);
        }

        //Populate description tv
        if (description.isEmpty()){
            tv_description.setText(R.string.no_data_message);
        }else {
            tv_description.setText(description);
        }
    }

    public StringBuilder listModel(List<String> list){
        StringBuilder stringBuilder= new StringBuilder();
        for (int i=0;i<list.size();i++){

            if (i==list.size()-1){
                stringBuilder.append(list.get(i));
            } else {
                stringBuilder.append(list.get(i)).append(", ");
            }

        }
        return stringBuilder;
    }

}
