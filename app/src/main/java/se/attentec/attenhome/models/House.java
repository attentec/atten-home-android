package se.attentec.attenhome.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Created by Attentec on 22/03/16.
 */
@JsonIgnoreProperties({"__v"})
public class House extends BaseModel {
    private ArrayList<Double> temperature;

    public House(){

    }

    public House(Parcel in){
        this._id = in.readString();
        this.name = in.readString();
        this.powerData = new ArrayList<>();
        in.readList(this.powerData, Double.class.getClassLoader());
        this.temperature = new ArrayList<>();
        in.readList(this.temperature, Double.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this._id);
        dest.writeString(this.name);
        dest.writeList(this.powerData);
        dest.writeList(this.temperature);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public House createFromParcel(Parcel in) {
            return new House(in);
        }
        public House[] newArray(int size) {
            return new House[size];
        }
    };

    public ArrayList<Double> getTemperature() {
        return temperature;
    }

    public void setTemperature(ArrayList<Double> temperature) {
        this.temperature = temperature;
    }
}
