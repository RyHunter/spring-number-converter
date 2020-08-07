package com.number.converter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.number.converter.service.NumConvertService;
import com.number.converter.dto.NumConvertDto;

//@RestController automagically converts response to JSON
@RestController
public class NumConvertController {
    private final NumConvertService numConvertService;

    @Autowired
    public NumConvertController(NumConvertService numConvertService) {
        this.numConvertService = numConvertService;
    }

    @GetMapping("convertNumberToEnglish/{inputNumber}")
    public NumConvertDto numConverter(@PathVariable String inputNumber) {
        return numConvertService.convertNumToString(inputNumber);
    }
}
