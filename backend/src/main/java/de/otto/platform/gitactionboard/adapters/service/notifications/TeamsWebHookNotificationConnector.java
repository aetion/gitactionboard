package de.otto.platform.gitactionboard.adapters.service.notifications;

import de.otto.platform.gitactionboard.domain.JobDetails;
import de.otto.platform.gitactionboard.domain.service.NotificationConnector;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
@ConditionalOnProperty("MS_TEAMS_NOTIFICATIONS_WEB_HOOK_URL")
@SuppressFBWarnings("EI_EXPOSE_REP2")
public class TeamsWebHookNotificationConnector implements NotificationConnector {
  private static final String CONNECTOR_TYPE = "MS_TEAMS_WEB_HOOK";
  private final RestTemplate restTemplate;
  private final String webHookUrl;

  @Autowired
  public TeamsWebHookNotificationConnector(
      RestTemplateBuilder restTemplateBuilder,
      @Value("${MS_TEAMS_NOTIFICATIONS_WEB_HOOK_URL}") String webHookUrl) {
    this.restTemplate = restTemplateBuilder.build();
    this.webHookUrl = webHookUrl;
  }

  @Override
  public void notify(JobDetails jobDetails) {
    log.info("Sending notification for {}", jobDetails);

    final ResponseEntity<String> responseEntity =
        restTemplate.postForEntity(
            webHookUrl, TeamsNotificationMessagePayload.from(jobDetails), String.class);

    if (!responseEntity.getStatusCode().is2xxSuccessful())
      throw new RuntimeException(responseEntity.getBody());
  }

  @Override
  public String getType() {
    return CONNECTOR_TYPE;
  }
}
