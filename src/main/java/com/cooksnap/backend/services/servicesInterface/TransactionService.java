package com.cooksnap.backend.services.servicesInterface;

import com.cooksnap.backend.domains.dto.requests.MomoWebHookRequest;
import org.springframework.http.ResponseEntity;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;

public interface TransactionService {
    ResponseEntity<?> executeTransaction (String userName, String items, Long totalAmount, String upinURL) throws NoSuchAlgorithmException, InvalidKeyException;

    void handleTransaction(MomoWebHookRequest momoResponse) throws NoSuchAlgorithmException, InvalidKeyException;
}
