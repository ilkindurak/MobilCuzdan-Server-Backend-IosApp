package com.mobilcuzdan.spring.mvc.controller;

import com.mobilcuzdan.spring.mvc.models.ActionResult;
import com.mobilcuzdan.spring.services.TransactionsService;
import com.mobilcuzdan.spring.services.UsersService;
import com.mobilcuzdan.spring.spring.ActiveUserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by fikricanca on 31.12.2016.
 */

@Controller
@RequestMapping(value = "/transactions")
public class TransactionsController {

    @Autowired
    TransactionsService transactionsService;

    @RequestMapping(value = "/make-payment-with-card", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult makePaymentWithCard(@ActiveUserId Integer userId, @RequestParam Integer cardId, @RequestParam Integer storeId, @RequestParam Double amount) {

        return transactionsService.makePaymentWithCard(userId, cardId, storeId, amount);

    }

}
