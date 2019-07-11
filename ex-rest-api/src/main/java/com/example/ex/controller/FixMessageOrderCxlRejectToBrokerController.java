package com.example.ex.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.ex.model.entity.ts.FixMessageOrderCxlRejectToBroker;

@Component
public class FixMessageOrderCxlRejectToBrokerController extends TimeSeriesController<FixMessageOrderCxlRejectToBroker>{

    public static final String BASE_URI = "/ordercxlrejecttobrokers";

    @Override
    public List<FixMessageOrderCxlRejectToBroker> findAll() {
        return timeSeriesService.getFixMessageOrderCxlRejectToBrokers();
    }

    @Override
    public Optional<FixMessageOrderCxlRejectToBroker> findById(Long sequence) {
        return timeSeriesService.getFixMessageOrderCxlRejectToBrokerById(sequence);
    }
    
}
