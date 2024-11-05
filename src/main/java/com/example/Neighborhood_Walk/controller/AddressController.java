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

    // Endpoint to save a new address
    @PostMapping("/save")
    public String saveAddress(@RequestBody Address address) {

        // Reverse geocode the address to get latitude and longitude if not provided
        if (address.getLatitude() == null || address.getLongitude() == null) {
            // Call geocodingService to get coordinates based on address details
            Coordinates coordinates = geocodingService.reverseGeocode(
                    address.getAddressLine1() + " " +
                            address.getAddressLine2() + " " +
                            address.getCity() + " " +
                            address.getState() + " " +
                            address.getPostcode() + " " +
                            address.getCountry()
            );

            // Fill the retrieved latitude and longitude into the address object
            System.out.println(coordinates);
            address.setLatitude(coordinates.getLatitude());
            address.setLongitude(coordinates.getLongitude());
        }

        // Generate a new UUID for the addressId
        address.setAddressId(UUID.randomUUID().toString());

        // Insert the new address into the database
        addressMapper.insert(address);

        // Return the generated addressId
        return address.getAddressId();
    }

    // Get an address by its ID
    @GetMapping("/{id}")
    public Address getAddressById(@PathVariable("id") String addressId) {
        return addressMapper.selectById(addressId);
    }

    // Update an existing address
    @PutMapping("/{id}")
    public String updateAddress(@PathVariable("id") String id, @RequestBody Address address) {
        // Find the existing address using the ID from the path
        Address existingAddress = addressMapper.selectById(id);
        if (existingAddress == null) {
            return "Address not found.";
        }

        // Update the address attributes with new values
        existingAddress.setAddressLine1(address.getAddressLine1());
        existingAddress.setAddressLine2(address.getAddressLine2());
        existingAddress.setCity(address.getCity());
        existingAddress.setState(address.getState());
        existingAddress.setPostcode(address.getPostcode());
        existingAddress.setCountry(address.getCountry());

        // Reverse geocode the updated address to get the new latitude and longitude
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

        // Update the address in the database
        addressMapper.updateById(existingAddress);

        return "Address updated successfully.";
    }

    // Delete an address by its ID
    @DeleteMapping("/{id}")
    public String deleteAddress(@PathVariable("id") String addressId) {
        int result = addressMapper.deleteById(addressId);
        if (result > 0) {
            return "Address deleted successfully.";
        } else {
            return "Address not found.";
        }
    }

    // Get all addresses
    @GetMapping("/all")
    public List<Address> getAllAddresses() {
        return addressMapper.selectList(null);
    }
}


