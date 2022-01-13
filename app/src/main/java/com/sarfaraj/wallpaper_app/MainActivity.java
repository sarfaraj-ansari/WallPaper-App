package com.sarfaraj.wallpaper_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sarfaraj.wallpaper_app.Adapters.Category_RV_Adapter;
import com.sarfaraj.wallpaper_app.Adapters.Wallpaper_Rv_Adapter;
import com.sarfaraj.wallpaper_app.Models.Category_Model;
import com.sarfaraj.wallpaper_app.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Category_RV_Adapter.onCategoryClicked {

    String api_key="563492ad6f917000010000011cddda8de46740a2ae7085a9a61dbea5";
    ActivityMainBinding binding;
    ArrayList<String> wallpaper_arraylist;
    ArrayList<Category_Model> categoryModelArrayList;

    Category_RV_Adapter category_rv_adapter;
    Wallpaper_Rv_Adapter wallpaper_rv_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        wallpaper_arraylist=new ArrayList<>();
        categoryModelArrayList=new ArrayList<>();

        binding.idCategoryRV.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        category_rv_adapter=new Category_RV_Adapter(categoryModelArrayList, this,this);
        binding.idCategoryRV.setAdapter(category_rv_adapter);

        getCategories();

        binding.idWallpaperRV.setLayoutManager(new GridLayoutManager(this,2));
        wallpaper_rv_adapter=new Wallpaper_Rv_Adapter(wallpaper_arraylist, this);
        binding.idWallpaperRV.setAdapter(wallpaper_rv_adapter);

        getWallpapers();

        binding.idImageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searched_text =binding.idEdtSearchBar.getText().toString();
                if(searched_text.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please Enter the Search Query", Toast.LENGTH_LONG).show();
                }else{

                    getImagebySearch(searched_text);

                }
            }
        });
    }

    private void getCategories(){
        categoryModelArrayList.add(new Category_Model("Programming","data:image/jpeg;base64,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"));
        categoryModelArrayList.add(new Category_Model("Technology",""));
        categoryModelArrayList.add(new Category_Model("Nature",""));
        categoryModelArrayList.add(new Category_Model("Birds",""));
        categoryModelArrayList.add(new Category_Model("Flowers",""));
        categoryModelArrayList.add(new Category_Model("Cars",""));
        categoryModelArrayList.add(new Category_Model("Animals",""));
        categoryModelArrayList.add(new Category_Model("Technology",""));
        categoryModelArrayList.add(new Category_Model("Technology",""));
        categoryModelArrayList.add(new Category_Model("Technology",""));
        categoryModelArrayList.add(new Category_Model("Technology",""));
        categoryModelArrayList.add(new Category_Model("Technology",""));

    }

    private void getWallpapers(){
        wallpaper_arraylist.clear();
        binding.idProgressbar.setVisibility(View.VISIBLE);
       String url="https://api.pexels.com/v1/curated?per_page=80&page=1";

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                binding.idProgressbar.setVisibility(View.GONE);
                try {
                    JSONArray jsonArray=response.getJSONArray("photos");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);

                        String imgUrl=jsonObject.getJSONObject("src").getString("portrait");
                        wallpaper_arraylist.add(imgUrl);
                    }
                    wallpaper_rv_adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    binding.idProgressbar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                binding.idProgressbar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Facing Error", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers=new HashMap<>();
                headers.put("Authorization","563492ad6f917000010000011cddda8de46740a2ae7085a9a61dbea5");
                return headers;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(jsonObjectRequest);
    }

    private void getImagebySearch(String searched_text) {
        wallpaper_arraylist.clear();
        binding.idProgressbar.setVisibility(View.VISIBLE);
        String url ="https://api.pexels.com/v1/search?query="+searched_text+"&page=1&per_page=80";
        //String url ="https://api.pexels.com/v1/search?query=technology&page=1&per_page=30";

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                binding.idProgressbar.setVisibility(View.GONE);
                try {
                    JSONArray jsonArray=response.getJSONArray("photos");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String imgUrl=jsonObject.getJSONObject("src").getString("portrait");
                        wallpaper_arraylist.add(imgUrl);
                    }

                    wallpaper_rv_adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    binding.idProgressbar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                binding.idProgressbar.setVisibility(View.GONE);

                Toast.makeText(MainActivity.this, "Facing Error", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers=new HashMap<>();
                headers.put("Authorization","563492ad6f917000010000011cddda8de46740a2ae7085a9a61dbea5");
                return headers;
            }
        };

        RequestQueue requestQueue=Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void categoryItemClicked(int position) {

    }
}