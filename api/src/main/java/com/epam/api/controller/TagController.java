package com.epam.api.controller;

import com.epam.business.model.request.CreateTagRequest;
import com.epam.business.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tag")
public class TagController {

    private final TagService tagService;

    @GetMapping("/")
    public ResponseEntity<?> getTag(@RequestParam Integer quantity,
                                    @RequestParam Integer page) {
        return ResponseEntity
                .ok(tagService.getAllTags(quantity, page));
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody Set<CreateTagRequest> createTagRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(tagService.create(createTagRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        tagService.deleteById(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}