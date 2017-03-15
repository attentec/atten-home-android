package se.attentec.attenhome;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import se.attentec.attenhome.models.Radiator;
import se.attentec.attenhome.rest.DeviceUpdateTask;

public class RadiatorFragment extends Fragment {
    private static final String RADIATOR = "radiator";
    private Radiator mRadiator;
    private TextView powerConsumptionText;

    public RadiatorFragment() {}

    public static RadiatorFragment newInstance(Radiator radiator) {
        RadiatorFragment fragment = new RadiatorFragment();
        Bundle args = new Bundle();
        args.putParcelable(RADIATOR, radiator);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRadiator = getArguments().getParcelable(RADIATOR);
            assert mRadiator != null;
            mRadiator.setType("Radiator");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_radiator, container, false);
        Switch powerswitch = (Switch) rootView.findViewById(R.id.powerswitch);
        powerswitch.setChecked(mRadiator.isPowered());
        powerswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updatePower(isChecked);
            }
        });
        Spinner effectSpinner = (Spinner) rootView.findViewById(R.id.effect_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.effect_strings,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        effectSpinner.setAdapter(adapter);
        effectSpinner.setSelection(mRadiator.getTemp());
        effectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateEffect(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        powerConsumptionText = (TextView) rootView.findViewById(R.id.power_consumption_text);
        setPowerConsumptionText();



        return rootView;
    }

    private void updateEffect(int effect) {
        mRadiator.setTemp(effect);
        setPowerConsumptionText();
        new DeviceUpdateTask(mRadiator).execute();
    }

    private void setPowerConsumptionText() {
        int powerConsumption = 0;
        if(mRadiator.isPowered()){
            powerConsumption =
                    (mRadiator.getPowerConsumption() * mRadiator.getTemp());
        }
        String s = powerConsumption+" W";
        powerConsumptionText.setText(s);
    }

    private void updatePower(boolean powered){
        mRadiator.setPowered(powered);
        setPowerConsumptionText();
        new DeviceUpdateTask(mRadiator).execute();
    }

}
