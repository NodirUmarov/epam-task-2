package com.epam.esm.controller;

import com.epam.esm.model.request.CreateTagRequest;
import com.epam.esm.service.TagService;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tag")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TagController {

    @NonNull TagService tagService;

    @GetMapping("/get-tags")
    public ResponseEntity<?> getTag(@RequestParam Integer quantity,
                                    @RequestParam Integer page) {
        return ResponseEntity
                .ok(tagService.getAllTags(quantity, page));
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Set<CreateTagRequest> createTagRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(tagService.create(createTagRequest));
    }

    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        tagService.deleteById(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
