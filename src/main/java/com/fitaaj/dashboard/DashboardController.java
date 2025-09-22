package com.fitaaj.dashboard;

import com.fitaaj.user.User;
import com.fitaaj.user.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardEntryRepository dashboardEntryRepository;
    private final UserRepository userRepository;

    public DashboardController(DashboardEntryRepository dashboardEntryRepository, UserRepository userRepository) {
        this.dashboardEntryRepository = dashboardEntryRepository;
        this.userRepository = userRepository;
    }

    public record DashboardRequest(@NotNull Long userId,
                                   @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                   Integer steps,
                                   Integer waterMl,
                                   Integer exerciseMinutes,
                                   Integer caloriesIntake) {}

    @PostMapping
    public ResponseEntity<?> upsertEntry(@Valid @RequestBody DashboardRequest request) {
        User user = userRepository.findById(request.userId()).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "User not found"));
        }

        DashboardEntry entry = dashboardEntryRepository
                .findByUserAndDate(user, request.date())
                .orElseGet(DashboardEntry::new);
        entry.setUser(user);
        entry.setDate(request.date());
        entry.setSteps(request.steps());
        entry.setWaterMl(request.waterMl());
        entry.setExerciseMinutes(request.exerciseMinutes());
        entry.setCaloriesIntake(request.caloriesIntake());
        dashboardEntryRepository.save(entry);
        return ResponseEntity.ok(Map.of("message", "dashboard saved"));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<DashboardEntry>> list(@PathVariable Long userId) {
        return userRepository.findById(userId)
                .map(user -> ResponseEntity.ok(dashboardEntryRepository.findByUserOrderByDateDesc(user)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}


