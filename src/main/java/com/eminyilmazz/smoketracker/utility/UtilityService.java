package com.eminyilmazz.smoketracker.utility;


import com.eminyilmazz.smoketracker.dto.RequestSmokeDto;
import com.eminyilmazz.smoketracker.dto.RequestStatDto;
import com.eminyilmazz.smoketracker.enums.Activity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.eminyilmazz.smoketracker.enums.Activity.fromValue;
import static java.lang.Long.parseLong;

public class UtilityService {
    private UtilityService() {

    }
    private static final String TIME_FORMAT = "HHmmss";
    private static final String DATE_FORMAT = "yyMMdd";
    private static final Logger logger = LoggerFactory.getLogger(UtilityService.class);

    public static LocalDateTime toLocalDateTime(Long d, Long t) {
        LocalTime localTime = formatTime(t);
        LocalDate localDate = formatDate(d);
        return LocalDateTime.of(localDate, localTime);
    }

    private static LocalTime formatTime(Long t) {
        String paddedTime = pad(t);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(TIME_FORMAT);
        return LocalTime.from(dtf.parse(paddedTime));
    }

    private static LocalDate formatDate(Long d) {
        String paddedTime = pad(d);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return LocalDate.from(dtf.parse(paddedTime));
    }

    private static String pad(Long l) {
        return StringUtils.leftPad(String.valueOf(l), 6, '0');
    }

    public static Long getTime() {
        return parseLong(LocalTime.now().format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
    }

    public static Long getDate() {
        return parseLong(LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
    }
    public static Long getTime(LocalTime o) {
        return parseLong(o.format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
    }

    public static Long getDate(LocalDate o) {
        return parseLong(o.format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
    }

    public static boolean dateTimeExists(RequestSmokeDto o) {
        return StringUtils.isEmpty(String.valueOf(o.getSmokedDate())) && StringUtils.isEmpty(String.valueOf(o.getSmokedTime()));
    }

    public static String getActivityValue(String s) {
        return fromValue(s).getValue();
    }

    public static Long transformTimeInterval(double h) {
        return (long) Math.floor(h * 60);
    }

    public static Map<String, Integer> fillStatResponse(List<RequestSmokeDto> dtoList) {
        Map<String, Integer> stat = new HashMap<>();
        Integer quantity = dtoList.stream().mapToInt(i -> i.getQuantity()).sum();
        stat.put("total", quantity);

        for (RequestSmokeDto var : dtoList) {
            stat.computeIfPresent(var.getActivity(), (key, value) -> value + 1);
            stat.putIfAbsent(var.getActivity(), 1);
        }
        return stat;
    }
}
