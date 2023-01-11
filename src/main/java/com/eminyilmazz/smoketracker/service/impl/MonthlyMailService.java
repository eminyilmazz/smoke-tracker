package com.eminyilmazz.smoketracker.service.impl;

import com.eminyilmazz.smoketracker.enums.Activity;
import com.eminyilmazz.smoketracker.repository.SmokeRepository;
import com.eminyilmazz.smoketracker.service.MailService;
import com.hp.gagawa.java.Document;
import com.hp.gagawa.java.DocumentType;
import com.hp.gagawa.java.elements.*;
import jakarta.mail.internet.MimeMessage;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class MonthlyMailService extends MailServiceImpl implements MailService {

    private static final Logger logger = LoggerFactory.getLogger(MonthlyMailService.class);
    @Autowired
    SmokeRepository smokeRepository;
    @Value("${stat.service.mail.from:john@gmail.com}")
    String _from;
    @Value("${stat.service.mail.to:bob@gmail.com}")
    String _to;
    final DateTimeFormatter completeDateTime = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Scheduled(cron = "${monthly.mail.cron:0 0 9 1 */1 *}")
    @Override
    public void sendScheduledMail() {
        logger.trace("Preparing monthly mail content");
        LocalDateTime beginDate = LocalDateTime.now().minusMonths(1);
        LocalDateTime endDate = LocalDateTime.now();
        Integer totalCount = smokeRepository.getNumberOfTotalQuantity(beginDate, endDate);
        List<ImmutablePair<Activity, Long>> list = smokeRepository.getTotalQuantityGroupedByActivityWithMinuteInterval(beginDate, endDate);
        String content = prepareMailContent(beginDate, endDate, list, totalCount);
        String subject = String.format("Monthly smoke tracker notification - %s", endDate.format(dateFormat));
        logger.debug("Monthly email sent status: {}", sendMail(subject, content));
    }

    public boolean sendMail(String subject, String content) {
        logger.trace("Sending monthly mail");
        return super.sendMail(subject, content, _to, _from);
    }

    @Override
    public String prepareMailContent(LocalDateTime beginDate, LocalDateTime endDate, List<ImmutablePair<Activity, Long>> list, Integer totalCount) {
        Document document = new Document(DocumentType.XHTMLTransitional);
        P p = new P().appendText(String.format("Calculated monthly activity based statistic between %s and %s", beginDate.format(completeDateTime), endDate.format(completeDateTime)));
        document.head.appendChild(new Title().appendChild(new Text(String.format("%s", beginDate.format(dateFormat)))));
        document.body.appendChild(p);
        Table table = this.getHTMLTable(list);
        table.getChild(0);
        document.body.appendChild(table);
        document.body.appendChild(new P().appendText(String.format("Total smoked amount: %d", totalCount)))
                .appendChild(new P().appendText(String.format("The activity you smoked the most during: %s", getMostSmokedActivity(list))));
        return document.write();
    }

    private String getMostSmokedActivity(List<ImmutablePair<Activity, Long>> list) {
        if (list.isEmpty()) return "";
        return list.stream().max(Comparator.comparing(ImmutablePair::getRight)).get().getLeft().getValue();
    }
}
