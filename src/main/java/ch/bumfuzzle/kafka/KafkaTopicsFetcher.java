package ch.bumfuzzle.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.Set;

@Slf4j
@Component
public class KafkaTopicsFetcher {

  private KafkaTopicsFetcher() {
  }

  @Value("${spring.kafka.bootstrap-servers}")
  private String kafkaBootstrapServer;

  private Set<String> fetchTopics() {
    try {

      Properties properties = new Properties();
      properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServer);

      try (AdminClient adminClient = AdminClient.create(properties)) {

        ListTopicsOptions listTopicsOptions = new ListTopicsOptions();
        listTopicsOptions.listInternal(true);

        log.info("Found Topics: {}", adminClient.listTopics(listTopicsOptions).names().get());
        return adminClient.listTopics().names().get();
      }
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return Set.of();
  }

  public String[] getTopics() {
    return fetchTopics().toArray(new String[0]);
  }
}
