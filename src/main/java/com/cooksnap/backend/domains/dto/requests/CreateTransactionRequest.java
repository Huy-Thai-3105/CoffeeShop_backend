package com.cooksnap.backend.domains.dto.requests;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTransactionRequest {
    String userName;
    String items;
    Long amounts;
    String redirectURL;
}
