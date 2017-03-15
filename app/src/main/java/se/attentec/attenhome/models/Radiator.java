package se.attentec.attenhome.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

public class Radiator extends Device {
    private List<Double> temperature;
    private int temp;

    public Radiator(){

    }
    public Radiator(Parcel in){
        this._id = in.readString();
        this.roomId = in.readString();
        this.name = in.readString();
        this.powered = Boolean.parseBoolean(in.readString());
        this.powerConsumption = in.readInt();
        this.temp = in.readInt();
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
        dest.writeString(this.roomId);
        dest.writeString(this.name);
        dest.writeString(this.powered + "");
        dest.writeInt(this.powerConsumption);
        dest.writeInt(this.temp);
        dest.writeList(this.powerData);
        dest.writeList(this.temperature);
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Radiator createFromParcel(Parcel in) {
            return new Radiator(in);
        }
        public Radiator[] newArray(int size) {
            return new Radiator[size];
        }
    };

    public List<Double> getTemperature() {
        return temperature;
    }

    public void setTemperature(List<Double> temperature) {
        this.temperature = temperature;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }
}
