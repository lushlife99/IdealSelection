package com.example.idealselect.controller;

import com.example.idealselect.service.IdealSelectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/selection")
public class SelectionController {

    private final IdealSelectionService selectionService;

    @GetMapping("/main")
    public String mainPage(){

        return "main";
    }

    @GetMapping("/myList")
    public String mySelectionPage(){

        return "mySelection";
    }

    @GetMapping("/play")
    public String playPage(){

        return "play";
    }
}
