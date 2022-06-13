package com.epam.esm.controller;

import com.epam.esm.model.enums.SortType;
import com.epam.esm.model.request.CreateGiftCertificateRequest;
import com.epam.esm.service.GiftCertificateService;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/gift-certificate")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GiftCertificateController {

    @NonNull GiftCertificateService giftCertificateService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CreateGiftCertificateRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(giftCertificateService.create(request));
    }

    @GetMapping("/get-by-name")
    public ResponseEntity<?> getByName(@RequestParam String name) {
        return ResponseEntity.ok(giftCertificateService.getByName(name));
    }

    @GetMapping("/get-by-tag")
    public ResponseEntity<?> getByTag(@RequestParam String tag,
                                      @RequestParam(defaultValue = "5", required = false) Integer quantity,
                                      @RequestParam(defaultValue = "1", required = false) Integer page,
                                      @RequestParam(defaultValue = "NONE", required = false) SortType sortType) {
        return ResponseEntity.ok(giftCertificateService.getByTag(tag, quantity, page, sortType));
    }

}
