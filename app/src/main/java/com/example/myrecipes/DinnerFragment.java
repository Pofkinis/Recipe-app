package com.example.myrecipes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DinnerFragment extends Fragment {

    private Cursor cursor;
    private List<Food> foodList = new ArrayList<Food>();

    public DinnerFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        SQLiteOpenHelper recipeDatabaseHelper = new RecipesDatabaseHelper(getActivity());
        try{
            SQLiteDatabase db = recipeDatabaseHelper.getReadableDatabase();
            String sql = "SELECT RECIPE._id, RECIPE.NAME, RECIPE.IMAGE_RESOURCE_ID FROM `RECIPE` INNER JOIN RECIPE_CATEGORY ON RECIPE._id = " +
                    "RECIPE_CATEGORY.RECIPE_ID INNER JOIN CATEGORY ON CATEGORY._id = RECIPE_CATEGORY.CATEGORY_ID AND CATEGORY.NAME = 'Dinner'";
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()){
                Food food = new Food();
                food.setId(cursor.getInt(0));
                food.setName(cursor.getString(1));
                food.setImageResourceId(cursor.getInt(2));
                foodList.add(food);
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e){
            Toast.makeText(getActivity(), "Database is unavailable", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView foodRecycler = (RecyclerView)inflater.inflate(
                R.layout.recycler_view, container, false);
        String[] foodNames = new String[foodList.size()];
        int[] foodImages = new int[foodList.size()];
        int counter = 0;
        for (Food food : foodList) {
            foodImages[counter] = food.getImageResourceId();
            foodNames[counter] = food.getName();
            counter++;
        }
        RecipesAdapter adapter = new RecipesAdapter(foodNames, foodImages);
        foodRecycler.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        foodRecycler.setLayoutManager(layoutManager);
        adapter.setListener(new RecipesAdapter.Listener(){
            public void onClick(int position){
                Intent intent = new Intent(getActivity(), FoodDetailActivity.class);
                intent.putExtra(FoodDetailActivity.EXTRA_FOOD_ID, Integer.toString(position));
                getActivity().startActivity(intent);
            }
        });
        return foodRecycler;
    }
}
