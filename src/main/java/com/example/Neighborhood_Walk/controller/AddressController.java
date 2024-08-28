package com.example.Neighborhood_Walk.controller;

import com.example.Neighborhood_Walk.Mapper.AddressMapper;
import com.example.Neighborhood_Walk.entity.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressMapper addressMapper;

    @PostMapping("/save")
    public String saveAddress(@RequestBody Address address) {
        // check address exits or not
        Address existingAddress = addressMapper.findByDetails(
                address.getAddressLine1(),
                address.getAddressLine2(),
                address.getCity(),
                address.getState(),
                address.getPostcode(),
                address.getCountry()
        );

        if (existingAddress != null) {
            // if address exist
            return existingAddress.getAddressId();
        } else {
            // if address not exist then insert
            address.setAddressId(UUID.randomUUID().toString());
            addressMapper.insert(address);
            return address.getAddressId();
        }
    }

    //Get address by id
    @GetMapping("/{id}")
    public Address getAddressById(@PathVariable("id") String addressId) {
        return addressMapper.selectById(addressId);
    }

    //Update address
    @PutMapping("/{id}")
    public String updateAddress(@PathVariable("id") String addressId, @RequestBody Address address) {
        Address existingAddress = addressMapper.selectById(addressId);
        if (existingAddress == null) {
            return "Address not found.";
        }
        address.setAddressId(addressId);
        addressMapper.updateById(address);
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
