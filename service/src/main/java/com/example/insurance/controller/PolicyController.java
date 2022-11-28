package com.example.insurance.controller;

import com.example.insurance.beans.CustomerPolicy;
import com.example.insurance.services.GetCustomerPolicyById;
import com.example.insurance.services.SavePoliciesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/policy")
@AllArgsConstructor
public class PolicyController {
    private final SavePoliciesService savePoliciesService;
    private final GetCustomerPolicyById getCustomerPolicyById;

    @GetMapping("/{id}")
    public ResponseEntity<List<CustomerPolicy>> getCustomerPolicies(@PathVariable String id) {
        return getCustomerPolicyById.get(id);
    }

    @PostMapping
    @ResponseStatus(NO_CONTENT)
    public void savePolicies(@RequestBody List<CustomerPolicy> customerPolicies) {
        savePoliciesService.save(customerPolicies);
    }
}
