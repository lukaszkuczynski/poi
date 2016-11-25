package pl.gihon.fdd.poi.printer.template;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.gihon.fdd.poi.exception.PoiException;
import pl.gihon.fdd.poi.model.Area;
import pl.gihon.fdd.poi.model.Place;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by luk on 2016-11-12.
 */
@Component
public class StaticMapCreator {

    public static final String BASE_URL = "https://maps.googleapis.com/maps/api/staticmap";

    @Value("${GOOGLE_API_KEY}")
    private String apiKey;


    /**
     * downloads map for area, writes to file at filename
     * @return
     */
    public void getForArea(Area area, String fileName) throws IOException {
        String url = urlForArea(area);
        byte[] response = queryUrl(url);
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(response);


    }

    private byte[] queryUrl(String url) throws IOException {
        RequestBuilder requestBuilder = RequestBuilder.get(url);
        HttpUriRequest request = requestBuilder.build();

        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(request);

        if (response.getStatusLine().getStatusCode() != 200) {
            String responseText = EntityUtils.toString(response.getEntity());
            int code = response.getStatusLine().getStatusCode();
            throw new PoiException(String.format("exception while requesting static map for area, code %d, text %s",code,responseText));
        }

        InputStream inputStream = response.getEntity().getContent();

        byte[] bytes = IOUtils.toByteArray(inputStream);

        return bytes;

    }

    private String urlForArea(Area area) {
        String center = "?center=";
        String markers = "";
        int scalevalue = 1;
        //
        int no = 1;
        for (Place place : area.getPlaces()) {
            String latlng = place.getLatitude() + "," + place.getLongitude();
            markers = markers + "&markers=color:blue%7Clabel:" + String.valueOf(no) + "%7C" + latlng;
            no ++;
        }
        String zoom = "";
        int width = 800;
        int height = 640;

        String scale = "&scale="+scalevalue;
        int wid = width / scalevalue;
        int hei = height / scalevalue;
        String size = "&size=" + wid + "x" + hei;
        String key = "&key=" + apiKey;
        return BASE_URL + center + markers + zoom + scale + size + key;
    }
}
