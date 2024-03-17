package com.cooksnap.backend.domains.dto.requests;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTransactionRequest {
    Long amounts;
    int campaignId;
    int organizationUserId;
}
