package com.eminyilmazz.smoketracker.service.impl;

import com.eminyilmazz.smoketracker.dto.RequestSmokeDto;
import com.eminyilmazz.smoketracker.entity.Smoke;
import com.eminyilmazz.smoketracker.enums.Activity;
import com.eminyilmazz.smoketracker.repository.SmokeRepository;
import com.eminyilmazz.smoketracker.service.SmokeService;
import com.eminyilmazz.smoketracker.utility.UtilityService;
import com.eminyilmazz.smoketracker.utility.mapper.SmokeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.eminyilmazz.smoketracker.utility.UtilityService.getDate;
import static com.eminyilmazz.smoketracker.utility.UtilityService.getTime;
import static com.eminyilmazz.smoketracker.utility.mapper.SmokeMapper.toEntity;

//TODO: Make everything asynchronous. Needs research.

@Service
public class SmokeServiceImpl implements SmokeService {
    @Autowired
    SmokeRepository smokeRepository;

    @Override
    public void addSmoke(RequestSmokeDto smokeDto) {
        if (!UtilityService.dateTimeExists(smokeDto)) {
            smokeDto.setSmokedDate(getDate());
            smokeDto.setSmokedTime(getTime());
        }
        Smoke smoke = toEntity(smokeDto);
        smokeRepository.saveAndFlush(smoke);
    }
}
