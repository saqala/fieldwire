package com.fieldwire.service.image.exception;

import java.io.IOException;
import java.io.UncheckedIOException;

public class ImageException extends UncheckedIOException {

  public static final String RETRIEVE_FILE_EXCEPTION = "error retrieve file check file path";
  public static final String DELETE_FILE_EXCEPTION = "error delete file check file path";

  public ImageException(String message, IOException cause) {
    super(message, cause);
  }

  public static ImageException create(String message, IOException cause){
    return new ImageException(message, cause);
  }
}
