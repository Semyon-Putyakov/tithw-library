package ru.javabegin.semyon.dto;

import jakarta.validation.constraints.FutureOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class DateDTO {
    @FutureOrPresent(message = "Дата должна быть не раньше сегодняшней!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
