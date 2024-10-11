package com.example.Neighborhood_Walk.controller;

import com.example.Neighborhood_Walk.entity.AgreementDetail;
import com.example.Neighborhood_Walk.Mapper.AgreementDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@RestController
@RequestMapping("/agreements")
public class AgreementDetailController {


    @Autowired
    private AgreementDetailMapper AgreementDetailMapper;


    // Get Detail
    @GetMapping("/detail/{agreement_id}")
    public AgreementDetail getDetail(@PathVariable("agreement_id") String agreementId) {
        return AgreementDetailMapper.getAgreementDetail(agreementId);
    }
}
