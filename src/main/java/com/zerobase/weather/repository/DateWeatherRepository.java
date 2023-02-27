package com.zerobase.weather.repository;

import com.zerobase.weather.domain.TheWeatherOfTheDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface DateWeatherRepository extends JpaRepository<TheWeatherOfTheDay, LocalDate> {
    Optional<TheWeatherOfTheDay> findByDate(LocalDate date);
}
