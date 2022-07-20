package com.security.basic.mapped;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mapped")
public class Controller {

    private final BaseItemRepository baseItemRepository;

    @GetMapping("/a")
    public ResponseEntity<?> a() {
        return ResponseEntity.ok(baseItemRepository.itemAList());
    }

    @GetMapping("/b")
    public ResponseEntity<?> b() {
        return ResponseEntity.ok(baseItemRepository.itemBList());
    }
}
