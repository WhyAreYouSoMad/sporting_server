package shop.mtcoding.sporting_server.core.util;

import java.util.HashMap;

import kr.co.bootpay.Bootpay;
import kr.co.bootpay.model.request.Cancel;
import shop.mtcoding.sporting_server.core.exception.Exception400;

public class BootPayPaymentUtils {

    public static void cancelPayment(String message, String receiptId) {
        String restApiKey = "643f9df8755e27001ae57d0c";
        String privateKey = "Xh68nK2FMKk5JZSoPEOzuOA3d4N+nR0t3CGgGo7Jf/Y=";

        Bootpay bootpay = new Bootpay(restApiKey, privateKey);
        HashMap token;

        try {
            token = bootpay.getAccessToken();
            if (token.get("error_code") != null) { // failed
                throw new Exception400("getAccessToken 에러 : ApiKey, PrivateKey 값 확인필요");
            }
        } catch (Exception e) {
            throw new Exception400("getAccessToken 통신 실패 : ApiKey, PrivateKey 값 확인필요");
        }
        Cancel cancel = new Cancel();
        cancel.receiptId = receiptId;
        cancel.cancelUsername = "관리자";
        cancel.cancelMessage = "결제 취소";

        HashMap res;
        try {
            res = bootpay.receiptCancel(cancel);
        } catch (Exception e) {
            throw new Exception400("결제취소 통신 실패 : receiptId 확인필요");
        }

        if (res.get("error_code") == null) { // success
            throw new Exception400("결제가 취소되었습니다 : " + message);
        } else {
            throw new Exception400("결제 취소에 실패하였습니다. : 고객센터에 문의바람, " + message);
        }
    }

}
