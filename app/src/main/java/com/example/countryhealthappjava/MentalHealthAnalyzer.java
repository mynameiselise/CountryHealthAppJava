package com.example.countryhealthappjava;
import android.content.res.AssetManager;

import com.opencsv.CSVReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class MentalHealthAnalyzer {

    private static final Log log = LogFactory.getLog(MentalHealthAnalyzer.class);
    private  final String CSV = "mental_illnesses_prevalence.csv";
    private  final Map<String, CountryMentalHealth> countries = new HashMap<>();
    AssetManager assets;

    public MentalHealthAnalyzer(AssetManager assets){
       this.assets = assets;
        try {
            loadCsv(CSV);
        } catch (Exception ex) {
            System.err.println("Could not load CSV: " + ex.getMessage());

            //return;
        }
    }


    private void loadCsv(String path) throws Exception {

        try{
            InputStream inputStream = assets.open("mental_illnesses_prevalence.csv"); //TODO 6
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            CSVReader csvReader = new CSVReader(inputStreamReader);
            String[] t;
            csvReader.readNext();
            while((t = csvReader.readNext())!=null){
                String entity = t[0];
                String code   = t[1];
//                System.out.println("This is data " + Arrays.toString(t));
                int    year   = Integer.parseInt(t[2]);

                Data d = new Data(Double.parseDouble(t[3]),  // schizophrenia
                        Double.parseDouble(t[4]),  // depression
                        Double.parseDouble(t[5]),  // anxiety
                        Double.parseDouble(t[6]),  // bipolar
                        Double.parseDouble(t[7])); // eating disorder

                CountryMentalHealth cmh = countries.get(entity);
                if (cmh == null) {
                    cmh = new CountryMentalHealth(entity, code);
                    countries.put(entity, cmh);
                }
                cmh.addYearData(year, d);
//                System.out.println(d);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public  List<CountryMentalHealth> showTop10Depression() {
        // Copy values into a list for easy sorting.
        List<CountryMentalHealth> list = new ArrayList<>(countries.values());

        // Sort descending by latest-year depression percentage.
        Collections.sort(list, new Comparator<CountryMentalHealth>() {
            @Override public int compare(CountryMentalHealth a, CountryMentalHealth b) {
                double da = a.latestData().getDepression();
                double db = b.latestData().getDepression();
                return Double.compare(db, da);   // descending
            }
        });

        System.out.println("\nTop-10 countries by latest-year depression share:");
        for (int i = 0; i < list.size() && i < 10; i++) {
            CountryMentalHealth c = list.get(i);
            Data d = c.latestData();
            System.out.println();
        }
        System.out.println(list.toString());
        return list;
    }

    public CountryMentalHealth showCountry(String name) {
        CountryMentalHealth c = countries.get(name);
        if (c == null) {
            return null;
        }

        return c;
    }

    public CountryMentalHealth filterYear(int year) {
        CountryMentalHealth worst = null;
        double maxDep = Double.NEGATIVE_INFINITY;

        for (CountryMentalHealth c : countries.values()) {
            System.out.println("COUNTRY CURRENT: " + c.getName());
            Data d = c.getAllData().get(year);
            if (d == null) continue;
            System.out.println("COUNTRY CURRENT DEP: " + d.getDepression());
            if (d.getDepression() > maxDep) {
                maxDep = d.getDepression();
                worst  = c;
                System.out.println("COUNTRY HIGH: " + c.getName());
                System.out.println("VALUE: " + d.getDepression());
            }
        }

        if (worst == null) {
            System.out.println("No country has data for that year.");
            return null;
        } else {
            return worst;
        }
    }
}
