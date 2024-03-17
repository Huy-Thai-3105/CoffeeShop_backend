package com.cooksnap.backend.services.servicesIplm;

import com.cooksnap.backend.domains.dto.ErrorResponseDto;
import com.cooksnap.backend.services.servicesInterface.TransactionService;
import com.cooksnap.backend.domains.dto.requests.MomoRequest;
import com.cooksnap.backend.domains.dto.requests.MomoWebHookRequest;
import com.cooksnap.backend.domains.dto.responses.MomoResponse;
import com.cooksnap.backend.domains.entity.*;
import com.cooksnap.backend.repositories.*;
import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final Environment environment;
    private final UserRepository userRepository;
    public ResponseEntity<?> executeTransaction (Principal connectedUser, Long totalAmount, int organizationUserId, int campaignId) throws NoSuchAlgorithmException, InvalidKeyException {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        String jsonData = "{\"campaignId\":" + campaignId + ",\"organizationUserId\":" + organizationUserId + "}";
        String base64Data = encodeBase64(jsonData);

        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String partnerCode = environment.getProperty("PARTNER_CODE");
        String accessKey = environment.getProperty("ACCESS_KEY");
        String secretKey = environment.getProperty("SECRET_KEY");
        String ipnURL = environment.getProperty("IPN_URL");
        String url = "https://test-payment.momo.vn:443/v2/gateway/api/create";
        String extraData = base64Data;
        String orderId = partnerCode +  outputFormat.format(new Date()) + "-" + user.getUserId();
        String orderInfo = user.getFullName() + " donation";
        String requestId = partnerCode + outputFormat.format(new Date());
        String redirectURL = "https://www.facebook.com";

        try {
//            Optional<Organization> organization = organizationRepository.findByUserId(organizationUserId);
//            if (organization.isEmpty()){
//                return ResponseEntity.badRequest().body(new ErrorResponseDto("organization user id not found"));
//            }
            assert secretKey != null;
            String signature = generateMomoCreateTransactionSignature(accessKey,totalAmount,extraData,ipnURL,orderId,orderInfo,partnerCode,redirectURL,requestId,"captureWallet",secretKey);

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            MomoRequest momoRequest = MomoRequest.builder()
                    .partnerCode(partnerCode)
                    .accessKey(accessKey)
                    .secretKey(secretKey)
                    .requestId(requestId)
                    .amount(totalAmount)
                    .storeId("Unidy")
                    .orderId(orderId)
                    .orderInfo(orderInfo)
                    .ipnUrl(ipnURL)
                    .redirectUrl(redirectURL)
                    .lang("en")
                    .requestType("captureWallet")
                    .signature(signature)
                    .extraData(extraData)
                    .build() ;
            HttpEntity<MomoRequest> requestEntity = new HttpEntity<>(momoRequest, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
            String responseData = response.getBody();
            System.out.println(responseData);
            // Check response
            if (response.getStatusCode() == HttpStatusCode.valueOf(500)){
                return ResponseEntity.badRequest().body(new ErrorResponseDto("Can't call api from recommend service"));
            }
            MomoResponse momoResponse = new Gson().fromJson(responseData, MomoResponse.class);
            return ResponseEntity.ok().body(momoResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @Transactional
    public void handleTransaction(MomoWebHookRequest momoResponse) {
        try {
                System.out.println("Log Transaction Successful");
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }
    public static String generateMomoCreateTransactionSignature(String accessKey, Long amount, String extraData, String ipnUrl,
                                                                String orderId, String orderInfo, String partnerCode, String redirectUrl,
                                                                String requestId, String requestType, String secretKey)
            throws NoSuchAlgorithmException, InvalidKeyException {
        String rawSignature = "accessKey=" + accessKey +
                "&amount=" + amount +
                "&extraData=" + extraData +
                "&ipnUrl=" + ipnUrl +
                "&orderId=" + orderId +
                "&orderInfo=" + orderInfo +
                "&partnerCode=" + partnerCode +
                "&redirectUrl=" + redirectUrl +
                "&requestId=" + requestId +
                "&requestType=" + requestType;

        Mac sha256Hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
        sha256Hmac.init(secretKeySpec);

        byte[] signatureBytes = sha256Hmac.doFinal(rawSignature.getBytes());

        return byteArrayToHexString(signatureBytes);
    }

    private static String byteArrayToHexString(byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

    private static String encodeBase64(String jsonData) {
        byte[] encodedBytes = Base64.getEncoder().encode(jsonData.getBytes());
        return new String(encodedBytes);
    }

    private static String decodeBase64(String base64Data) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64Data.getBytes());
        return new String(decodedBytes);
    }

    @Getter
    static class MyDataObject {
        private Integer organizationUserId;
        private Integer campaignId;
    }
}
