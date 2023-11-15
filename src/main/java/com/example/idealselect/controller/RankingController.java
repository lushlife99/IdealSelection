package com.example.idealselect.controller;

import com.example.idealselect.service.IdealSelectionService;
import com.example.idealselect.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class RankingController {

    private final IdealSelectionService selectionService;
    private final ReplyService replyService;


}
