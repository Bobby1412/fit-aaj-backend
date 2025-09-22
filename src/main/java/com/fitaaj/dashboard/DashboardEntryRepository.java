package com.fitaaj.dashboard;

import com.fitaaj.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DashboardEntryRepository extends JpaRepository<DashboardEntry, Long> {
    List<DashboardEntry> findByUserOrderByDateDesc(User user);
    Optional<DashboardEntry> findByUserAndDate(User user, LocalDate date);
}


