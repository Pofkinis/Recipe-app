package com.example.myrecipes;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FoodDetailActivity extends AppCompatActivity {
    public static final String EXTRA_FOOD_ID = "id";

    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String recipeId = intent.getStringExtra(EXTRA_FOOD_ID);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        SQLiteOpenHelper recipeDatabaseHelper = new RecipesDatabaseHelper(this);
        Food food = new Food();
        try{
            SQLiteDatabase db = recipeDatabaseHelper.getReadableDatabase();
            String sql = "SELECT RECIPE._id, RECIPE.NAME, RECIPE.INGREDIENTS, RECIPE.INSTRUCTION, RECIPE.IMAGE_RESOURCE_ID FROM `RECIPE`";
            cursor = db.rawQuery(sql, null);
            int count = 0;
            while (cursor.moveToNext()){
                if(count == Integer.parseInt(recipeId)){
                    food.setId(cursor.getInt(0));
                    food.setName(cursor.getString(1));
                    food.setProducts(cursor.getString(2));
                    food.setRecipe(cursor.getString(3));
                    food.setImageResourceId(cursor.getInt(4));
                    break;
                }
                count++;
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e){
            Toast.makeText(this, "Database is unavailable", Toast.LENGTH_SHORT).show();
        }

        TextView foodName = (TextView) findViewById(R.id.food_name);
        foodName.setText(food.getName());
        TextView foodCategories = (TextView) findViewById(R.id.food_category);
        foodCategories.setText(findCategories(food.getId()));
        TextView foodIngredients = (TextView) findViewById(R.id.recipe_ingredients);
        foodIngredients.setText(food.getProducts());
        TextView foodInstruction = (TextView) findViewById(R.id.recipe_instruction);
        foodInstruction.setText(food.getRecipe());
}
    private String findCategories (int recipeID){
        String categories = "";
        Cursor cursor1;
        SQLiteOpenHelper recipeDatabaseHelper = new RecipesDatabaseHelper(this);
        try{
            SQLiteDatabase db = recipeDatabaseHelper.getReadableDatabase();
            String sql = "SELECT RECIPE_CATEGORY.RECIPE_ID, RECIPE_CATEGORY.CATEGORY_ID FROM `RECIPE_CATEGORY`";
            cursor1 = db.rawQuery(sql, null);
            while (cursor1.moveToNext()){
                if(cursor1.getInt(0) == recipeID){
                    int id = cursor1.getInt(1);
                    if(id == 1){
                        categories = categories + "Breakfast ";
                    }
                    if(id == 2){
                        categories = categories + "Lunch ";
                    }
                    if(id == 3){
                        categories = categories + "Dinner ";
                    }
                }
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e){
            Toast.makeText(this, "Database is unavailable", Toast.LENGTH_SHORT).show();
        }
        return categories;
    }
}
