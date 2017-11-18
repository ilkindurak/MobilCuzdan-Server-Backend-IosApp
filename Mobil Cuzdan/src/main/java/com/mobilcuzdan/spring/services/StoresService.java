package com.mobilcuzdan.spring.services;

import com.google.zxing.WriterException;
import com.mobilcuzdan.spring.db.StoresDao;
import com.mobilcuzdan.spring.mvc.models.ActionResult;
import com.mobilcuzdan.spring.mvc.models.Store;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.UUID;

/**
 * Created by fikricanca on 31.12.2016.
 */
@Component
public class StoresService {

    @Autowired
    StoresDao storesDao;

    @Autowired
    UploadService uploadService;

    public Store loginWithEmailAndPassword(String email, String passwordClear) {
        Store storeByEmail = storesDao.getUserByEmail(email);
        if (storeByEmail != null) {
            boolean passwordMatch = BCrypt.checkpw(passwordClear, storeByEmail.getPassword());
            if (passwordMatch) {
                return storeByEmail;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public Store createStore(String name, String email, String phone, String passwordClear) {

        String hashedPassword = BCrypt.hashpw(passwordClear, BCrypt.gensalt());

        Store store = new Store();
        store.setName(name);
        store.setEmail(email);
        store.setPhone(phone);
        store.setPassword(hashedPassword);

        Integer newStoreId = storesDao.createStore(store);

        if (newStoreId > 0) {
            store.setId(newStoreId);
            return store ;
        }else {
            return store;
        }
    }

    public Store getStoreById(int storeId) {

        return storesDao.getStoreById(storeId);
    }

}
