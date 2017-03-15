package se.attentec.attenhome.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;


@JsonIgnoreProperties({"__v"})
public class Room extends BaseModel{
    private String houseId;
    private ArrayList<Double> temperature;

    public Room(){}

    public Room(Parcel in){
        this._id = in.readString();
        this.houseId = in.readString();
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
        dest.writeString(this.houseId);
        dest.writeString(this.name);
        dest.writeList(this.powerData);
        dest.writeList(this.temperature);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public ArrayList<Double> getTemperature() {
        return temperature;
    }

    public void setTemperature(ArrayList<Double> temperature) {
        this.temperature = temperature;
    }
}
