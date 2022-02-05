package com.fieldwire.service.exception;

public class ImageException extends RuntimeException {

  public ImageException(String message) {
    super(message);
  }

  public static ImageException create(String message){
    return new ImageException(message);
  }
}
