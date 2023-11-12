package com.example.idealselect.controller.api;

import com.example.idealselect.dto.IdealSelectionDto;
import com.example.idealselect.exception.CustomException;
import com.example.idealselect.exception.ErrorCode;
import com.example.idealselect.service.IdealSelectionService;
import com.example.idealselect.session.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/selection")
public class SelectionApiController {

    private final IdealSelectionService selectionService;
    private final SessionManager sessionManager;

    @PostMapping("/create")
    public ResponseEntity<IdealSelectionDto> create(@RequestParam String title,
                                                    @RequestParam String body,
                                                    @RequestParam List<MultipartFile> files, HttpServletRequest request){
        return new ResponseEntity<>(selectionService.create(title, body, files, request), HttpStatus.OK);
    }

//    @GetMapping("/myList")
//    public ResponseEntity<IdealSelectionDto> getSelectionSubList(@RequestParam(defaultValue = "0") Integer pagePrefix, HttpServletRequest request){
//        if (sessionManager.getSession(request).isEmpty())
//            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
//
//        model.addAttribute("selectionList", selectionService.getCreationList(pagePrefix, request));
//    }

}
