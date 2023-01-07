package com.eminyilmazz.smoketracker.controller;

import com.eminyilmazz.smoketracker.dto.RequestSmokeDto;
import com.eminyilmazz.smoketracker.service.SmokeService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;

//TODO: Replace ResponseEntities with DeferredResults. Needs Research.

@RestController
@RequestMapping("/smoke")
public class SmokeController {
    private static final Logger logger = LoggerFactory.getLogger(SmokeController.class);
    @Autowired
    SmokeService smokeService;

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> addSmoke(@Valid @RequestBody RequestSmokeDto smokeDto) {
        Instant start = Instant.now();
        logger.trace("/smoke/add request received.");
        smokeService.addSmoke(smokeDto);
        logger.trace("Completed /smoke/add in {}ms", Duration.between(start, Instant.now()).toMillis());
        return ResponseEntity.status(HttpStatus.CREATED).body("Congratulations loser. I hope you enjoyed that smoke.");
    }
}
