package se.attentec.attenhome.models;

import android.os.Parcelable;
import java.util.List;

public abstract class BaseModel implements Parcelable {

    protected String _id;
    protected String name;
    protected List<Double> powerData;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Double> getPowerData() {
        return powerData;
    }

    public void setPowerData(List<Double> powerData) {
        this.powerData = powerData;
    }
}
