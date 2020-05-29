package com.joss.bundaegi.service;

import com.joss.bundaegi.domain.LocationDomain;
import com.joss.bundaegi.domain.Response.JSONResponse;
import com.joss.bundaegi.domain.RestException;
import com.joss.bundaegi.domain.UserDomain;
import com.joss.bundaegi.mapper.CommonMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@Transactional
public class CommonService {
    @Autowired
    CommonMapper commonMapper;

    public JSONResponse<LocationDomain> getLocationFromAddr(final String addr) {
        JSONResponse<LocationDomain> response;
        try{
            LocationDomain locationInfo = fn_getLocationFromAddr(addr);
            if(locationInfo.getCode() == 1) response = new JSONResponse<>(1,"succ.select.addr",locationInfo);
            else throw new Exception();
        }catch (Exception e){
            throw new RestException(0,"fail.select.addr",e.getLocalizedMessage(),null);
            //response = new JSONResponse<>(0,"fail.select.addr",null);
        }
        return response;
    }

    public LocationDomain fn_getLocationFromAddr(final String addr){
        final String CLIENTID = "p64anon2ax";
        final String CLIENTSECRET = "UKZ8xbuICeCp2x77oBz7ipNIgIGr5TYFR1WXwt9s";
        final String MAPURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=";

        LocationDomain domain;
        try {
            String pAddr = URLEncoder.encode(addr, "UTF-8");
            String apiURL = MAPURL + pAddr; //json
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", CLIENTID);
            con.setRequestProperty("X-NCP-APIGW-API-KEY", CLIENTSECRET);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream(), StandardCharsets.UTF_8));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            JSONParser jsonParser = new JSONParser();
            Object object = jsonParser.parse(response.toString());
            JSONObject jsonObject = (JSONObject) object;

            JSONArray addresses = (JSONArray) jsonObject.get("addresses");

            String lon = "";
            String lat = "";

            for(int j = 0; j < addresses.size(); ++j){
                JSONObject address = (JSONObject) addresses.get(j);
                if(address.containsKey("x")) lon = (String) address.get("x");
                if(address.containsKey("y")) lat = (String) address.get("y");
            }
            if("".equals(lon) || "".equals(lat)) throw new Exception();
            else domain = new LocationDomain(1,Float.parseFloat(lat),Float.parseFloat(lon));
        } catch (Exception e) {
            domain = new LocationDomain(0,0,0);
        }
        return domain;
    }
}
