package com.epam.api.controller;

import com.epam.business.model.request.CreateGiftCertificateRequest;
import com.epam.business.model.request.UpdateGiftCertificateRequest;
import com.epam.business.service.GiftCertificateService;
import com.epam.lib.constants.SortType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/gift-certificate")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    @GetMapping(value = "/", params = "name")
    public ResponseEntity<?> getByName(@RequestParam String name) {
        return ResponseEntity
                .ok(giftCertificateService
                        .getByName(name));
    }

    @GetMapping(value = "/", params = "{tag, quantity, page, sortType}")
    public ResponseEntity<?> getByTag(@RequestParam String tag,
                                      @RequestParam(defaultValue = "5", required = false) Integer quantity,
                                      @RequestParam(defaultValue = "1", required = false) Integer page,
                                      @RequestParam(defaultValue = "NONE", required = false) SortType sortType) {
        return ResponseEntity
                .ok(giftCertificateService
                        .getByTag(tag, quantity, page, sortType));
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody CreateGiftCertificateRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(giftCertificateService
                        .create(request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody @Validated UpdateGiftCertificateRequest request) {
        return ResponseEntity
                .accepted()
                .body(giftCertificateService
                        .updateById(id, request));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        giftCertificateService.deleteById(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}