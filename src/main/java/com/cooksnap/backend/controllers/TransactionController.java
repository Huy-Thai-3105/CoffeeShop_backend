package com.cooksnap.backend.controllers;


import com.cooksnap.backend.domains.dto.requests.CreateTransactionRequest;
import com.cooksnap.backend.domains.dto.requests.MomoWebHookRequest;
import com.cooksnap.backend.services.servicesInterface.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;


@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("")
    public ResponseEntity<?> donation (@RequestBody CreateTransactionRequest createTransactionRequest) throws NoSuchAlgorithmException, InvalidKeyException {
        return transactionService.executeTransaction(
                createTransactionRequest.getUserName(),
            createTransactionRequest.getItems(),
            createTransactionRequest.getAmounts(),
                createTransactionRequest.getRedirectURL()
        );
    }

    @PostMapping("/callBack-momo")
    public void callback_MOMO_IPN (@RequestBody MomoWebHookRequest momoResponse){
        try {
//            donationService.handleTransaction(momoResponse);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
