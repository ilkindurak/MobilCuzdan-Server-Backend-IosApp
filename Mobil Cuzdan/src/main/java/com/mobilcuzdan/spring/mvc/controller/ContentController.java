package com.mobilcuzdan.spring.mvc.controller;

import com.mobilcuzdan.spring.services.ContentService;
import com.mobilcuzdan.spring.services.UsersService;
import com.mobilcuzdan.spring.spring.ActiveUserId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * Created by YunusS on 4/21/2016.
 */
@Controller
@RequestMapping("/content")
public class ContentController {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    ContentService contentService;

    @Autowired
    UsersService usersService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public HashMap<String, Object> test(@ActiveUserId Integer id) {


        HashMap<String, Object> result = new HashMap<>();
        log.debug(usersService.getUserById(id));
        result.put("test", usersService.getUserById(id).getEmail());
        return result;
    }
}
