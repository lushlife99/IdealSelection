package com.example.idealselect.controller;

import com.example.idealselect.dto.IdealSelectionDto;
import com.example.idealselect.entity.User;
import com.example.idealselect.exception.CustomException;
import com.example.idealselect.exception.ErrorCode;
import com.example.idealselect.service.IdealSelectionService;
import com.example.idealselect.session.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class SelectionController {

    private final IdealSelectionService selectionService;
    private final SessionManager sessionManager;

    @GetMapping("/main")
    public String mainPage(Model model, HttpServletRequest request){
        if (sessionManager.getSession(request).isEmpty())
            return "redirect:/login";

        model.addAttribute("selectionList",selectionService.getByPopularity(request));
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
