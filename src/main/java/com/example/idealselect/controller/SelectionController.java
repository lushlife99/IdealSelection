package com.example.idealselect.controller;

import com.example.idealselect.dto.IdealSelectionDto;
import com.example.idealselect.service.IdealSelectionService;
import com.example.idealselect.session.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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
    public String mySelectionPage(Model model, HttpServletRequest request){
        if (sessionManager.getSession(request).isEmpty())
            return "redirect:/login";
        List<IdealSelectionDto> creationList = selectionService.getCreationList(request);

        for (IdealSelectionDto idealSelectionDto : creationList) {
            System.out.println(creationList);
            System.out.println();
        }
        model.addAttribute("selectionList", selectionService.getCreationList(request));

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
