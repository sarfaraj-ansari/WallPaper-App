package com.sarfaraj.wallpaper_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.sarfaraj.wallpaper_app.databinding.ActivitySetWallPaperBinding;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Set_WallPaper_Activity extends AppCompatActivity {

    ActivitySetWallPaperBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySetWallPaperBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String url=getIntent().getStringExtra("imgUrl");
        Glide.with(this).load(url).into(binding.imageView2);
        binding.idSetWallpaperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WallpaperManager wallpaperManager=WallpaperManager.getInstance(getApplicationContext());

                Glide.with(Set_WallPaper_Activity.this).asBitmap().load(url).addListener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        Toast.makeText(Set_WallPaper_Activity.this, "Loading Failed", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap bitmap, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        try {
                            wallpaperManager.setBitmap(bitmap);
                        } catch (IOException e) {
                            Toast.makeText(Set_WallPaper_Activity.this, "Failed to set wallpaper", Toast.LENGTH_LONG).show();
                        }
                        return false;
                    }
                }).submit();

                Toast.makeText(Set_WallPaper_Activity.this, "wallpaper set to home screen", Toast.LENGTH_LONG).show();

            }
        });
    }
}