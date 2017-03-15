package se.attentec.attenhome.rest;

import android.os.AsyncTask;
import android.util.Log;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import se.attentec.attenhome.models.Device;

public class DeviceUpdateTask extends AsyncTask<Void, Void, String> {

    private Device mDevice;

    public DeviceUpdateTask(Device device){
        this.mDevice = device;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            final String url = "http://188.166.33.205:3000/api/devices/" + mDevice.get_id();
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.put(url, mDevice);
            return "Device updated";
        } catch (Exception e) {
            Log.e("DeviceUpdateTask", e.getMessage(), e);
        }
        return "Update failed";
    }

}
