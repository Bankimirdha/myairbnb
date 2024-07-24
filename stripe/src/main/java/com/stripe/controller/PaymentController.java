package com.stripe.controller;

import com.stripe.entities.Product;
import com.stripe.repository.ProductRepository;
import com.stripe.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Value("${stripe.api.key}")
 private String stripeApiKey;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PaymentService paymentService;

    //http://localhost:8080/payments?prodectId=1
    @PostMapping("/initiate")
    public ResponseEntity<String> initiatePayment(@RequestParam Long prodectId){
        Product product = productRepository.findById(prodectId).orElseThrow(
                ()-> new EntityNotFoundException("product not found"));
        String paymentIntentId = paymentService.createPaymentIntent(product.getPrice());
        return ResponseEntity.ok(paymentIntentId);
    }
}
