package kr.co.bootpay.bootpay_api;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kr.co.bootpay.Bootpay;
import kr.co.bootpay.bootpay_api.model.Payload;
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

public class BootpayActivity extends Activity {
    public static String TAG = "BootpayActivity";

    Payload initPayload(JsonObject jsonObject) {
        List<String> methods = new ArrayList<String>();
        Payload payload = new Payload();

        payload.setApplication_id(jsonObject.get("application_id").getAsString());
        payload.setPg(jsonObject.get("pg").getAsString());
        payload.setMethod(jsonObject.get("method").getAsString());

        JsonArray jsonArray = jsonObject.get("methods").getAsJsonArray();
        Iterator<JsonElement> iterator = jsonArray.iterator();
        while (iterator.hasNext()) {
            methods.add(iterator.next().getAsString());
        }
        payload.setMethods(methods);

        payload.setName(jsonObject.get("name").getAsString());
        payload.setPrice(jsonObject.get("price").getAsDouble());
        payload.setTax_free(jsonObject.get("tax_free").getAsDouble());
        payload.setOrder_id(jsonObject.get("order_id").getAsString());
        payload.setUse_order_id(jsonObject.get("use_order_id").getAsBoolean());
        payload.setShow_agree_window(jsonObject.get("show_agree_window").getAsBoolean());
        payload.setParams(jsonObject.get("params").getAsString());
        payload.setAccount_expire_at(jsonObject.get("account_expire_at").getAsString());
        payload.setShow_agree_window(jsonObject.get("show_agree_window").getAsBoolean());
        payload.setUx(jsonObject.get("ux").getAsString());
        payload.setEasyPayUserToken(jsonObject.get("user_token").getAsString());
        return payload;
    }

    BootUser initBootUser(JsonObject jsonObject) {
        BootUser bootUser = new BootUser();
        bootUser.setArea(jsonObject.get("area").getAsString());
        bootUser.setAddr(jsonObject.get("addr").getAsString());
        bootUser.setBirth(jsonObject.get("birth").getAsString());
        bootUser.setPhone(jsonObject.get("phone").getAsString());
        bootUser.setID(jsonObject.get("id").getAsString());
        bootUser.setEmail(jsonObject.get("email").getAsString());
        bootUser.setUsername(jsonObject.get("username").getAsString());
        return bootUser;
    }

    List<Item> initItems(JsonArray jsonArray) {
        List<Item> items = new ArrayList<>();
        Iterator<JsonElement> iterator = jsonArray.iterator();
        while (iterator.hasNext()) {
            JsonObject currentObject = iterator.next().getAsJsonObject();
            Item item = new Item(
                    currentObject.get("item_name").getAsString(),
                    currentObject.get("qty").getAsInt(),
                    currentObject.get("unique").getAsString(),
                    currentObject.get("price").getAsDouble(),
                    currentObject.get("cat1").getAsString(),
                    currentObject.get("cat2").getAsString(),
                    currentObject.get("cat3").getAsString()
            );
            items.add(item);
        }
        return items;
    }

    BootExtra initBootExtra(JsonObject jsonObject) {
        BootExtra bootExtra = new BootExtra();
        bootExtra.setEndAt(jsonObject.get("end_at").getAsString());
        bootExtra.setExpireMonth(jsonObject.get("expire_month").getAsInt());
        bootExtra.setApp_scheme(jsonObject.get("app_scheme").getAsString());
        bootExtra.setApp_scheme_host(jsonObject.get("app_scheme_host").getAsString());
        bootExtra.setStartAt(jsonObject.get("start_at").getAsString());
        bootExtra.setDisp_cash_result(jsonObject.get("disp_cash_result").getAsString());
        bootExtra.setVbankResult(jsonObject.get("vbank_result").getAsBoolean());
        bootExtra.setEscrow(jsonObject.get("escrow").getAsInt());
        return bootExtra;
    }


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

        Log.d(TAG, payloadString);
        Log.d(TAG, userString);
        Log.d(TAG, itemsString);
        Log.d(TAG, extraString);
        Payload payload = new Gson().fromJson("{\"application_id\": \"5f118ed58f0751002136c44d\"}", Payload.class);
        JsonElement payloadJsonElement = JsonParser.parseString(payloadString);
        payload = initPayload(payloadJsonElement.getAsJsonObject());
        Log.d(TAG, payload.toString());
        if (userString != null && !userString.isEmpty())
//            bootUser = new Gson().fromJson(userString, BootUser.class);
            bootUser = initBootUser(JsonParser.parseString(userString).getAsJsonObject());
        if (itemsString != null && !itemsString.isEmpty())
//            items = new Gson().fromJson(itemsString, new TypeToken<List<Item>>() {
//            }.getType());
            items = initItems(JsonParser.parseString(itemsString).getAsJsonArray());
        if (extraString != null && !extraString.isEmpty())
//            bootExtra = new Gson().fromJson(extraString, BootExtra.class);
            bootExtra = initBootExtra(JsonParser.parseString(extraString).getAsJsonObject());

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
