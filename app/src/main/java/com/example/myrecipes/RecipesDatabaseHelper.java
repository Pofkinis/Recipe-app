package com.example.myrecipes;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import androidx.core.content.ContextCompat;

import java.util.Locale;

public class RecipesDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "recipes";
    private static final int DB_VERSION = 1;
    RecipesDatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE RECIPE ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "NAME TEXT, "
                + "INGREDIENTS TEXT, "
                + "INSTRUCTION TEXT, "
                + "IMAGE_RESOURCE_ID INTEGER);");

        db.execSQL("CREATE TABLE CATEGORY ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "NAME TEXT);");

        db.execSQL("CREATE TABLE RECIPE_CATEGORY("
                + "RECIPE_ID INTEGER, "
                + "CATEGORY_ID INTEGER," +
                "FOREIGN KEY (RECIPE_ID) REFERENCES RECIPE (_id)," +
                "FOREIGN KEY (CATEGORY_ID) REFERENCES CATEGORY (_id));");
        Food[] foods = getFood();
        insertRecipe(db, foods[0].getName(), foods[0].getProducts(), foods[0].getRecipe(), foods[0].getImageResourceId());
        insertRecipe(db, foods[1].getName(), foods[1].getProducts(), foods[1].getRecipe(), foods[1].getImageResourceId());
        insertRecipe(db, foods[2].getName(), foods[2].getProducts(), foods[2].getRecipe(), foods[2].getImageResourceId());
        insertCategory(db, "Breakfast");
        insertCategory(db, "Lunch");
        insertCategory(db, "Dinner");
        insertRecicipeCategory(db, 1, 2);
        insertRecicipeCategory(db, 1, 3);
        insertRecicipeCategory(db, 2, 1);
        insertRecicipeCategory(db, 2, 2);
        insertRecicipeCategory(db, 3, 1);
        insertRecicipeCategory(db, 3, 2);
        insertRecicipeCategory(db, 3, 3);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
    public static void insertRecipe(SQLiteDatabase db, String name, String ingredients,
                                String instruction, int resourceId){
        ContentValues recipeValues = new ContentValues();
        recipeValues.put("NAME", name);
        recipeValues.put("INGREDIENTS", ingredients);
        recipeValues.put("INSTRUCTION", instruction);
        recipeValues.put("IMAGE_RESOURCE_ID", resourceId);
        db.insert("RECIPE", null, recipeValues);
    }

    public static void insertCategory (SQLiteDatabase db, String name){
        ContentValues categoryValues = new ContentValues();
        categoryValues.put("NAME", name);
        db.insert("CATEGORY", null, categoryValues);
    }

    public static void insertRecicipeCategory(SQLiteDatabase db, int recipeId, int categoryId){
        ContentValues values = new ContentValues();
        values.put("RECIPE_ID",  recipeId);
        values.put("CATEGORY_ID", categoryId);
        db.insert("RECIPE_CATEGORY", null, values);
    }

    public Food[] getFood (){
        Food[] foods = new Food[]{
                new Food("Organic Pork and Mushroom Casserole",
                        "20g dried porcini mushrooms\n" +
                                "2 tbsp olive oil\n" +
                                "450g organic pork fillet, cut into large chunks\n" +
                                "1 organic onion, chopped\n" +
                                "2 organic cloves of garlic, crushed\n" +
                                "200g organic chestnut mushrooms, quartered\n" +
                                "1 tbsp fresh sage, chopped\n" +
                                "150ml Marsala wine\n" +
                                "250g organic double cream or crème fraiche\n" +
                                "Freshly ground pepper\n" +
                                "1 tbsp fresh sage, chopped, for serving",
                        "1. Preheat the oven to 180˚C, gas mark 4.\n" +
                                "\n" +
                                "2. Pour 150ml boiling water over the dried mushrooms and leave them to soak for 15 minutes. Strain through a fine sieve and reserve the soaking liquid.\n" +
                                "\n" +
                                "3. Heat the oil in a large casserole dish and fry the pork fillet for 5-6 minutes until lightly browned. Add the onion, garlic, chestnut mushrooms and dried mushrooms. Cook for 1-2 minutes until the onion starts to soften. Add the sage, wine and cream and bring to the boil. Cover the casserole with a lid and place in the oven to cook.\n" +
                                "\n" +
                                "4. Cook for 40-45 minutes or until the pork is completely cooked through with no pink juices. Half way through cooking add the reserved mushroom liquid if it starts to become too dry.\n" +
                                "\n" +
                                "5. Season with black pepper and sprinkle over a little additional sage to serve.", R.drawable.foodpic1),
                new Food("French Dip Squares",
                        "INGREDIENTS\n" +
                                "Cooking spray\n" +
                                "2 (8-oz.) tubes refrigerated crescent rolls\n" +
                                "4 tbsp. butter, divided\n" +
                                "2 medium onions, thinly sliced\n" +
                                "4 sprigs fresh thyme, divided\n" +
                                "Kosher salt\n" +
                                "Freshly ground black pepper\n" +
                                "1/4 c. whole-grain mustard\n" +
                                "1/2 lb. deli roast beef, patted dry\n" +
                                "9 slices provolone\n" +
                                "1 tbsp. freshly chopped parsley\n" +
                                "1 clove garlic, minced\n" +
                                "1 c. low-sodium beef broth\n" +
                                "1 tbsp. Worcestershire sauce",
                        "Preheat oven to 350° and grease a 9\"-x-13\" baking sheet with cooking spray. Place one unrolled can of crescents on prepared baking sheet and pinch together seams. \n" +
                                "Bake until edges are slightly golden, about 12 minutes. Remove from oven and set aside.\n" +
                                "Meanwhile, caramelize onions: In a large skillet over medium heat, melt 2 tablespoons butter. Add onion and 2 sprigs thyme and season with salt and pepper. Cook, stirring occasionally, until onions begin to soften and turn slightly golden. Continue cooking, stirring occasionally, until onions are soft and caramelized, about 20 minutes. Remove from heat.\n" +
                                "Build squares: Spread an even layer of mustard onto prepared crescent, leaving a 1/2\" border clear on all edges. Top with a layer of roast beef and a layer of provolone. Top with caramelized onions.\n" +
                                "Unroll remaining tube of crescent rolls and place on top of onion layer. Press seam into bottom crust to seal all edges. Melt 1 tablespoon butter and brush all over top of crescent dough, then sprinkle with parsley and salt. \n" +
                                "Bake until dough is golden and cooked through, about 35 minutes. (If dough is browning too quickly, cover with foil.) \n" +
                                "Let cool 15 minutes before slicing into squares.\n" +
                                "Meanwhile, make au jus: Melt remaining 1 tablespoon butter in a medium skillet over medium heat. Stir in garlic and cook until fragrant. Add beef stock, Worcestershire sauce, and remaining 2 sprigs thyme, stripped from stem. Season with salt and pepper. Simmer for 10 minutes until reduced slightly. \n" +
                                "Serve squares with au jus on the side for dipping. ",
                        R.drawable.foodpic2),
                new Food("Cream Sandwich Cookies",
                        "1/2 c. (1 stick) butter, softened \n" +
                                "1/2 c. packed brown sugar\n" +
                                "1/4 c. granulated sugar\n" +
                                "1 large egg\n" +
                                "1 tsp. pure vanilla extract\n" +
                                "1 1/4 c. all-purpose flour\n" +
                                "1/2 tsp. baking soda\n" +
                                "1/2 tsp. kosher salt\n" +
                                "1 (4-oz.) Hershey's Cookies 'n' Creme bar, chopped (about 3/4 c.)\n" +
                                "1/2 c. chocolate chips\n" +
                                "24 Oreos",
                        "Preheat oven to 350°. Line two baking sheets with parchment paper. In a large bowl using a hand mixer, beat butter and sugars together. Add egg and vanilla and beat until incorporated. Add flour, baking soda, and salt and beat until just combined. Stir in Cookies ‘n’ Creme pieces and chocolate chips. \n" +
                                "Separate Oreos and place half on prepared baking sheets. Using a small cookie scoop, scoop cookie dough onto Oreo halves, then top with remaining halves, pressing top Oreo gently into dough. \n" +
                                "Bake until cookies are golden around the edges, 12 to 14 minutes. ",
                        R.drawable.foodpic3)
                };
        return foods;
    }
}
