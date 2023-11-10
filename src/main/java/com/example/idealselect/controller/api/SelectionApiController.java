package com.example.idealselect.controller.api;

import com.example.idealselect.service.IdealSelectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/selection")
public class SelectionApiController {

    private final IdealSelectionService selectionService;

}
