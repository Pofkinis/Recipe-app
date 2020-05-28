package com.example.myrecipes;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void onClickAddRecipe (View view){
        SQLiteOpenHelper recipeDatabaseHelper = new RecipesDatabaseHelper(this);
        SQLiteDatabase db = recipeDatabaseHelper.getWritableDatabase();
        try {
            EditText recipeNameE = (EditText) findViewById(R.id.recipe_name);
            EditText recipeIngredientsE = (EditText) findViewById(R.id.recipe_ingredients);
            EditText recipeInstructionE = (EditText) findViewById(R.id.recipe_instruction);
            CheckBox breakfastBox = (CheckBox) findViewById(R.id.checkbox_breakfast);
            CheckBox lunchBox = (CheckBox) findViewById(R.id.checkbox_lunch);
            CheckBox dinnerBox = (CheckBox) findViewById(R.id.checkbox_dinner);
            String recipeName = recipeNameE.getText().toString();
            String recipeIngredients = recipeIngredientsE.getText().toString();
            String recipeInstruction = recipeInstructionE.getText().toString();

            if(recipeName.equals("") || recipeIngredients.equals("") || recipeInstruction.equals("")){
                throw new NullPointerException("Please fill all entries");
            }

            long count = DatabaseUtils.queryNumEntries(db, "RECIPE");


            ContentValues recipeValues = new ContentValues();
            recipeValues.put("NAME", recipeName);
            recipeValues.put("INGREDIENTS", recipeIngredients);
            recipeValues.put("INSTRUCTION", recipeInstruction);
            Random rand = new Random();
            int randNum = rand.nextInt(5);
            switch(randNum) {
                case 0:
                    recipeValues.put("IMAGE_RESOURCE_ID", R.drawable.foodpic0);
                    break;
                case 1:
                    recipeValues.put("IMAGE_RESOURCE_ID", R.drawable.foodpic1);
                    break;
                case 2:
                    recipeValues.put("IMAGE_RESOURCE_ID", R.drawable.foodpic2);
                    break;
                case 3:
                    recipeValues.put("IMAGE_RESOURCE_ID", R.drawable.foodpic3);
                    break;
                case 4:
                    recipeValues.put("IMAGE_RESOURCE_ID", R.drawable.foodpic4);
                    break;
            }

            int counter = 0;
            if (breakfastBox.isChecked()) {
                ContentValues values = new ContentValues();
                values.put("RECIPE_ID", (int) count + 1);
                values.put("CATEGORY_ID", 1);
                db.insert("RECIPE_CATEGORY", null, values);
                counter++;
            }
            if (lunchBox.isChecked()) {
                ContentValues values = new ContentValues();
                values.put("RECIPE_ID", (int) count + 1);
                values.put("CATEGORY_ID", 2);
                counter++;
                db.insert("RECIPE_CATEGORY", null, values);
            }
            if (dinnerBox.isChecked()) {
                ContentValues values = new ContentValues();
                values.put("RECIPE_ID", (int) count + 1);
                values.put("CATEGORY_ID", 3);
                counter++;
                db.insert("RECIPE_CATEGORY", null, values);
            }
            if(counter == 0){
                throw new SecurityException("Please select at least 1 category");
            }
            db.insert("RECIPE", null, recipeValues);
            db.close();
            Intent intent = new Intent(AddActivity.this, card_views_activity.class);
            startActivity(intent);
        }
        catch (NullPointerException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        catch (SecurityException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}
