package com.eminyilmazz.smoketracker.repository;

import com.eminyilmazz.smoketracker.entity.Smoke;
import com.eminyilmazz.smoketracker.enums.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SmokeRepository extends JpaRepository<Smoke, Long> {

    @Query(value = "select * from smoke s where s.smoked_date >= NOW() - :minutesAgo * INTERVAL '1 minute'", nativeQuery = true)
    Optional<List<Smoke>> getStatWithMinuteInterval(@Param("minutesAgo") Long minutesAgo);

    @Query(value = "select * from smoke s where s.smoked_date >= NOW() - :minutesAgo * INTERVAL '1 minute' and s.activity = :activity", nativeQuery = true)
    Optional<List<Smoke>> getStatByActivityAndWithMinuteInterval(@Param("activity") String activity, @Param("minutesAgo") Long minutesAgo);
}
