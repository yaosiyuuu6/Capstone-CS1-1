package com.example.Neighborhood_Walk.controller;

import com.example.Neighborhood_Walk.entity.AgreementDetail;
import com.example.Neighborhood_Walk.Mapper.AgreementDetailMapper;
import com.example.Neighborhood_Walk.entity.WalkerRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    @GetMapping("/comments/{walker_id}")
    public List<AgreementDetail> getComments(@PathVariable("walker_id") String walkerId) {
        return AgreementDetailMapper.getComments(walkerId);
    }

    @GetMapping("/rating/{walker_id}")
    public WalkerRating getRating(@PathVariable("walker_id") String walkerId){
        return AgreementDetailMapper.getRating(walkerId);
    }

    @GetMapping("/listByParent/{parent_id}")
    public List<AgreementDetail> getAgreements(@PathVariable("parent_id") String parentId){
        return AgreementDetailMapper.getAgreements(parentId);
    }
}
