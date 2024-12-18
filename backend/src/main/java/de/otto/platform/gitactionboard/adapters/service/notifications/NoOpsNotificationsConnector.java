package de.otto.platform.gitactionboard.adapters.service.notifications;

import de.otto.platform.gitactionboard.domain.JobDetails;
import de.otto.platform.gitactionboard.domain.service.NotificationConnector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnMissingBean(TeamsWebHookNotificationConnector.class)
@Profile("local")
public class NoOpsNotificationsConnector implements NotificationConnector {
  @Override
  public void notify(JobDetails jobDetails) {
    log.info("Sent notification for {}", jobDetails);
  }

  @Override
  public String getType() {
    return "NO_OPS";
  }
}
