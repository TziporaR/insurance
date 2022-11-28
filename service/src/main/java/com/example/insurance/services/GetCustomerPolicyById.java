package com.example.insurance.services;

import com.example.insurance.beans.CustomerPolicy;
import com.example.insurance.model.Customer;
import com.example.insurance.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetCustomerPolicyById {
    private final CustomerRepository customerRepository;

    public ResponseEntity<List<CustomerPolicy>> get(String id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            return ResponseEntity.ok(customer.getPolicies().stream().map(c ->
                    CustomerPolicy.builder()
                            .insuredFirstName(customer.getFirstName())
                            .insuredLastName(customer.getLastName())
                            .insuredId(customer.getId())
                            .policyNumber(c.getPolicyNumber())
                            .productNumber(c.getProductNumber())
                            .build()).collect(Collectors.toList()));
        }
        return ResponseEntity.badRequest().build();
    }
}
