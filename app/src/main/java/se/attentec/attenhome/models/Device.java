package se.attentec.attenhome.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "__t")
@JsonSubTypes({
        @JsonSubTypes.Type(value=Lamp.class, name="Lamp"),
        @JsonSubTypes.Type(value=Radiator.class, name="Radiator"),
})

@JsonIgnoreProperties({"__v"})
public abstract class Device extends BaseModel {
    protected String roomId;
    protected boolean powered;
    protected int powerConsumption;
    protected String type;

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public boolean isPowered() {
        return powered;
    }

    public void setPowered(boolean powered) {
        this.powered = powered;
    }

    public int getPowerConsumption() {
        return powerConsumption;
    }

    public void setPowerConsumption(int powerConsumption) {
        this.powerConsumption = powerConsumption;
    }
}
