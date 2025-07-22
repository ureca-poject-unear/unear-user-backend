package com.unear.userservice.common.exception.exception;


import com.unear.userservice.common.exception.BusinessException;
import com.unear.userservice.common.exception.ErrorCode;

public class DuplicatedEmailException extends BusinessException {
  public DuplicatedEmailException() {
    super(ErrorCode.DUPLICATED_EMAIL);
  }
  public DuplicatedEmailException(String message) {
    super(ErrorCode.DUPLICATED_EMAIL, message);
  }
}

