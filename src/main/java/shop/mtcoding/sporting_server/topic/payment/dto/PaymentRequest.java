package shop.mtcoding.sporting_server.topic.payment.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class PaymentRequest {

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class FormInDTO {
        private LocalDate reservationDate;
        private String time;

        public FormInDTO(LocalDate reservationDate, String time) {
            this.reservationDate = reservationDate;
            this.time = time;
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReceiptInDTO {
        @JsonProperty("receipt_id")
        private String receiptId;

        @JsonProperty("order_id")
        private String orderId;

        @JsonProperty("price")
        private int price;

        @JsonProperty("tax_free")
        private int taxFree;

        @JsonProperty("cancelled_price")
        private int cancelledPrice;

        @JsonProperty("cancelled_tax_free")
        private int cancelledTaxFree;

        @JsonProperty("order_name")
        private String orderName;

        @JsonProperty("company_name")
        private String companyName;

        @JsonProperty("gateway_url")
        private String gatewayUrl;

        @JsonProperty("metadata")
        private Metadata metadata;

        private boolean sandbox;

        private String pg;

        private String method;

        @JsonProperty("method_symbol")
        private String methodSymbol;

        @JsonProperty("method_origin")
        private String methodOrigin;

        @JsonProperty("method_origin_symbol")
        private String methodOriginSymbol;

        @JsonProperty("purchased_at")
        private OffsetDateTime purchasedAt;

        @JsonProperty("cancelled_at")
        private OffsetDateTime cancelledAt;

        @JsonProperty("requested_at")
        private OffsetDateTime requestedAt;

        @JsonProperty("status_locale")
        private String statusLocale;

        @JsonProperty("receipt_url")
        private String receiptUrl;

        private int status;

        @JsonProperty("kakao_money_data")
        private CardData kakaoMoneyData;

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        public class CardData {
            @JsonProperty("tid")
            private String tid;

            @JsonProperty("cancel_tid")
            private String cancelTid;

            // @JsonProperty("card_approve_no")
            // private String cardApproveNo;

            // @JsonProperty("card_no")
            // private String cardNo;

            // @JsonProperty("card_quota")
            // private String cardQuota;

            // @JsonProperty("card_company_code")
            // private String cardCompanyCode;

            // @JsonProperty("card_company")
            // private String cardCompany;
        }

        @Getter
        @Setter
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public class Metadata {
            // @JsonProperty("test")
            // private String test;
        }
    }

}
