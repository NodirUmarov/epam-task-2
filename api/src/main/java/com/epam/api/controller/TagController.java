package com.epam.api.controller;

import com.epam.business.model.request.CreateTagRequest;
import com.epam.business.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@Api(produces = "application/json", consumes = "application/json")
@RequestMapping("/api/v1/tag")
public class TagController {

    private final TagService tagService;

    @GetMapping
    @ApiOperation(value = "Get tags", notes = "This method retrieves all tags in given range.")
    public ResponseEntity<?> getTags(@RequestParam(defaultValue = "5", required = false) Integer quantity,
                                    @RequestParam(defaultValue = "1", required = false) Integer page) {
        return ResponseEntity
                .ok(tagService.getAllTags(quantity, page));
    }

    @PostMapping
    @ApiOperation(value = "Create tags", notes = "This method creates all tags provided and returns them with their IDs as response")
    public ResponseEntity<?> create(@RequestBody Set<CreateTagRequest> createTagRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(tagService.create(createTagRequest));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete by ID", notes = "This method deletes single tag by its ID")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        tagService.deleteById(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}