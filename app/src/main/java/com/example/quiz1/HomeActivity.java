package com.example.quiz1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.quiz1.adapter.FurnitureAdapter;
import com.example.quiz1.data.FurnitureData;
import com.example.quiz1.data.UserData;
import com.example.quiz1.helper.FurnitureHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {

    FurnitureData furnitureData;
    UserData userData;
    RecyclerView rvFurniture;
    FurnitureAdapter furnitureAdapter;
    Intent intent;
    FurnitureHelper furnitureHelper;
    String emailUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setTitle("Hi " + userData.getLoggedIn().getUsername() + "!");
        Log.e("Message", ""+ userData.getLoggedIn().getUsername() +"");

        furnitureHelper = new FurnitureHelper(this);
        furnitureHelper.open();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final String url = "https://mocki.io/v1/5f379081-2473-4494-9cc3-9e808772dc54";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray furnitures = response.getJSONArray("furnitures");
                    for(int i=0;i<furnitures.length(); i++){
                        JSONObject c = furnitures.getJSONObject(i);
                        String product_name = c.getString("product_name");
                        String rating = c.getString("rating");
                        String price = c.getString("price");
                        String desc = c.getString("description");
                        String image = c.getString("image");
                        int priceInt = Integer.parseInt(price);
                        double ratingInt = Double.parseDouble((rating));

                        furnitureHelper.saveProducts(i,product_name, ratingInt, priceInt, image, desc);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                furnitureAdapter.notifyDataSetChanged();
            }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Info", error.toString());
                }
        });

        requestQueue.add(jsonObjectRequest);

        rvFurniture = findViewById(R.id.rvFurniture);
        furnitureAdapter = new FurnitureAdapter(this, furnitureData.getVectFurniture());

        rvFurniture.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvFurniture.setAdapter(furnitureAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home :
                Log.wtf("test", "Masuk Home");
                break;
            case R.id.profile :
                intent = new Intent(this, ProfileActivity.class);
                intent.putExtra("username", userData.getLoggedIn().getUsername());
                intent.putExtra("email", userData.getLoggedIn().getEmailAddress());
                intent.putExtra("phone", userData.getLoggedIn().getPhoneNum());
                startActivity(intent);
                Log.wtf("test", "Masuk Profile");
                break;
            case R.id.history :
                intent = new Intent(this, HistoryActivity.class);
                int userId = userData.getLoggedIn().getId();
                intent.putExtra("userId", userData.getLoggedIn().getId());
                startActivity(intent);
                Log.wtf("test", "Masuk History");
                break;
            case R.id.about :
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}