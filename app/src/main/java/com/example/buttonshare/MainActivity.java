package com.example.buttonshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<hienDacSanNgauNhien> specialties = null;

        // Đọc JSON từ file assets
        try {
            InputStream is = getAssets().open("dacsan.json");
            InputStreamReader reader = new InputStreamReader(is);

            specialties = new Gson().fromJson(reader, new TypeToken<List<hienDacSanNgauNhien>>(){}.getType());

            // Chọn đặc sản ngẫu nhiên
            Random random = new Random();
            int randomIndex = random.nextInt(specialties.size());
            hienDacSanNgauNhien randomSpecialty = specialties.get(randomIndex);

            // Hiển thị thông tin đặc sản ngẫu nhiên
            TextView nameTextView = findViewById(R.id.specialty_name);
            TextView descriptionTextView = findViewById(R.id.specialty_description);
            ImageView imageView = findViewById(R.id.specialty_image);

            nameTextView.setText(randomSpecialty.getName());
            descriptionTextView.setText(randomSpecialty.getDescription());
            Glide.with(this).load(randomSpecialty.getImageUrl()).into(imageView);

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Check out this cool Application");
        intent.putExtra(Intent.EXTRA_TEXT, "Your Application Link Here"); // link chia sẻ nằm ở đây
        startActivity(Intent.createChooser(intent, "Share Via"));
        return super.onOptionsItemSelected(item);
    }
}
