package com.eminyilmazz.smoketracker.service;

import com.eminyilmazz.smoketracker.dto.RequestSmokeDto;

public interface SmokeService {
    void addSmoke(RequestSmokeDto smokeDto);
}
