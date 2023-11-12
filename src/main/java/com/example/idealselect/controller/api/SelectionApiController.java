package com.example.idealselect.controller.api;

import com.example.idealselect.dto.IdealSelectionDto;
import com.example.idealselect.exception.CustomException;
import com.example.idealselect.exception.ErrorCode;
import com.example.idealselect.service.IdealSelectionService;
import com.example.idealselect.session.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @GetMapping(value = "/image/{imageName}/{imageName2}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getIdealImg(@PathVariable String imageName, @PathVariable String imageName2) {

        try {
            byte[] img = selectionService.getIdealImg(imageName, imageName2);
            return new ResponseEntity<>(img, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
