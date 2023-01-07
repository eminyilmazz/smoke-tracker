package com.eminyilmazz.smoketracker.repository;

import com.eminyilmazz.smoketracker.entity.Smoke;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmokeRepository extends JpaRepository<Smoke, Long> {
}
