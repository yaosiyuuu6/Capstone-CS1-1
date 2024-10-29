package com.example.Neighborhood_Walk.controller;

import com.example.Neighborhood_Walk.Mapper.AddressMapper;
import com.example.Neighborhood_Walk.dto.Coordinates;
import com.example.Neighborhood_Walk.entity.Address;
import com.example.Neighborhood_Walk.service.GeocodingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private GeocodingService geocodingService;

    @PostMapping("/save")
    public String saveAddress(@RequestBody Address address) {
        // 检查地址是否已存在
        Address existingAddress = addressMapper.findByDetails(
                address.getAddressLine1(),
                address.getAddressLine2(),
                address.getCity(),
                address.getState(),
                address.getPostcode(),
                address.getCountry()
        );

        if (existingAddress != null) {
            // 如果地址已存在，返回现有的 addressId
            return existingAddress.getAddressId();
        } else {
            // 如果地址不存在，反解析地址获取经纬度
            if (address.getLatitude() == null || address.getLongitude() == null) {
                // 调用 geocodingService 来获取坐标
                Coordinates coordinates = geocodingService.reverseGeocode(
                        address.getAddressLine1() + " " +
                                address.getAddressLine2() + " " +
                                address.getCity() + " " +
                                address.getState() + " " +
                                address.getPostcode() + " " +
                                address.getCountry()
                );

                // 将获取到的经纬度填入 address 对象中
                System.out.println(coordinates);
                address.setLatitude(coordinates.getLatitude());
                address.setLongitude(coordinates.getLongitude());
            }

            // 生成一个新的 UUID 作为 addressId
            address.setAddressId(UUID.randomUUID().toString());
            // 插入新地址
            addressMapper.insert(address);

            // 返回新地址的 addressId
            return address.getAddressId();
        }
    }


    //Get address by id
    @GetMapping("/{id}")
    public Address getAddressById(@PathVariable("id") String addressId) {
        return addressMapper.selectById(addressId);
    }

    // Update address
    @PutMapping("/{id}")
    public String updateAddress(@PathVariable("id") String id, @RequestBody Address address) {
        // 使用路径参数中的 id 查找现有地址
        Address existingAddress = addressMapper.selectById(id);
        if (existingAddress == null) {
            return "Address not found.";
        }

        // 更新其他属性
        existingAddress.setAddressLine1(address.getAddressLine1());
        existingAddress.setAddressLine2(address.getAddressLine2());
        existingAddress.setCity(address.getCity());
        existingAddress.setState(address.getState());
        existingAddress.setPostcode(address.getPostcode());
        existingAddress.setCountry(address.getCountry());

        // 如果坐标未提供，调用地理编码服务来获取坐标
        if (existingAddress.getLatitude() == null || existingAddress.getLongitude() == null) {
            Coordinates coordinates = geocodingService.reverseGeocode(
                    existingAddress.getAddressLine1() + " " +
                            existingAddress.getAddressLine2() + " " +
                            existingAddress.getCity() + " " +
                            existingAddress.getState() + " " +
                            existingAddress.getPostcode() + " " +
                            existingAddress.getCountry()
            );
            existingAddress.setLatitude(coordinates.getLatitude());
            existingAddress.setLongitude(coordinates.getLongitude());
        }

        // 更新地址
        addressMapper.updateById(existingAddress);

        return "Address updated successfully.";
    }


    //Delete address
    @DeleteMapping("/{id}")
    public String deleteAddress(@PathVariable("id") String addressId) {
        int result = addressMapper.deleteById(addressId);
        if (result > 0) {
            return "Address deleted successfully.";
        } else {
            return "Address not found.";
        }
    }

    //Get all addresses
    @GetMapping("/all")
    public List<Address> getAllAddresses() {
        return addressMapper.selectList(null);
    }





}
