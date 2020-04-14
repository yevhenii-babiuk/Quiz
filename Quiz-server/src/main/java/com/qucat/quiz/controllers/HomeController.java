package com.qucat.quiz.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.InetAddress;

@Slf4j
@Controller
@RequestMapping("/home")
public class HomeController {
    @GetMapping
    public String home(Model model) {
        log.info("input url" + InetAddress.getLoopbackAddress().getHostName());//todo delete later

        return "forward:/index.html";
    }
}
