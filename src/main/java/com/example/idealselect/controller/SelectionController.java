package com.example.idealselect.controller;

import com.example.idealselect.dto.UserDto;
import com.example.idealselect.entity.User;
import com.example.idealselect.service.IdealSelectionService;
import com.example.idealselect.service.ReplyService;
import com.example.idealselect.session.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class SelectionController {

    private final IdealSelectionService selectionService;
    private final SessionManager sessionManager;
    private final ReplyService replyService;

    @GetMapping("/main")
    public String mainPage(@RequestParam(value = "pagePrefix", required = false, defaultValue = "0") Integer pagePrefix, Model model, HttpServletRequest request){

        if (sessionManager.getSession(request).isEmpty())
            return "redirect:/login";

        model.addAttribute("selectionList",selectionService.getByPopularity("", pagePrefix, request));
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
    public String playPage(@RequestParam Long selectionId, Model model, HttpServletRequest request){
        if (sessionManager.getSession(request).isEmpty())
            return "redirect:/login";

        model.addAttribute("selection", selectionService.getSelection(selectionId));

        return "play";
    }

    @GetMapping("/create")
    public String createPage(Model model, HttpServletRequest request){
        if (sessionManager.getSession(request).isEmpty())
            return "redirect:/login";

        return "create";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam Long selectionId, Model model, HttpServletRequest request){
        if (sessionManager.getSession(request).isEmpty())
            return "redirect:/login";

        model.addAttribute("selection", selectionService.getSelection(selectionId, request));
        return "edit";
    }

    @GetMapping("/ranking")
    public String rankingPage(@RequestParam Long id, Model model, HttpServletRequest request){
        if (sessionManager.getSession(request).isEmpty())
            return "redirect:/login";

        model.addAttribute("selection", selectionService.getSelection(id, request));
        return "ranking";
    }

    @GetMapping("/reply")
    public String replyPage(@RequestParam("id") Long selectionId, @RequestParam(value = "pagePrefix", required = false, defaultValue = "0") Integer pagePrefix, Model model, HttpServletRequest request){
        Optional<User> session = sessionManager.getSession(request);
        if (session.isEmpty())
            return "redirect:/login";

        model.addAttribute("replyList", replyService.getReplyList(selectionId, pagePrefix, request));
        model.addAttribute("user", new UserDto(session.get()));
        model.addAttribute("pagePrefix", pagePrefix);
        model.addAttribute("selectionId", selectionId);
        return "reply";
    }
}
