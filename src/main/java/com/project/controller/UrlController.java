package com.project.controller;

import com.project.service.UrlService;
import com.project.utility.FileProcessorUtility;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Created by chesterjavier.
 */
@RestController
@RequestMapping(path = "/api/v1")
@Slf4j
public class UrlController {

  @Autowired
  private UrlService urlService;

  @PostMapping(path = "/process/urls", consumes = {"multipart/form-data"}, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity convertUrlListToShortenedUrls(@RequestPart("file") @Valid @NotNull @NotBlank MultipartFile file, HttpServletRequest request) {
    try {
      log.info("[UrlController] - Uploading text file with list of URLS to convert to shortened URLS....");
      FileUtils.readLines(FileProcessorUtility.convertFileToRequiredType(file), StandardCharsets.UTF_8).stream().forEach(result -> urlService.processUrl(result, request));
      return new ResponseEntity(new JSONObject().put("json", "Successfully added URLs into the database"), HttpStatus.CREATED);
    } catch (Exception e) {
      log.error("[BookController] ERROR [{}]", e.getLocalizedMessage());
      return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping(path = "/convert", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity shortenedUrl(@RequestParam("url") String url, HttpServletRequest request) {
    log.info("[UrlController] - Converting URL to shortened URL [{}]", url);
    return Optional.ofNullable(urlService.processUrl(url, request)).map(result -> new ResponseEntity(result, HttpStatus.OK))
        .orElse(new ResponseEntity("No data found", HttpStatus.NO_CONTENT));
  }

  @GetMapping(path = "/fetch/urls", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity fetchUrl() {
    log.info("[UrlController] - Fetching all URL from database...");
    return Optional.ofNullable(urlService.fetchAllUrls()).map(result -> new ResponseEntity(result, HttpStatus.OK))
        .orElse(new ResponseEntity("No data found", HttpStatus.NO_CONTENT));
  }
}
