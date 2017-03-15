package se.attentec.attenhome.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class Lamp extends Device {
    private int dimmer;

    public Lamp(){}

    public int getDimmer() {
        return dimmer;
    }

    public void setDimmer(int dimmer) {
        this.dimmer = dimmer;
    }

    public Lamp(Parcel in){
        this._id = in.readString();
        this.roomId = in.readString();
        this.type = in.readString();
        this.name = in.readString();
        this.powered = Boolean.parseBoolean(in.readString());
        this.powerConsumption = in.readInt();
        this.dimmer = in.readInt();
        this.powerData = new ArrayList<>();
        in.readList(this.powerData, Double.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(this.roomId);
        dest.writeString(this.type);
        dest.writeString(this.name);
        dest.writeString(this.powered + "");
        dest.writeInt(this.powerConsumption);
        dest.writeInt(this.dimmer);
        dest.writeList(this.powerData);

    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Lamp createFromParcel(Parcel in) {
            return new Lamp(in);
        }

        public Lamp[] newArray(int size) {
            return new Lamp[size];
        }
    };
}
