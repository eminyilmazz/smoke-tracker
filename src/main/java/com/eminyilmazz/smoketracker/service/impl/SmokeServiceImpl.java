package com.eminyilmazz.smoketracker.service.impl;

import com.eminyilmazz.smoketracker.dto.RequestSmokeDto;
import com.eminyilmazz.smoketracker.dto.RequestStatDto;
import com.eminyilmazz.smoketracker.entity.Smoke;
import com.eminyilmazz.smoketracker.enums.Activity;
import com.eminyilmazz.smoketracker.repository.SmokeRepository;
import com.eminyilmazz.smoketracker.service.SmokeService;
import com.eminyilmazz.smoketracker.utility.UtilityService;
import com.eminyilmazz.smoketracker.utility.mapper.SmokeMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.eminyilmazz.smoketracker.utility.UtilityService.*;
import static com.eminyilmazz.smoketracker.utility.mapper.SmokeMapper.toEntity;

//TODO: Make everything asynchronous. Needs research.

@Service
public class SmokeServiceImpl implements SmokeService {
    @Autowired
    SmokeRepository smokeRepository;
    ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(SmokeServiceImpl.class);

    @Override
    public void addSmoke(RequestSmokeDto smokeDto) {
        if (!UtilityService.dateTimeExists(smokeDto)) {
            smokeDto.setSmokedDate(getDate());
            smokeDto.setSmokedTime(getTime());
        }
        Smoke smoke = toEntity(smokeDto);
        smokeRepository.saveAndFlush(smoke);
    }

    @Override
    public String getAll(RequestStatDto statDto) {
        Optional<List<Smoke>> optionalList;
        List<RequestSmokeDto> dtoList;
        Long m = transformTimeInterval(statDto.getHours());
        if(StringUtils.isEmpty(statDto.getActivity())) {
            optionalList = smokeRepository.getStatWithMinuteInterval(m);
        } else {
            Activity a = Activity.fromValue(statDto.getActivity());
            optionalList = smokeRepository.getStatByActivityAndWithMinuteInterval(a.name().toUpperCase(), m);
        }
        if (optionalList.isPresent()) {
            dtoList = optionalList.get().stream().map(SmokeMapper::toDto).toList();
            Map<String, Integer> responseMap = fillStatResponse(dtoList);
            try {
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseMap);
            } catch (JsonProcessingException e) {
                logger.error("Error occurred while parsing response to String!");
                ExceptionUtils.printRootCauseStackTrace(e);
            }
        }
        return null;
    }
}
