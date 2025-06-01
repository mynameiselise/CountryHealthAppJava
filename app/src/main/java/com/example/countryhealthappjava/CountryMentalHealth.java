package com.example.countryhealthappjava;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class CountryMentalHealth {
    private final String name;
    private final String code;
    private final Map<Integer, Data> mentalHealthMap = new HashMap<>();

    public CountryMentalHealth(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public void addYearData(int year, Data d) {
        mentalHealthMap.put(year, d);
    }
    public String getName() {
        return name;
    }
    public String getCode() {
        return code;
    }
    public Map<Integer, Data> getAllData(){
        return mentalHealthMap;
    }


    /** Latest calendar year with data. */
    public int latestYear() {
        int latest = Integer.MIN_VALUE;
        for (int y : mentalHealthMap.keySet()) {
            if (y > latest) latest = y;
        }
        if (latest == Integer.MIN_VALUE) {
            throw new IllegalStateException("No year-data available");
        }
        return latest;
    }

    /** Data object for the latest available year. */
    public Data latestData() {
        return mentalHealthMap.get(latestYear());
    }

    /** Mean depressive-disorder share across all years. */
    public double avgDepression() {
        double sum = 0.0;
        int count  = 0;
        for (Data d : mentalHealthMap.values()) {
            sum   += d.getDepression();
            count += 1;
        }
        return (count == 0) ? Double.NaN : sum / count;
    }

    /** Year in which this country recorded its highest depression share. */
    public int peakDepressionYear() {
        double maxDep = Double.NEGATIVE_INFINITY;
        int    year   = -1;
        for (Map.Entry<Integer, Data> e : mentalHealthMap.entrySet()) {
            double dep = e.getValue().getDepression();
            if (dep > maxDep) {
                maxDep = dep;
                year   = e.getKey();
            }
        }
        if (year == -1) {
            throw new IllegalStateException("No depression data available");
        }
        return year;
    }

    @Override
    public String toString() {
        return "CountryMentalHealth{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", mentalHealthMap=" + mentalHealthMap +
                '}';
    }
}
