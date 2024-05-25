package software.btech.learnmate.framework.foundation.tracing;

import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TraceIdService implements ITraceIdService {

  private final Tracer tracer;

  public String getTraceId() {
    var currentSpan = tracer.currentSpan();
    if (Objects.nonNull(currentSpan) &&
      Objects.nonNull(currentSpan.context())) {
      return currentSpan.context().traceId();
    } else {
      return "";
    }
  }

}
