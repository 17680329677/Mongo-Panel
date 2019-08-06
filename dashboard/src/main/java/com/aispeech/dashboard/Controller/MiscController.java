package com.aispeech.dashboard.Controller;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
public class MiscController {
    @RequestMapping(value = "/healthz", method = RequestMethod.GET)
    public String health() {
        return "up";
    }
}

