package se.attentec.attenhome.rest;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.github.mikephil.charting.data.LineData;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import se.attentec.attenhome.StatsListAdapter;
import se.attentec.attenhome.models.BaseModel;
import se.attentec.attenhome.models.Device;
import se.attentec.attenhome.models.Radiator;

public class DeviceGetStatsTask extends StatsBaseTask<Device> {

    private String mDeviceId;
    private RecyclerView mList;

    public DeviceGetStatsTask(String deviceId, RecyclerView list){
        mDeviceId = deviceId;
        mList = list;
    }

    @Override
    protected Device doInBackground(Void... params) {
        try {
            final String url = "http://188.166.33.205:3000/api/devices/" + mDeviceId;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Device device = restTemplate.getForObject(url, Device.class);
            return device;
        } catch (Exception e) {
            Log.e("RoomListFragment", e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Device device) {
        List<List<Double>> list = new ArrayList<>();
        list.add(device.getPowerData());
        if(device instanceof Radiator){
            Radiator r = (Radiator) device;
            list.add(r.getTemperature());
        }
        if(mList.getAdapter() == null){
            mList.setAdapter(new StatsListAdapter());
        }
        List<LineData> data = createData(list);
        StatsListAdapter adapter = (StatsListAdapter) mList.getAdapter();
        adapter.setStatsData(data);
    }
}
