package com.sarfaraj.wallpaper_app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sarfaraj.wallpaper_app.R;
import com.sarfaraj.wallpaper_app.Wallpaper_Activity;

import java.util.ArrayList;

public class Wallpaper_Rv_Adapter extends RecyclerView.Adapter<Wallpaper_Rv_Adapter.viewHolder> {

    ArrayList<String> wallPaper_ArrayList;
    Context context;

    public Wallpaper_Rv_Adapter(ArrayList wallPaper_ArrayList, Context context) {
        this.wallPaper_ArrayList = wallPaper_ArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_wallpaper_rv, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        Glide.with(context).load(wallPaper_ArrayList.get(position)).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Wallpaper_Activity.class);
                intent.putExtra("imgUrl",wallPaper_ArrayList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wallPaper_ArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.id_ImageView_wallpaper);
        }
    }
}
