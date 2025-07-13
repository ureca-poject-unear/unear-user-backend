package com.unear.userservice.exception.exception;


import com.unear.userservice.exception.BusinessException;
import com.unear.userservice.exception.ErrorCode;

public class DuplicatedEmailException extends BusinessException {
  public DuplicatedEmailException() {
    super(ErrorCode.DUPLICATED_EMAIL);
  }
  public DuplicatedEmailException(String message) {
    super(ErrorCode.DUPLICATED_EMAIL, message);
  }
}

