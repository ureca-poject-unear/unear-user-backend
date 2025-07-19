package com.unear.userservice.exception.exception;

import com.unear.userservice.exception.BusinessException;
import com.unear.userservice.exception.ErrorCode;

public class UnauthorizedException extends BusinessException {
  public UnauthorizedException() {
    super(ErrorCode.UNAUTHORIZED_USER);
  }

  public UnauthorizedException(String message) {
    super(ErrorCode.UNAUTHORIZED_USER, message);
  }
}