package com.eminyilmazz.smoketracker.service;

import com.eminyilmazz.smoketracker.enums.Activity;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.time.LocalDateTime;
import java.util.List;

public interface MailService {
    void sendScheduledMail();

    String prepareMailContent(LocalDateTime beginDate, LocalDateTime endDate, List<ImmutablePair<Activity, Long>> list, Integer totalCount);
}
