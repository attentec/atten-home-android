package se.attentec.attenhome;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import se.attentec.attenhome.models.Lamp;
import se.attentec.attenhome.rest.DeviceUpdateTask;

public class LampFragment extends Fragment {
    private static final String LAMP = "lamp";
    private Lamp mLamp;
    private TextView dimmerLabel;
    private TextView powerConsumptionText;

    public LampFragment() {}

    public static LampFragment newInstance(Lamp lamp) {
        LampFragment fragment = new LampFragment();
        Bundle args = new Bundle();
        args.putParcelable(LAMP, lamp);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLamp = getArguments().getParcelable(LAMP);
            assert mLamp != null;
            mLamp.setType("Lamp");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_lamp, container, false);
        Switch powerswitch = (Switch) rootView.findViewById(R.id.powerswitch);
        powerswitch.setChecked(mLamp.isPowered());
        powerswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updatePower(isChecked);
            }
        });
        powerConsumptionText = (TextView) rootView.findViewById(R.id.power_consumption_text);
        setPowerConsumptionText();
        SeekBar dimmer = (SeekBar) rootView.findViewById(R.id.dimmer_slider);
        dimmer.setProgress(mLamp.getDimmer());
        dimmer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String s = progress+" %";
                dimmerLabel.setText(s);
                mLamp.setDimmer(progress);
                setPowerConsumptionText();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateDimmer(seekBar.getProgress());

            }
        });
        dimmerLabel = (TextView) rootView.findViewById(R.id.dimmer_label);
        String s = mLamp.getDimmer()+" %";
        dimmerLabel.setText(s);
        return rootView;
    }

    private void setPowerConsumptionText() {
        int powerConsumption = 0;
        if(mLamp.isPowered()){
            powerConsumption =
                    (mLamp.getPowerConsumption() * mLamp.getDimmer()/ 100);
        }
        String s = powerConsumption+" W";
        powerConsumptionText.setText(s);
    }

    private void updateDimmer(int progress) {
        new DeviceUpdateTask(mLamp).execute();
    }

    private void updatePower(boolean powered){
        mLamp.setPowered(powered);
        setPowerConsumptionText();
        new DeviceUpdateTask(mLamp).execute();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
