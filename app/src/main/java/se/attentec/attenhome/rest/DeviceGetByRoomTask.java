package se.attentec.attenhome.rest;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import se.attentec.attenhome.DeviceListAdapter;
import se.attentec.attenhome.models.Device;

public class DeviceGetByRoomTask extends AsyncTask<Void, Void, Device[]> {

    private String mRoomId;
    private RecyclerView mListView;

    public DeviceGetByRoomTask(String roomId, RecyclerView list){
        mRoomId = roomId;
        mListView = list;
    }

    @Override
    protected Device[] doInBackground(Void... params) {
        try {
            final String url = "http://188.166.33.205:3000/api/devices?roomId=" + mRoomId;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return restTemplate.getForObject(url, Device[].class);
        } catch (Exception e) {
            Log.e("DeviceGetTask", e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Device[] devices) {
        mListView.setAdapter(new DeviceListAdapter(devices));
    }
}