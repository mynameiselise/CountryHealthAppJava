package com.example.countryhealthappjava;

public class Data {
    private double schizophrenia;
    private double depression;
    private double anxiety;
    private double bipolar;
    private double eating;

    public Data(double schizophrenia, double depression, double anxiety, double bipolar, double eating) {
        this.schizophrenia = schizophrenia;
        this.depression = depression;
        this.anxiety = anxiety;
        this.bipolar = bipolar;
        this.eating = eating;
    }

    public double getSchizophrenia() {
        return schizophrenia;
    }
    public double getDepression() {
        return depression;
    }
    public double getAnxiety()  {
        return anxiety;
    }
    public double getBipolar() {
        return bipolar;
    }
    public double getEating() {
        return eating;
    }

    // Returns the mental-disorder name with the largest percentage in this entry.
    public String mostPrevalent() {
        double[] vals = { schizophrenia, depression, anxiety, bipolar, eating };
        String[] names = { "Schizophrenia", "Depression",
                "Anxiety", "Bipolar", "Eating Disorder" };
        int maxIdx = 0;
        for (int i = 1; i < vals.length; i++) {
            if (vals[i] > vals[maxIdx]) maxIdx = i;
        }
        return names[maxIdx] + "";
    }
}

