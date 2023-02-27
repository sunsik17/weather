package com.zerobase.weather.service;

import com.zerobase.weather.domain.TheWeatherOfTheDay;
import com.zerobase.weather.domain.Diary;
import com.zerobase.weather.dto.DiaryDto;
import com.zerobase.weather.exception.ErrorCode;
import com.zerobase.weather.exception.WeatherDiaryException;
import com.zerobase.weather.repository.DateWeatherRepository;
import com.zerobase.weather.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final DateWeatherRepository dateWeatherRepository;

    @Transactional
    public void createDiary(LocalDate date, String text) {
        TheWeatherOfTheDay theWeatherOfTheDay = dateWeatherRepository.findByDate(date)
                .orElseThrow(() -> new WeatherDiaryException(ErrorCode.NOT_FOUND_WEATHER_DATA));

        diaryRepository.save(Diary.builder()
                .weather(theWeatherOfTheDay.getWeather())
                .icon(theWeatherOfTheDay.getIcon())
                .temperature(theWeatherOfTheDay.getTemperature())
                .date(date)
                .text(text)
                .build());
    }

    @Transactional(readOnly = true)
    public List<DiaryDto> getDiaryList(LocalDate date) {
        return diaryRepository.findAllByDate(date)
                .stream().map(DiaryDto::from).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DiaryDto> getDiaries(LocalDate startDate, LocalDate endDate) {
        return diaryRepository.findAllByDateBetween(startDate, endDate)
                .stream()
                .map(DiaryDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateDiary(LocalDate date, String text) {
        Diary diary = diaryRepository.findFirstByDate(date).orElseThrow(
                () -> new WeatherDiaryException(ErrorCode.NOT_FOUND_DIARY));

        diary.setText(text);
    }

    @Transactional
    public void deleteDiary(LocalDate date) {
        diaryRepository.deleteAllByDate(date);
    }
}
