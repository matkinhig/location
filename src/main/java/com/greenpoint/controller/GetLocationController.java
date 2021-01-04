package com.greenpoint.controller;

import com.google.gson.*;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/")
public class GetLocationController {

    @GetMapping("/province")
    public ResponseEntity<Object> getAllProvince() {
        InputStream input = GetLocationController.class.getClassLoader().getResourceAsStream("data-tinh-thanh.json");
        InputStreamReader is = null;
        //todo using redis
        try {
            is = new InputStreamReader(input, "UTF-8");
            String jsonTxt = IOUtils.toString(is);
            Gson g = new Gson();
            JsonArray array = g.fromJson(jsonTxt, JsonArray.class);
            JsonArray result = new JsonArray();
            for (JsonElement j : array) {
                JsonObject js = new JsonObject();
                js.addProperty("id", j.getAsJsonObject().get("id").getAsString());
                js.addProperty("name", j.getAsJsonObject().get("name").getAsString());
                result.add(js);
            }
            return new ResponseEntity<>(g.toJson(result), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("", HttpStatus.OK);
        }
    }

    @GetMapping("/district/{id}")
    public ResponseEntity<Object> getDistrictByProvince(@PathVariable String id) {
        InputStream input = GetLocationController.class.getClassLoader().getResourceAsStream("data-tinh-thanh.json");
        InputStreamReader is = null;
        //todo using redis
        try {
            is = new InputStreamReader(input, "UTF-8");
            String jsonTxt = IOUtils.toString(is);
            Gson g = new Gson();
            JsonArray array = g.fromJson(jsonTxt, JsonArray.class);
            JsonArray result = new JsonArray();
            for (JsonElement j : array) {
                if (j.getAsJsonObject().get("id").getAsString().equals(id)) {
                    result = j.getAsJsonObject().get("huyen").getAsJsonArray();
                }
            }
            JsonArray result1 = new JsonArray();
            for (JsonElement j : result) {
                JsonObject js = new JsonObject();
                js.addProperty("id", j.getAsJsonObject().get("id").getAsString());
                js.addProperty("name", j.getAsJsonObject().get("name").getAsString());
                result1.add(js);
            }
            return new ResponseEntity<>(g.toJson(result1), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("", HttpStatus.OK);
        }
    }

    @GetMapping("/wards/{prov}/{dist}")
    public ResponseEntity<Object> getAllWardByProvinceAnDistrict(@PathVariable(name = "prov") String prov, @PathVariable(name = "dist") String dist) {
        InputStream input = GetLocationController.class.getClassLoader().getResourceAsStream("data-tinh-thanh.json");
        InputStreamReader is = null;
        //todo using redis
        try {
            is = new InputStreamReader(input, "UTF-8");
            String jsonTxt = IOUtils.toString(is);
            Gson g = new Gson();
            JsonArray array = g.fromJson(jsonTxt, JsonArray.class);
            JsonArray result = new JsonArray();
            for (JsonElement j : array) {
                if (j.getAsJsonObject().get("id").getAsString().equals(prov)) {
                    result = j.getAsJsonObject().get("huyen").getAsJsonArray();
                }
            }
            JsonArray result1 = new JsonArray();
            for (JsonElement j : result) {
                if (j.getAsJsonObject().get("id").getAsString().equals(dist)) {
                    result1 = j.getAsJsonObject().get("xa").getAsJsonArray();
                }
            }
            JsonArray result2 = new JsonArray();
            for (JsonElement j : result1) {
                JsonObject js = new JsonObject();
                js.addProperty("id", j.getAsJsonObject().get("id").getAsString());
                js.addProperty("name", j.getAsJsonObject().get("name").getAsString());
                result2.add(js);
            }
            return new ResponseEntity<>(g.toJson(result2), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("", HttpStatus.OK);
        }
    }
}
