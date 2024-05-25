package software.btech.learnmate.framework.test_support.test_containers;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.opensearch.client.Request;
import org.opensearch.client.RestClient;
import org.springframework.util.StreamUtils;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.HttpWaitStrategy;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Objects;

@Slf4j
public class SearchContainerExtension implements AfterAllCallback, BeforeAllCallback {

  private static final String IMAGE_NAME = "opensearchproject/opensearch:latest";

  private final GenericContainer<?> container;

  private final RestClient client;

  private static SearchContainerExtension singleton;

  public static synchronized SearchContainerExtension getInstance(
    int hostPort
  ) {
    if (Objects.isNull(singleton)) {
      singleton = new SearchContainerExtension(hostPort);
    }
    return singleton;
  }

  protected SearchContainerExtension(
    int hostPort
  ) {

    // container setup
    container = new FixedHostPortGenericContainer<>(IMAGE_NAME) // NOSONAR
      .withExposedPorts(9200)
      .withFixedExposedPort(hostPort, 9200)
      .withLogConsumer(new Slf4jLogConsumer(log))
      .withEnv("transport.host", "localhost")
      .withEnv("network.host", "0.0.0.0")
      .withEnv("discovery.type", "single-node")
      .withEnv("plugins.security.disabled", "true")
      .withReuse(true);

    client = RestClient.builder(HttpHost.create(String.format("localhost:%s", 19200))).build();

  }

  public void index(String indexName, String id, String document) throws IOException, InterruptedException {
    var request = new Request("POST", String.format("/%s/_doc/%s", indexName, id));
    log.info("Indexing: {}", document);
    request.setJsonEntity(document);
    var response = client.performRequest(request);
    log.info("Index response:{}", response);
    Thread.sleep(1000L);
  }

  public void setupIndex(String indexName, String mappingClasspathLocation) throws InterruptedException, IOException {
    var request = new Request("PUT", String.format("/%s", indexName));
    var mappingResource =
      Objects.requireNonNull(
        getClass().getClassLoader().getResourceAsStream(mappingClasspathLocation)
      );
    request.setJsonEntity(StreamUtils.copyToString(mappingResource, StandardCharsets.UTF_8));
    var response = client.performRequest(request);
    log.info("Index mapping response:{}", response);
  }


  public void deleteAll() throws IOException {
    var response = client.performRequest(new Request("DELETE", "/_all"));
    log.info("Delete all response: {}", response.toString());
  }

  public void afterAll(ExtensionContext extensionContext) throws Exception {
    if (container.isRunning()) {
      container.stop();
    }
  }

  public void beforeAll(ExtensionContext extensionContext) throws Exception {
    if (!container.isRunning()) {

      container.setWaitStrategy(new HttpWaitStrategy()
        .forPort(9200)
        .forStatusCodeMatching(response -> response == 200)
        .withStartupTimeout(Duration.ofMinutes(1)));

      container.start();
    }
  }

}


