package com.example.idealselect.controller.api;

import com.example.idealselect.dto.IdealDto;
import com.example.idealselect.dto.IdealSelectionDto;
import com.example.idealselect.entity.Ideal;
import com.example.idealselect.exception.CustomException;
import com.example.idealselect.exception.ErrorCode;
import com.example.idealselect.service.IdealSelectionService;
import com.example.idealselect.service.IdealService;
import com.example.idealselect.session.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SelectionApiController {

    private final IdealSelectionService selectionService;
    private final IdealService idealService;
    private final SessionManager sessionManager;

    @PostMapping("/selection/create")
    public ResponseEntity<IdealSelectionDto> create(@RequestParam String title,
                                                    @RequestParam String body,
                                                    @RequestParam List<MultipartFile> files, HttpServletRequest request){
        return new ResponseEntity<>(selectionService.create(title, body, files, request), HttpStatus.OK);
    }

    /**
     *
     * @return updated Image Url
     */

    @PutMapping("/ideal/image")
    public ResponseEntity<IdealDto> updateImage(@RequestParam String filePath,
                                                @RequestParam Long idealId,
                                                @RequestParam Long selectionId,
                                                @RequestParam MultipartFile file, HttpServletRequest request){
        return new ResponseEntity<>(idealService.updateImage(filePath, idealId, selectionId, file, request), HttpStatus.OK);
    }

    @GetMapping(value = "/selection/image/{imageName}/{imageName2}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getIdealImg(@PathVariable String imageName, @PathVariable String imageName2) {

        log.info("imageName2={}", imageName2);
        try {
            byte[] img = selectionService.getIdealImg(imageName, imageName2);
            return new ResponseEntity<>(img, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/ideal/{filePath}")
    public ResponseEntity editIdealName(@PathVariable String filePath, @RequestBody IdealDto idealDto, HttpServletRequest request){
        selectionService.editIdealName(filePath, idealDto, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/ideal/{id}")
    public ResponseEntity deleteIdeal(@PathVariable Long id, HttpServletRequest request){
        idealService.delete(id, request);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/idealSelection/{id}")
    public ResponseEntity deleteIdealSelection(@PathVariable Long id, HttpServletRequest request){
        selectionService.delete(id, request);
        return  new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/play")
    public IdealSelectionDto playSelection(@RequestParam Long selectionId, @RequestParam int round, HttpServletRequest request){
        return selectionService.getPlayableSelection(selectionId, round, request);
    }

    @PutMapping("/play/winCount")
    public ResponseEntity updateWinCount(@RequestParam Long winIdealId, @RequestParam Long loseIdealId, HttpServletRequest request){
        selectionService.updateWinCount(winIdealId, loseIdealId, request);
        return new ResponseEntity(HttpStatus.OK);
    }


}
