package com.example.countryhealthappjava;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CountryDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_country_details);
        TextView details = findViewById(R.id.countryDetailsText);
        Button backToHome = findViewById(R.id.backToHomeButton);


        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("country");

        MentalHealthAnalyzer analyzer = new MentalHealthAnalyzer(this.getAssets());

        // For the latest year, which of the selected 5 mental disorders is most
        // prevalent
        CountryMentalHealth c1 = analyzer.showCountry(message);

        if (c1 == null) {
            details.setText("Country Not Found");
        }
        else {
            // Country, Schizophrenia disorders (share of population), Depressive
            // disorders (share of population), Anxiety disorders (share of population), Bipolar
            // disorders (share of population), Eating disorders (share of population). Use the
            // latest year's statistics.
            Data latestc1 = c1.latestData();

            // The average percentage of individuals with depression across all years.
            Double avdDepression = c1.avgDepression();

            // The year with the highest percentage of the population with depression.
            int peakYear = c1.peakDepressionYear();

            String toDisplay = "\n===== " + c1.getName() + " =====\n" +
                    "Latest year: " + c1.latestYear() + "\n" +
                    "• Schizophrenia: " + latestc1.getSchizophrenia() + "%\n" +
                    "• Depression: " + latestc1.getDepression() + "%\n" +
                    "• Anxiety: " + latestc1.getAnxiety() + "%\n" +
                    "• Bipolar: " + latestc1.getBipolar() + "%\n" +
                    "• Eating D/O: " + latestc1.getEating() + "%\n" +
                    "Most prevalent (latest): " + latestc1.mostPrevalent() + "\n\n" +
                    "Average depression (all years): " + c1.avgDepression() + "%\n" +
                    "Highest depression year: " + c1.peakDepressionYear() + "\n";

            details.setText(toDisplay);
        }

        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CountryDetailsActivity.this, HomeScreenActivity.class));
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}