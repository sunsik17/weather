package com.zerobase.weather.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class TheWeatherOfTheDay {
    @Id
    private LocalDate date;
    private String weather;
    private String icon;
    private double temperature;
}
