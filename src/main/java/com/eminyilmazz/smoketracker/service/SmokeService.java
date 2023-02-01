package com.eminyilmazz.smoketracker.service;

import com.eminyilmazz.smoketracker.dto.RequestSmokeDto;
import com.eminyilmazz.smoketracker.dto.RequestStatDto;

public interface SmokeService {
    void addSmoke(RequestSmokeDto smokeDto);
    String getAll(RequestStatDto statDto);
}
