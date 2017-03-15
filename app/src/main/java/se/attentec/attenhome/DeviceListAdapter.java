package se.attentec.attenhome;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import se.attentec.attenhome.models.Device;
import se.attentec.attenhome.models.Radiator;
import se.attentec.attenhome.rest.DeviceUpdateTask;


public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.ViewHolder> {
    private Device[] mDataset;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtHeader;
        public SwitchCompat powerSwitch;
        public TextView temperatureText;
        private RelativeLayout clicky;

        public ViewHolder(View v) {
            super(v);
            clicky = (RelativeLayout) v.findViewById(R.id.clicky_container);
            txtHeader = (TextView) v.findViewById(R.id.name);
            powerSwitch = (SwitchCompat) v.findViewById(R.id.list_power_switch);
            temperatureText = (TextView) v.findViewById(R.id.list_temperature_text);
        }
    }

    public DeviceListAdapter(Device[] myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DeviceListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_device, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Device device = mDataset[position];
        holder.powerSwitch.setChecked(device.isPowered());
        holder.powerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                device.setPowered(isChecked);
                new DeviceUpdateTask(device).execute();
            }
        });
        holder.txtHeader.setText(device.getName());
        holder.clicky.setOnClickListener(new DeviceClickListener(mDataset[position]));
        holder.txtHeader.setTypeface(Typeface.createFromAsset(holder.txtHeader.getContext().getAssets(),
                "fonts/lato.ttf"));
        if(device instanceof Radiator){
            Radiator r = (Radiator) device;
            int currentTemp = (int) Math.round(r.getTemperature().get(r.getTemperature().size()-1));
            holder.temperatureText.setText(currentTemp + "\u00b0 C");
            holder.temperatureText.setOnClickListener(new DeviceClickListener(mDataset[position]));
            holder.temperatureText.setTypeface(Typeface.createFromAsset(
                    holder.temperatureText.getContext().getAssets(),
                    "fonts/lato.ttf"));
        }

    }

    @Override
    public int getItemCount() {
        if(mDataset != null) return mDataset.length;
        return 0;
    }


    private class DeviceClickListener implements View.OnClickListener {
        private Device mDevice;

        public DeviceClickListener(Device device){
            mDevice = device;
        }

        @Override
        public void onClick(View v) {
            Log.d("Pressed device", mDevice.getName());
            Intent intent=new Intent(v.getContext(), DeviceActivity.class);
            intent.putExtra("device", mDevice);
            v.getContext().startActivity(intent);
        }
    }


}
