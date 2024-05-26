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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import software.btech.learnmate.framework.foundation.tracing.TraceIdService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicationExceptionHandlerTest {

  @Mock
  private TraceIdService traceIdService;

  @InjectMocks
  private ApplicationExceptionHandler underTest;

  @Test
  void testHandleWebApplicationException() {
    // given
    var expectedTraceId = "trace-id";
    when(traceIdService.getTraceId()).thenReturn(expectedTraceId);

    var message = "message";
    var status = HttpStatus.I_AM_A_TEAPOT;
    var exception = new WebApplicationException(message, status);

    // when
    var responseEntity = underTest.handleWebApplicationException(exception);

    // then
    assertNotNull(responseEntity);

  }

}
