package com.example.idealselect.controller.api;

import com.example.idealselect.dto.IdealSelectionDto;
import com.example.idealselect.service.IdealSelectionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/api/selection")
public class SelectionApiController {

    private final IdealSelectionService selectionService;


    @PostMapping("/create")
    public ResponseEntity<IdealSelectionDto> create(@RequestParam String title,
                                                    @RequestParam String body,
                                                    @RequestParam List<MultipartFile> files, HttpServletRequest request){
        return new ResponseEntity<>(selectionService.create(title, body, files, request), HttpStatus.OK);
    }

    @GetMapping(value = "/image/{selectionPath}/{idealName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getIdealImg(@PathVariable String selectionPath, @PathVariable String idealName) {

        try {
            byte[] img = selectionService.getIdealImg(selectionPath, idealName);
            return new ResponseEntity<>(img, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteIdealSelection(@PathVariable Long id, HttpServletRequest request){
        selectionService.delete(id, request);
        return  new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/play")
    public IdealSelectionDto playSelection(@RequestParam Long selectionId, @RequestParam int round, HttpServletRequest request){
        return selectionService.getPlayableSelection(selectionId, round, request);
    }

    @PutMapping("/play/endGame/{winIdealId}")
    public ResponseEntity updateCounts(@PathVariable Long winIdealId, @RequestBody IdealSelectionDto playingSelection, HttpServletRequest request){
        selectionService.updateWinCount(winIdealId, playingSelection, request);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/search")
    public List<IdealSelectionDto> getSearchList(@RequestParam(required = false, defaultValue = "") String context, @RequestParam(required = false, defaultValue = "POPULARITY") String orderBy,
                                                 @RequestParam(required = false, defaultValue = "0") Integer pageNum, HttpServletRequest request){

        if(orderBy.equals("LATEST")) {
            return selectionService.getByLatest(context, pageNum, request);
        } else return selectionService.getByPopularity(context, pageNum, request);
    }


}