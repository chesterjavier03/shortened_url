package com.project.utility;

import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.UrlValidator;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * Created by chesterjavier.
 */
@Component
@Slf4j
public class ShortenedUrlProcessorUtility {

  public final static String convertToShortenedUrl(final String url) {
    final UrlValidator validator = new UrlValidator(new String[]{"http", "https"});
    String shortenedUrl = null;
    if (validator.isValid(url)) {
      shortenedUrl = Hashing.murmur3_32().hashString(url, StandardCharsets.UTF_8).toString();
    }
    return shortenedUrl;
  }
}
