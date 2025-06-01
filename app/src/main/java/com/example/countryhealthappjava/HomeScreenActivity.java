package com.example.countryhealthappjava;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_screen);
        TextView topTenText = findViewById(R.id.topTenText);
        EditText searchEditText = findViewById(R.id.searchEditText);
        Button searchButton = findViewById(R.id.countrySearchButton);
        Button filterButton = findViewById(R.id.filterButton);
        Button firstScreenButton = findViewById(R.id.firstScreenbutton);


        MentalHealthAnalyzer analyzer = new MentalHealthAnalyzer(this.getAssets());
        List<CountryMentalHealth> topTen = analyzer.showTop10Depression();

        String textToShow = "Top Ten Countries with Highest Percentage of Depression: \n \n";
        int num = 1;
        for (CountryMentalHealth country : topTen){
            textToShow += num + ". " + country.getName() + "\n";
            num++;
        }

        topTenText.setText(textToShow);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreenActivity.this, CountryDetailsActivity.class);
                String countryToSearch = searchEditText.getText().toString();
                intent.putExtra("country", countryToSearch);
                startActivity(intent);
            }
        });

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreenActivity.this, FilterActivity.class));
            }
        });

        firstScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreenActivity.this, MainActivity.class));
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}