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

public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_filter);
        EditText yearInput = findViewById(R.id.yearEditText);
        TextView filterInfo = findViewById(R.id.filterTextView);
        Button backToHomeFilter = findViewById(R.id.filterToHomeButton);
        Button yearFilterButton = findViewById(R.id.yearFilterButton);
        MentalHealthAnalyzer analyzer = new MentalHealthAnalyzer(this.getAssets()); //TODO 2

        yearFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  int year = Integer.parseInt(yearInput.getText().toString());
                CountryMentalHealth country = analyzer.filterYear(year);
                if(country == null){
                    filterInfo.setText("No Information Found for that Year");
                }
                else {
                    Data countryData = country.getAllData().get(year);
                    String toDisplay = "Country with Highest Percentage depression for " + year + "\n" + "Country: " + country.getName() +
                            " \n" +
                            "Depression Percentage: " + countryData.getDepression();

                    filterInfo.setText(toDisplay);
                }
            }
        });


        backToHomeFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FilterActivity.this, HomeScreenActivity.class));
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}