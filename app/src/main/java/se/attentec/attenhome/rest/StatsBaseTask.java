package se.attentec.attenhome.rest;

import android.graphics.Color;
import android.os.AsyncTask;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Attentec on 22/03/16.
 */
public abstract class StatsBaseTask<T> extends AsyncTask<Void, Void, T>{
    public void execute(){
        super.execute();
    }

    public List<LineData> createData(List<List<Double>> stats){
        List<LineData> data = new ArrayList<>();
        for (List<Double> list:stats) {
            ArrayList<Entry> entries = new ArrayList<>();
            ArrayList<String> labels = new ArrayList<>();
            int listSize = list.size()-1;
            for (int i = 0; i < 21; i++) {
                entries.add(new Entry(list.get(listSize-i).floatValue(), 20-i));
                labels.add(i+"");
            }
            LineDataSet dataset = new LineDataSet(entries, "");
            dataset.setDrawCircles(true);
            dataset.setCircleColor(Color.parseColor("#910B26"));
            dataset.setDrawValues(false);
            dataset.setColor(Color.parseColor("#910B26"));
            data.add(new LineData(labels,dataset));
        }
        return data;
    }

}

