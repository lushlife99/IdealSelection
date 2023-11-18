package com.example.idealselect.controller.api;

import com.example.idealselect.dto.IdealDto;
import com.example.idealselect.service.IdealService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/ideal")
@RequiredArgsConstructor
public class IdealApiController {

    private final IdealService idealService;

    /**
     *
     * @return updated Image Url
     */

    @PutMapping("/image")
    public ResponseEntity<IdealDto> updateImage(@RequestParam String filePath,
                                                @RequestParam Long idealId,
                                                @RequestParam Long selectionId,
                                                @RequestParam MultipartFile file, HttpServletRequest request){
        return new ResponseEntity<>(idealService.updateImage(filePath, idealId, selectionId, file, request), HttpStatus.OK);
    }

    @PutMapping("/{filePath}")
    public ResponseEntity editIdealName(@PathVariable String filePath, @RequestBody IdealDto idealDto, HttpServletRequest request){
        idealService.editIdealName(filePath, idealDto, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteIdeal(@PathVariable Long id, HttpServletRequest request){
        idealService.delete(id, request);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity addIdeal(@RequestParam MultipartFile file, @RequestParam Long selectionId, HttpServletRequest request){
        idealService.add(file, selectionId, request);
        return new ResponseEntity(HttpStatus.OK);
    }

}
