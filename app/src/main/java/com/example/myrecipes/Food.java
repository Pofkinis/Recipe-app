package com.example.myrecipes;

public class Food {
    private int id;
    private String name;
    private String products;
    private String recipe;
    private int imageResourceId;

    public Food(){
        id = -1;
        name = "";
        products = "";
        recipe = "";
        imageResourceId = -1;
    }

    public Food(String name, String products, String recipe, int imageResourceId) {
        this.name = name;
        this.products = products;
        this.recipe = recipe;
        this.imageResourceId = imageResourceId;
    }

    public Food (String name, int imageIds){
        this.name = name;
        imageResourceId = imageIds;
    }

 // setters
    public void setName(String name){
        this.name = name;
    }
    public void setId(int id){
        this.id = id;
    }
    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }
    public void setImageResourceId(int imageResourceId){
        this.imageResourceId = imageResourceId;
    }
    public void setProducts(String products){
        this.products = products;
    }

    public int getId(){
        return id;
    }
    public String getName() {
        return name;
    }
    public String getProducts() {
        return products;
    }
    public String getRecipe() {
        return recipe;
    }
    public int getImageResourceId(){
        return imageResourceId;
    }

}