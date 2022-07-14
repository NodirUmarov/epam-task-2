package com.epam.api.controller;

import com.epam.business.model.request.CreateGiftCertificateRequest;
import com.epam.business.model.request.UpdateGiftCertificateRequest;
import com.epam.business.service.GiftCertificateService;
import com.epam.lib.constants.SortType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@Api(produces = "application/json", consumes = "application/json")
@RequestMapping("/api/v1/gift-certificate")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    @GetMapping(params = {"name"})
    @ApiOperation(value = "Get By Name", notes = "This method retrieves single gift-certificate by its name")
    public ResponseEntity<?> getByName(@RequestParam String name) {
        return ResponseEntity
                .ok(giftCertificateService
                        .getByName(name));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get By ID", notes = "This method retrieves single gift-certificate by its ID")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity
                .ok(giftCertificateService
                        .getById(id));
    }

    @GetMapping(params = {"tag"})
    @ApiOperation(value = "Get By Tag", notes = "This method retrieves all gift-certificates by single tag. Pagination and order can be configured.")
    public ResponseEntity<?> getByTag(@RequestParam String tag,
                                      @RequestParam(defaultValue = "5", required = false) Integer quantity,
                                      @RequestParam(defaultValue = "1", required = false) Integer page,
                                      @RequestParam(defaultValue = "NONE", required = false) SortType sortType) {
        return ResponseEntity
                .ok(giftCertificateService
                        .getByTag(tag, quantity, page, sortType));
    }

    @PostMapping
    @ApiOperation(value = "Create gift-certificate",
            notes = "This method creates gift-certificate with tags. If tag does not exists, it will be created automatically. " +
                    "Created gift-certificate will be returned as response.")
    public ResponseEntity<?> create(@RequestBody CreateGiftCertificateRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(giftCertificateService
                        .create(request));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update gift-certificate", notes = "This method updates only changed fields.")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody @Validated UpdateGiftCertificateRequest request) {
        return ResponseEntity
                .accepted()
                .body(giftCertificateService
                        .updateById(id, request));
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Untag gift-certificate", notes = "This method removes given tags from gift-certificate.")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody @Validated Set<String> tags) {
        return ResponseEntity
                .accepted()
                .body(giftCertificateService
                        .untag(id, tags));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete by ID", notes = "This method deletes gift-certificate by its ID")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        giftCertificateService.deleteById(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}