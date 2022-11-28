package com.example.insurance.services;

import com.example.insurance.beans.CustomerPolicy;
import com.example.insurance.model.Customer;
import com.example.insurance.model.Policy;
import com.example.insurance.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class SavePoliciesService {
    private final CustomerRepository customerRepository;

    private List<Policy> joinAllPoliciesForCustomer(String id, List<Policy> customerPolicies) {
        List<Policy> policies = customerRepository.findById(id).orElse(Customer.builder().policies(new ArrayList<>()).build()).getPolicies();
        policies.addAll(customerPolicies.stream().map(c -> Policy.builder()
                .policyNumber(c.getPolicyNumber())
                .productNumber(c.getProductNumber())
                .build()).collect(toList()));
        return policies;
    }


    public void save(List<CustomerPolicy> customerPolicies) {
        Map<String, List<CustomerPolicy>> customersMap = customerPolicies.stream().collect(groupingBy(CustomerPolicy::getInsuredId));
        customerRepository.saveAll(customersMap.values().stream().map(v -> {
                    CustomerPolicy customer = v.get(v.size()-1);
                    List<Policy> policies = v.stream().map(p ->
                            Policy.builder()
                                    .policyNumber(p.getPolicyNumber())
                                    .productNumber(p.getProductNumber())
                                    .build()).collect(toList());
                    return Customer.builder()
                            .id(customer.getInsuredId())
                            .firstName(customer.getInsuredFirstName())
                            .lastName(customer.getInsuredLastName())
                            .policies(joinAllPoliciesForCustomer(customer.getInsuredId(), policies)).build();
                }).collect(toList())
        );
    }
}
