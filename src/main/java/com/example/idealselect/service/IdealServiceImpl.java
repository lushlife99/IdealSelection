package com.example.idealselect.service;

import com.example.idealselect.repository.IdealMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class IdealServiceImpl {

    private final IdealMapper idealMapper;
}
