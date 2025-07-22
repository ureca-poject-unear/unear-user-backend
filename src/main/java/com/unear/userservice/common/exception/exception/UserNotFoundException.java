package com.unear.userservice.common.exception.exception;

import com.unear.userservice.common.exception.BusinessException;
import com.unear.userservice.common.exception.ErrorCode;

public class UserNotFoundException extends BusinessException {
  public UserNotFoundException() {
    super(ErrorCode.USER_NOT_FOUND);
  }

  public UserNotFoundException(String message) {
    super(ErrorCode.USER_NOT_FOUND, message);
  }
}
