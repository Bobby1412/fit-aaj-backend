package com.fitaaj.dashboard;

import com.fitaaj.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "dashboard_entries")
public class DashboardEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    private LocalDate date;

    private Integer steps;
    private Integer waterMl;
    private Integer exerciseMinutes;
    private Integer caloriesIntake;

    public DashboardEntry() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getSteps() {
        return steps;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }

    public Integer getWaterMl() {
        return waterMl;
    }

    public void setWaterMl(Integer waterMl) {
        this.waterMl = waterMl;
    }

    public Integer getExerciseMinutes() {
        return exerciseMinutes;
    }

    public void setExerciseMinutes(Integer exerciseMinutes) {
        this.exerciseMinutes = exerciseMinutes;
    }

    public Integer getCaloriesIntake() {
        return caloriesIntake;
    }

    public void setCaloriesIntake(Integer caloriesIntake) {
        this.caloriesIntake = caloriesIntake;
    }
}


