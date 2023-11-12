package com.example.idealselect.controller;

import com.example.idealselect.dto.IdealSelectionDto;
import com.example.idealselect.service.IdealSelectionService;
import com.example.idealselect.session.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SelectionController {

    private final IdealSelectionService selectionService;
    private final SessionManager sessionManager;

    @GetMapping("/main")
    public String mainPage(@RequestParam(value = "pagePrefix", required = false, defaultValue = "0") Integer pagePrefix, Model model, HttpServletRequest request){
        if (sessionManager.getSession(request).isEmpty())
            return "redirect:/login";

        model.addAttribute("selectionList",selectionService.getByPopularity(pagePrefix, request));
        model.addAttribute("pagePrefix", pagePrefix);
        return "main";
    }

    @GetMapping("/mySelection")
    public String mySelectionPage(@RequestParam(value = "pagePrefix", required = false, defaultValue = "0") Integer pagePrefix, Model model, HttpServletRequest request){
        if (sessionManager.getSession(request).isEmpty())
            return "redirect:/login";

        model.addAttribute("selectionList", selectionService.getCreationList(pagePrefix, request));
        model.addAttribute("pagePrefix", pagePrefix);
        return "mySelection";
    }

    @GetMapping("/play")
    public String playPage(Model model, HttpServletRequest request){
        if (sessionManager.getSession(request).isEmpty())
            return "redirect:/login";

        return "play";
    }

    @GetMapping("/create")
    public String createPage(Model model, HttpServletRequest request){
        if (sessionManager.getSession(request).isEmpty())
            return "redirect:/login";

        return "create";
    }
}
