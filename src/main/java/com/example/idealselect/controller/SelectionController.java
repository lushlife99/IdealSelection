package com.example.idealselect.controller;

import com.example.idealselect.entity.User;
import com.example.idealselect.exception.CustomException;
import com.example.idealselect.exception.ErrorCode;
import com.example.idealselect.service.IdealSelectionService;
import com.example.idealselect.session.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class SelectionController {

    private final IdealSelectionService selectionService;
    private final SessionManager sessionManager;

    @GetMapping("/main")
    public String mainPage(HttpServletRequest request){
        if (sessionManager.getSession(request).isEmpty())
            return "redirect:/login";

        return "main";
    }

    @GetMapping("/mySelection")
    public String mySelectionPage(){

        return "mySelection";
    }

    @GetMapping("/play")
    public String playPage(){

        return "play";
    }

    @GetMapping("/create")
    public String createPage(){

        return "create";
    }
}
