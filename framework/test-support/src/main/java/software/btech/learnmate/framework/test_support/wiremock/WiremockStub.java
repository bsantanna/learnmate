package software.btech.learnmate.framework.test_support.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;

public interface WiremockStub {
  void start(WireMockServer server);

}
