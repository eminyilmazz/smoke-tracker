package com.eminyilmazz.smoketracker.utility.mapper;

import com.eminyilmazz.smoketracker.dto.RequestSmokeDto;
import com.eminyilmazz.smoketracker.entity.Smoke;

import static com.eminyilmazz.smoketracker.utility.UtilityService.*;

public class SmokeMapper {
    private SmokeMapper() {
    }

    public static RequestSmokeDto toDto(Smoke entity) {
        return RequestSmokeDto.builder()
                .smokedDate(getDate(entity.getSmokedDate().toLocalDate()))
                .smokedTime(getTime(entity.getSmokedDate().toLocalTime()))
                .activity(entity.getActivity())
                .quantity(entity.getQuantity())
                .build();
    }
    public static Smoke toEntity(RequestSmokeDto dto) {
        return Smoke.builder()
                .smokedDate(toLocalDateTime(dto.getSmokedDate(), dto.getSmokedTime()))
                .activity(dto.getActivity())
                .quantity(dto.getQuantity())
                .build();
    }
}
