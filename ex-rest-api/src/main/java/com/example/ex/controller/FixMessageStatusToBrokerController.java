package com.example.ex.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.ex.model.entity.ts.FixMessageStatusToBroker;

@Component
public class FixMessageStatusToBrokerController extends TimeSeriesController<FixMessageStatusToBroker> {

    public static final String BASE_URI = "/statustobrokers";

    @Override
    public List<FixMessageStatusToBroker> findAll() {
        return timeSeriesService.getFixMessageStatusToBrokers();
    }

    @Override
    public Optional<FixMessageStatusToBroker> findById(Long sequence) {
        return timeSeriesService.getFixMessageStatusToBrokerById(sequence);
    }
}
