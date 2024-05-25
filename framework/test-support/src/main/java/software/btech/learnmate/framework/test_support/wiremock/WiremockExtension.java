package software.btech.learnmate.framework.test_support.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.List;
import java.util.Objects;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

public class WiremockExtension
  implements BeforeAllCallback, AfterAllCallback {

  private WireMockServer server;

  private final int hostPort;

  private final List<WiremockStub> stubs;

  private static WiremockExtension singleton;

  public static synchronized WiremockExtension getInstance(
    int hostPort,
    List<WiremockStub> stubs
  ) {
    if (Objects.isNull(singleton)) {
      singleton = new WiremockExtension(hostPort, stubs);
    }
    return singleton;
  }

  protected WiremockExtension(
    int hostPort,
    List<WiremockStub> stubs
  ) {
    this.hostPort = hostPort;
    this.stubs = stubs;
  }

  public void start() {
    stubs.forEach(stub -> stub.start(server));
  }

  @Override
  public void afterAll(ExtensionContext extensionContext) throws Exception {
    server.stop();
  }

  @Override
  public void beforeAll(ExtensionContext extensionContext) throws Exception {

    server = new WireMockServer(
      options().port(this.hostPort)
    );

    server.start();

    extensionContext
      .getRoot()
      .getStore(GLOBAL)
      .put("wiremock", this);

  }
}
