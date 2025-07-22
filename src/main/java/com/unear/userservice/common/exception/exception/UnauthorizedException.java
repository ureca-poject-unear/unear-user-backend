package com.unear.userservice.common.exception.exception;

import com.unear.userservice.common.exception.BusinessException;
import com.unear.userservice.common.exception.ErrorCode;

public class UnauthorizedException extends BusinessException {
  public UnauthorizedException() {
    super(ErrorCode.UNAUTHORIZED_USER);
  }

  public UnauthorizedException(String message) {
    super(ErrorCode.UNAUTHORIZED_USER, message);
  }
}