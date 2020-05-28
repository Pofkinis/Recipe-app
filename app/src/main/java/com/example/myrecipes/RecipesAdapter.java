package com.example.myrecipes;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.GpsStatus;
import android.net.sip.SipSession;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> implements Filterable {
    private Listener listener;
    private List<Food> foodListas = new ArrayList<>();
    private List<Food> foodListFull;

    interface Listener{
        void onClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    public RecipesAdapter(String[] captions, int[] imageIds){
        for (int i = 0; i < captions.length; i++){
            Food food = new Food(captions[i], imageIds[i]);
            this.foodListas.add(food);
        }
        foodListFull = new ArrayList<>(foodListas);
    }

    public RecipesAdapter(List<String> captions, List<Integer> imageIds){
        for (int i = 0; i < captions.size(); i++){
            Food food = new Food(captions.get(i), imageIds.get(i));
            this.foodListas.add(food);
        }
        foodListFull = new ArrayList<>(foodListas);
    }

    @Override
    public int getItemCount() {
        return foodListas.size();
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

     @Override
     public RecipesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).
                inflate(R.layout.fragment_food, parent, false);
        return new ViewHolder(cv);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CardView cardView = holder.cardView;
        ImageView imageView = (ImageView)cardView.findViewById(R.id.info_image);
        Drawable drawable =
                ContextCompat.getDrawable(cardView.getContext(), foodListas.get(position).getImageResourceId());
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(foodListas.get(position).getName());
        TextView textView = (TextView)cardView.findViewById(R.id.info_text);
        textView.setText(foodListas.get(position).getName());
        cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(listener !=null) {
                    listener.onClick(position);
                }
            }
        });
    }



    @Override
    public Filter getFilter(){
        return foodFilter;
    }
    private Filter foodFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Food> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(foodListFull);
            }
            else{
                String filteredPattern = constraint.toString().toLowerCase().trim();
                for (Food item : foodListFull){
                    if(item.getName().toLowerCase().contains(filteredPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            foodListas.clear();
            foodListas.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
