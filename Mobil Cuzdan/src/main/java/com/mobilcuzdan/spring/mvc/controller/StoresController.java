package com.mobilcuzdan.spring.mvc.controller;

import com.mobilcuzdan.spring.mvc.models.Store;
import com.mobilcuzdan.spring.services.StoresService;
import com.mobilcuzdan.spring.services.UploadService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/**
 * Created by fikricanca on 31.12.2016.
 */
@Controller
@RequestMapping(value = "/stores", produces = MediaType.APPLICATION_JSON_VALUE)
public class StoresController {

    @Autowired
    StoresService storesService;

    @Autowired
    UploadService uploadService;

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    @ResponseBody
    public Store me(@RequestParam Integer storeId) {
        return storesService.getStoreById(storeId);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public Store signUp(@RequestParam String name, @RequestParam String email, @RequestParam String phone, @RequestParam String password) {
        Store store = storesService.createStore(name, email, phone, password);
        return store;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public Store login(HttpServletResponse response, @RequestParam String email, @RequestParam String password) {
        Store store = storesService.loginWithEmailAndPassword(email, password);

        if (store == null) {
            return null;
        }else {
            return store;
        }
    }

    @RequestMapping(value = "/create-qr", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public JSONObject createQrCodeUrl(@RequestParam int storeId, @RequestParam Double amount) {

        String qrString = storeId + "/" + amount;
        try {
            BufferedImage qrCodeImage = uploadService.createQRImage(qrString, 2048);
            String imageUrl = uploadService.persistFile(qrCodeImage);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("image", imageUrl);
            return jsonObject;
        }catch (Exception e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("error", e.toString());
            return jsonObject;
        }
    }



}
