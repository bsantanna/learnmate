/*
    LearnMate - AI Learning Assistant
    Copyright (C) 2024 - LearnMate Developers

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses />.
 */

package software.btech.learnmate.framework.foundation.tracing.error;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import software.btech.learnmate.framework.foundation.tracing.ITraceIdService;
import software.btech.learnmate.schema.api.ErrorResponse;

@ControllerAdvice
@RequiredArgsConstructor
public class ApplicationExceptionHandler
  extends ResponseEntityExceptionHandler {

  private final ITraceIdService traceIdService;

  @ExceptionHandler(WebApplicationException.class)
  public ResponseEntity<String> handleWebApplicationException(WebApplicationException exception) {

    var errorResponse =
      ErrorResponse.newBuilder()
        .setDescription(exception.getMessage())
        .setTraceId(traceIdService.getTraceId())
        .build().toString();

    return ResponseEntity
      .status(exception.getHttpStatus().value())
      .body(errorResponse);
  }

}
