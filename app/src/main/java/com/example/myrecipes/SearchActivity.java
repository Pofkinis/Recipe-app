package com.example.myrecipes;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity  {
    private Cursor cursor;
    private List<Food> foodList = new ArrayList<Food>();
    private RecipesAdapter adapter;
    private List<String> captions = new ArrayList<>();
    private List<Integer> ids = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        SQLiteOpenHelper recipeDatabaseHelper = new RecipesDatabaseHelper(this);
        try{
            SQLiteDatabase db = recipeDatabaseHelper.getReadableDatabase();
            String sql = "SELECT RECIPE._id, RECIPE.NAME , RECIPE.IMAGE_RESOURCE_ID FROM `RECIPE`";
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()){
                Food food = new Food();
                food.setId(cursor.getInt(0));
                food.setName(cursor.getString(1));
                food.setImageResourceId(cursor.getInt(2));
                captions.add(cursor.getString(1));
                ids.add(cursor.getInt(2));
                foodList.add(food);
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e){
            Toast.makeText(this, "Database is unavailable", Toast.LENGTH_SHORT).show();
        }

        setUpRecycleView();

    }



    private void setUpRecycleView() {
        RecyclerView recyclerView = findViewById(R.id.search_recycler);
        //recyclerView.setHasFixedSize(true);
        adapter = new RecipesAdapter(captions, ids);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setListener(new RecipesAdapter.Listener(){
            public void onClick(int position){
                Intent intent = new Intent(SearchActivity.this, FoodDetailActivity.class);
                intent.putExtra(FoodDetailActivity.EXTRA_FOOD_ID, Integer.toString(foodList.get(position).getId()));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.actionSearch1);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

                return false;
            }
        });

        return true;
    }
}
