package software.btech.learnmate.framework.foundation.tracing;

import io.micrometer.tracing.Tracer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraceIdServiceTest {

  @Mock(answer = RETURNS_DEEP_STUBS)
  private static Tracer tracer;

  @InjectMocks
  private TraceIdService underTest;

  @Test
  void testGetTraceId() {
    // Given
    var expectedTraceId = "test-trace-id";
    when(tracer.currentSpan().context().traceId()).thenReturn(expectedTraceId);

    // When
    var resultTraceId = underTest.getTraceId();

    // Then
    assertEquals(expectedTraceId, resultTraceId);
  }

}
