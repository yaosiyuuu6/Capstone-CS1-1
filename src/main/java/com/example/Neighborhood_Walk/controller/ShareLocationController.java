package com.example.Neighborhood_Walk.controller;

import com.example.Neighborhood_Walk.entity.ShareLocation;
import com.example.Neighborhood_Walk.service.ShareLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/sharelocation")
public class ShareLocationController {

    @Autowired
    private ShareLocationService shareLocationService;

    // 开始位置共享
    @PostMapping("/start/{agreementId}")
    public String startLocationSharing(@PathVariable String agreementId, @RequestBody ShareLocation shareLocation) {
        // 设置 agreementId，确保区分不同的共享
        shareLocation.setAgreementId(agreementId);

        // 保存到 Redis 或其他存储介质
        shareLocationService.saveLocation(shareLocation);

        return "Location sharing started.";
    }

    // 停止位置共享
    @PostMapping("/stop/{agreementId}")
    public String stopLocationSharing(@PathVariable String agreementId) {
        ShareLocation shareLocation = shareLocationService.getLocationByAgreementId(agreementId);
        shareLocation.setEndTime(LocalDateTime.now());
        shareLocationService.saveLocation(shareLocation);
        return "Location sharing stopped.";
    }

    // 获取 Walker 的实时位置
    @GetMapping("/current/{agreementId}")
    public ShareLocation getCurrentLocation(@PathVariable String agreementId) {
        return shareLocationService.getLocationByAgreementId(agreementId);
    }
}

