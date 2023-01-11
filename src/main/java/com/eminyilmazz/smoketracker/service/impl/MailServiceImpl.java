package com.eminyilmazz.smoketracker.service.impl;

import com.eminyilmazz.smoketracker.enums.Activity;
import com.eminyilmazz.smoketracker.service.MailService;
import com.hp.gagawa.java.elements.Table;
import com.hp.gagawa.java.elements.Td;
import com.hp.gagawa.java.elements.Th;
import com.hp.gagawa.java.elements.Tr;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public abstract class MailServiceImpl implements MailService {
    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
    @Autowired
    JavaMailSender javaMailSender;

    boolean sendMail(String subject, String content, String _to, String _from) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

        try {
            helper.setText(content, true);
            helper.setTo(_to);
            helper.setFrom(_from);
            helper.setSubject(subject);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            logger.error("Error sending mail");
            logger.error(ExceptionUtils.getStackTrace(e));
            return false;
        }
        return true;
    }

    protected Table getHTMLTable(List<ImmutablePair<Activity, Long>> list) {
        if (list.isEmpty()) return null;
        AtomicReference<Table> table = new AtomicReference<>(new Table().setBorder("1").setCellpadding("1").setCellspacing("1").setStyle("width:250px"));
        AtomicReference<Tr> tr = new AtomicReference<>(new Tr());
        AtomicReference<Td> td = new AtomicReference<>(new Td());
        AtomicReference<Td> td2 = new AtomicReference<>(new Td());
        table.get().appendChild(new Th().appendText("Activity")).appendChild(new Th().appendText("Quantity"));
        list.forEach(immutablePair -> {
            td.set(new Td());
            td2.set(new Td());
            tr.set(new Tr());
            td.get().appendText(immutablePair.left.name());
            td2.get().appendText(String.valueOf(immutablePair.right));
            tr.get().appendChild(td.get(), td2.get());
            table.get().appendChild(tr.get());
        });
        return table.get();
    }
}
