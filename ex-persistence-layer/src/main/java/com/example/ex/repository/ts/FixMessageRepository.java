package com.example.ex.repository.ts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.example.ex.model.entity.ts.FixMessage;

@NoRepositoryBean
public interface FixMessageRepository<T extends FixMessage> extends JpaRepository<T, Long> {

    List<T> findAllByOrderBySequenceDesc();

}
