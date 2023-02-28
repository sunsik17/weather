package com.zerobase.weather.controller;

import com.zerobase.weather.dto.DiaryDto;
import com.zerobase.weather.dto.DiaryResponse;
import com.zerobase.weather.service.DiaryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class DiaryController {
    private final DiaryService diaryService;

    @ApiOperation("일기 텍스트와 날씨를 이용해서 DB에 일기 저장")
    @PostMapping("/create/diary")
    public DiaryResponse createDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                         @ApiParam(value = "yyyy-MM-dd",example = "2023-02-27")LocalDate date,
                                     @RequestBody String text) {
        diaryService.createDiary(date, text);
        return new DiaryResponse("다이어리가 성공적으로 생성되었습니다.");
    }

    @ApiOperation("선택한 날짜의 모든 일기 데이터를 가져옵니다")
    @GetMapping("/read/diary")
    public List<DiaryDto> readDiaries(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                          @ApiParam(value = "yyyy-MM-dd",example = "2023-02-27") LocalDate date) {
        return diaryService.getDiaryList(date);
    }

    @ApiOperation("선택한 기간중의 모든 일기 데이터를 가져옵니다")
    @GetMapping("/read/diaries")
    public List<DiaryDto> readDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                        @ApiParam(value = "yyyy-MM-dd",example = "2023-02-27") LocalDate startDate,
                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                    @ApiParam(value = "yyyy-MM-dd",example = "2023-02-27") LocalDate endDate) {
        return diaryService.getDiaries(startDate, endDate);
    }

    @ApiOperation("선택한 날짜 일기 수정")
    @PutMapping("/update/diary")
    public DiaryResponse updateDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                         @ApiParam(value = "yyyy-MM-dd",example = "2023-02-27") LocalDate date,
                            @RequestBody String text) {
        diaryService.updateDiary(date, text);

        return new DiaryResponse("다이어리가 성공적으로 수정되었습니다.");
    }

    @ApiOperation("선택한 날짜 일기 삭제")
    @DeleteMapping("/delete/diary")
    public DiaryResponse deleteDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                         @ApiParam(value = "yyyy-MM-dd",example = "2023-02-27") LocalDate date) {
        diaryService.deleteDiary(date);

        return new DiaryResponse("다이어리가 성공적으로 삭제되었습니다.");
    }
}