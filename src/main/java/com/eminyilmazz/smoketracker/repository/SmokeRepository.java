package com.eminyilmazz.smoketracker.repository;

import com.eminyilmazz.smoketracker.dto.ActivityBasedQuantity;
import com.eminyilmazz.smoketracker.entity.Smoke;
import com.eminyilmazz.smoketracker.enums.Activity;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SmokeRepository extends JpaRepository<Smoke, Long> {

    @Query(value = "select * from smoke s where s.smoked_date >= NOW() - :minutesAgo * INTERVAL '1 minute'", nativeQuery = true)
    Optional<List<Smoke>> getStatWithMinuteInterval(@Param("minutesAgo") Long minutesAgo);

    @Query(value = "select * from smoke s where s.smoked_date >= NOW() - :minutesAgo * INTERVAL '1 minute' and s.activity = :activity", nativeQuery = true)
    Optional<List<Smoke>> getStatByActivityAndWithMinuteInterval(@Param("activity") String activity, @Param("minutesAgo") Long minutesAgo);

    @Query(value = "SELECT COALESCE(SUM(s.quantity),0) FROM smoke s WHERE s.smoked_date BETWEEN :beginDate AND :endDate", nativeQuery = true)
    int getNumberOfTotalQuantity(@Param("beginDate")LocalDateTime beginDate, @Param("endDate") LocalDateTime endDate);
    @Query("SELECT new org.apache.commons.lang3.tuple.ImmutablePair(s.activity, SUM(s.quantity)) FROM Smoke s WHERE s.smokedDate BETWEEN :beginDate AND :endDate GROUP BY s.activity")
    List<ImmutablePair<Activity, Long>> getTotalQuantityGroupedByActivityWithMinuteInterval(@Param("beginDate") LocalDateTime beginDate, @Param("endDate") LocalDateTime endDate);
}
