package com.example.ex.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ex.model.entity.ts.FixMessageOrderCxlRejectToBroker;
import com.example.ex.model.entity.ts.FixMessageStatusToBroker;
import com.example.ex.repository.ts.FixMessageOrderCxlRejectToBrokerRepository;
import com.example.ex.repository.ts.FixMessageStatusToBrokerRepository;

@Service
public class TimeSeriesService {
    
    @Autowired
    private FixMessageStatusToBrokerRepository fixMessageStatusToBrokerRepository;
    @Autowired
    private FixMessageOrderCxlRejectToBrokerRepository fixMessageOrderCxlRejectToBrokerRepository;

    public List<FixMessageStatusToBroker> getFixMessageStatusToBrokers() {
        return fixMessageStatusToBrokerRepository.findAllByOrderBySequenceDesc();
    }

    public Optional<FixMessageStatusToBroker> getFixMessageStatusToBrokerById(Long sequence) {
        return fixMessageStatusToBrokerRepository.findById(sequence);
    }

    public List<FixMessageOrderCxlRejectToBroker> getFixMessageOrderCxlRejectToBrokers() {
        return fixMessageOrderCxlRejectToBrokerRepository.findAllByOrderBySequenceDesc();
    }

    public Optional<FixMessageOrderCxlRejectToBroker> getFixMessageOrderCxlRejectToBrokerById(Long sequence) {
        return fixMessageOrderCxlRejectToBrokerRepository.findById(sequence);
    }
}
