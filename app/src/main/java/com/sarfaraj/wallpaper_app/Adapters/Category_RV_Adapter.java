package com.sarfaraj.wallpaper_app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sarfaraj.wallpaper_app.Models.Category_Model;
import com.sarfaraj.wallpaper_app.R;

import java.util.ArrayList;

public class Category_RV_Adapter extends RecyclerView.Adapter<Category_RV_Adapter.viewHolder> {

    ArrayList<Category_Model> category_arraylist;
    Context context;

    public Category_RV_Adapter(ArrayList<Category_Model> category_arraylist, Context context) {
        this.category_arraylist = category_arraylist;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_rv,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        Category_Model category_model=category_arraylist.get(position);

        holder.textView.setText(category_model.getCategory_txt().toString());
        Glide.with(context).load(category_model.getCategory_img_url()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return category_arraylist.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView textView;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.id_imageView_category);
            textView=itemView.findViewById(R.id.id_TV_category);
        }
    }
}
