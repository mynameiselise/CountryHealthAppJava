package com.example.countryhealthappjava;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        TextView infoToShow = (TextView) findViewById(R.id.countryInformation);
        Button toHomeScreen = (Button) findViewById(R.id.homeScreenButton);


        MentalHealthAnalyzer analyzer = new MentalHealthAnalyzer(this.getAssets());


        // For the latest year, which of the selected 5 mental disorders is most
        // prevalent
        CountryMentalHealth c1 = analyzer.showCountry("Mexico");
        String mostPrevalent = c1.latestData().mostPrevalent();

        // The average share of the population with depressive disorders across all
        // years
        Double avdDep = c1.avgDepression();

        infoToShow.setText("Most prevalent Disorder for Latest Year is: " + mostPrevalent + "\n" +
                "The average share of the population with depressive disorders across all years is : " + avdDep);


        toHomeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, HomeScreenActivity.class));
            }
        });

        // Year with the largest share of the population with depressive disorders
        int yearc1 = c1.peakDepressionYear();

        // The 10 Countries with the Highest Percentage of Depression: Display the 10
        // countries with the highest percentage of depression in their population, sorted in
        // descending order.

        List<CountryMentalHealth> topTen = analyzer.showTop10Depression();
        System.out.println(Arrays.toString(topTen.toArray()));

        // Country, Schizophrenia disorders (share of population), Depressive
        // disorders (share of population), Anxiety disorders (share of population), Bipolar
        // disorders (share of population), Eating disorders (share of population). Use the
        // latest year's statistics.
        Data latestc1 = c1.latestData();

        // The average percentage of individuals with depression across all years.
        Double avdDepression = c1.avgDepression();

        // The year with the highest percentage of the population with depression.
        int peakYear = c1.peakDepressionYear();


        // The country displayed should have the largest percentage of its
        // population with a depressive disorder
        CountryMentalHealth country = analyzer.filterYear(1997);
        Data countryData = country.getAllData().get(1997);
        System.out.println("BYE " + country.getName() + " " + countryData.getDepression()) ;


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}