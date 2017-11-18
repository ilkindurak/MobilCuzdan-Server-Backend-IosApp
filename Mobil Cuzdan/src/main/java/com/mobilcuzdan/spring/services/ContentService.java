package com.mobilcuzdan.spring.services;

import com.mobilcuzdan.spring.db.ContentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by YunusS on 4/17/2016.
 */
@Component
public class ContentService {

    @Autowired
    ContentDao contentDao;

}
