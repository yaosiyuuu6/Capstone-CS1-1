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


    // Get detailed information about an agreement by its ID
    @GetMapping("/detail/{agreement_id}")
    public AgreementDetail getDetail(@PathVariable("agreement_id") String agreementId) {
        return AgreementDetailMapper.getAgreementDetail(agreementId);
    }

    // Get all comments for a specific walker by their walker ID
    @GetMapping("/comments/{walker_id}")
    public List<AgreementDetail> getComments(@PathVariable("walker_id") String walkerId) {
        return AgreementDetailMapper.getComments(walkerId);
    }

    // Get all comments across all agreements
    @GetMapping("/comments/all")
    public List<AgreementDetail> getAllComments() {
        return AgreementDetailMapper.getAllComments();
    }

    // Get the rating for a specific walker by their walker ID
    @GetMapping("/rating/{walker_id}")
    public WalkerRating getRating(@PathVariable("walker_id") String walkerId) {
        return AgreementDetailMapper.getRating(walkerId);
    }

    // Get all agreements associated with a specific parent by their parent ID
    @GetMapping("/listByParent/{parent_id}")
    public List<AgreementDetail> getAgreements(@PathVariable("parent_id") String parentId) {
        return AgreementDetailMapper.getAgreements(parentId);
    }

}
