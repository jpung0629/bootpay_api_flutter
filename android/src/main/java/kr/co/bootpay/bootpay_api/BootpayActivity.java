package kr.co.bootpay.bootpay_api;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import kr.co.bootpay.Bootpay;
import kr.co.bootpay.enums.UX;
import kr.co.bootpay.listener.CancelListener;
import kr.co.bootpay.listener.CloseListener;
import kr.co.bootpay.listener.ConfirmListener;
import kr.co.bootpay.listener.DoneListener;
import kr.co.bootpay.listener.ErrorListener;
import kr.co.bootpay.listener.ReadyListener;
import kr.co.bootpay.model.BootExtra;
import kr.co.bootpay.model.BootUser;
import kr.co.bootpay.model.Item;
import kr.co.bootpay.model.Payload;

public class BootpayActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String payloadString = this.getIntent().getStringExtra("payload");
        String userString = this.getIntent().getStringExtra("user");
        String itemsString = this.getIntent().getStringExtra("items");
        String extraString = this.getIntent().getStringExtra("extra");

        BootUser bootUser = new BootUser();
        List<Item> items = new ArrayList<>();
        BootExtra bootExtra = new BootExtra();

        Payload payload = new Gson().fromJson(payloadString, Payload.class);
        if(userString != null && !userString.isEmpty()) bootUser =  new Gson().fromJson(userString, BootUser.class);
        if(itemsString != null && !itemsString.isEmpty()) items =  new Gson().fromJson(itemsString, new TypeToken<List<Item>>(){}.getType());
        if(extraString != null && !extraString.isEmpty()) bootExtra =  new Gson().fromJson(extraString, BootExtra.class);

        goBootpayRequest(payload, bootUser, bootExtra, items);
    }

    void goBootpayRequest(Payload payload, BootUser user, BootExtra extra, List<Item> items) {

        Bootpay.init(this)
            .setContext(this)
            .setApplicationId(payload.getApplication_id()) // 해당 프로젝트(안드로이드)의 application id 값
            .setBootExtra(extra)
            .setPG(payload.getPg())
            .setMethod(payload.getMethod())
            .setMethods(payload.getMethods())
            .setBootUser(user)
            .setUX(UX.PG_DIALOG)
            //.isShowAgree(true)
            .setName(payload.getName()) // 결제할 상품명
            .setOrderId(payload.getOrder_id()) // 결제 고유번호
            .setIsShowAgree(payload.getShow_agree_window())
            .setTaxFree(payload.getTax_free())
            .setPrice(payload.getPrice()) // 결제할 금액
            .setAccountExpireAt(payload.getAccount_expire_at())
            .setEasyPayUserToken(payload.getEasyPayUserToken())
            .setItems(items)
            .onConfirm(new ConfirmListener() { // 결제가 진행되기 바로 직전 호출되는 함수로, 주로 재고처리 등의 로직이 수행
                @Override
                public void onConfirm(String message) {
                    Bootpay.confirm(message); // 재고가 있을 경우.
                }
            })
            .onDone(new DoneListener() { // 결제완료시 호출, 아이템 지급 등 데이터 동기화 로직을 수행합니다
                @Override
                public void onDone(String message) {
                    setFinishData("onDone", message);
                }
            })
            .onReady(new ReadyListener() { // 가상계좌 입금 계좌번호가 발급되면 호출되는 함수입니다.
                @Override
                public void onReady(String message) {
                    setFinishData("onReady", message);
                }
            })
            .onCancel(new CancelListener() { // 결제 취소시 호출
                @Override
                public void onCancel(String message) {
                    setFinishData("onCancel", message);
                }
            })
            .onError(new ErrorListener() { // 에러가 났을때 호출되는 부분
                @Override
                public void onError(String message) {
                    setFinishData("onError", message);
                }
            })
            .onClose(new CloseListener() { //결제창이 닫힐때 실행되는 부분
                @Override
                public void onClose(String message) {
                    finish();
//                    setFinishData("onClose", "");
                }
            })
            .request();
    }

    void setFinishData(String method, String message) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("method", method);
        resultIntent.putExtra("message", message);
        setResult(9876, resultIntent);
    }
}
