package com.project.utility;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by chesterjavier.
 */
@Component
@Slf4j
public class FileProcessorUtility {

  public final static File convertFileToRequiredType(final MultipartFile multipartFile) {
    log.info("[FileProcessorUtility] Start conversion of MultipartFile to File....");
    File newFile = new File(multipartFile.getOriginalFilename());
    try {
      newFile.createNewFile();
      FileOutputStream fos = new FileOutputStream(newFile);
      fos.write(multipartFile.getBytes());
      fos.close();
    } catch (IOException e) {
      log.error("ERROR [{}]", e.getLocalizedMessage());
    }
    return newFile;
  }
}
