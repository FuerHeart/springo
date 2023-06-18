package com.zh.springo.controller;


import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/pay")
public class AlipayController {
    private String serverUrl = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";
    private String appId = "2021000122662324";
    private String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCA9Rml0314T26FAjfNsU4IO3yWAkF9YvzdtJnfJSF/Z9Q2W9JE0/1RGiO5xOWlGxD+qf027winUnPl5vyMWHKArfzAdVkys2EL2cpIFiF5ubQRUIjsEPMVtateGGcUZeKg49auWPQRRw+7K/OCLKF0WXuYpqytNMwpmSMenxJWSowzK+8V3+v2giN1bBre8Mp7vDKnllfgZC7Sck06dMxVBNVzvGd61DPIX6W49vNf6TqRQUsF/srNcOOsEZiIPG98zlCyDTB1geDDJjJ+cW71mKrsFA+7w0W7bUttnRy/5OQJPHq61xf41Z9tL4QL1FArus3HJQN7kFg9oFVlkiG/AgMBAAECggEAY97uHQncWweRPTtJgmh1WVTQmNBobbXyxbTvkeyMs8HyjKKyBpHy1hObZWyCuUpr7/4wV5HprsPhdERehEafbt7QJzD6gpR/bREfYpKZHE9gkCRB1ikOSVrnu7jEY/y/9gBdq6rCYnsZSMiyDreH1rbSTJeQlZeVIgYyN63UIrUA2IaUiF7RmbstpoV7qPzJFriPcDr9Kp+6PCnHsfd5KDtgt+Sz2FtvJrurAGqJq34rjo5kzgkQaC0TIokC9kRLiqL6vj9ux3goodrlQy0Os5kzMJAaNvIr+VsGyyNSFWgJj9t/YMQD4FxK0KrtEYFcHwrqDB1w6mzx0hcQXTdtUQKBgQDFWxqTk6uom1jJuL4HFzHw9e0EqJcMY8l6+y5ruOfIaD5BxQziDpcK5Vulnm0TLj8TqowMjfGgOg1iT57fnL1bOv8KuAw6EpnJgttW8Gw3/k6c2SRTZ8iesGCqPL1CgMLEaLfPNX06lcYcv0SpXocs7S74J+DIYw5yZM4Gd6em6QKBgQCnRuoBUKBiCj/YGMZWU4c3PVyDIxqa8bub9XeztZPCISiF/z+s5o+HL38Kh2MNiwHIiZQsv263iiqHT02bdmW5h2MD+hA64dXJRfliSRB/lsF5VRcIh7Tuk8cW6cvMD0bZDyjZzUTmPKvMRjJ89dRdZx0XbIScOHX5FDVPwAXqZwKBgHBnvlBVqEylBkIGff0t4dz7RR8FItsErsnj7IGgVCuZ6/Or1vWeF7s1/SfsfvWQvo5l8dRouDlpNxxKexOjg1pF1ON3BkfBNmRGqsTutMSDjKTbLExw7RwtGzF63NTW35fciOx067mFesv+yI2q8FTPVpQyql2Xxa+z6IBYgF2ZAoGBAKFSfrMt8r8aa2AY5I60l57qiqHd5PaxUuGh5gNmhk2edNplxB8XbCxP3hFlaF3TRbEFHeXZ6QkRMtqz3ICluo0myJM5QxXGXstxtITZOhQSbBx7t27+kbvUwRiT+Xue8BC90gPbNytkL7sdp+tjmZ9Kpn/EpE+VFBFXL+LtqcbZAoGAHLagCxiSP2PxyCLDrHYr7a9TksPNvL4Xlb11uhp2XuKVqd+/CffGmTd9bz+bWWbtm4HgYwZmVmp4tSwlH9P1CxasHQxt0BQvh//bJdRqA2z50ANiIw07/vl0x/ASqU2gwvaEjBaphiKe7qnNdE7OROq+doY9dcU98I0eGSFnguA=";
    private String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqsQm0StjbV4uwurdEsmo3aiZVK1Ymap38CFKrLu82d1d1moqUCeS+gIWPYFeB5+fTxVoo1QTd1uuJexc6z64XhxP1dQhgm31KvaBrwi7hBvUCcduTlijkXPWC2nARycZz8NqJBGZfE/S1sY8lMp14kkZajUUDrK7EKh/2ksfoTOh3F6BgpE6cTn5r1ndJJdKpEUn5hvmBqCshTqvE1XP3gAHtt9FXo6RR56YMBjcn8vV2vUEgwEbGKoaYVAMu9jow04ckd1k3Hkz/gJiQ7opDKRXJz41E1GhvLw4EUIIOjwVvE6yMfGz+Wed2C3dHhCp1QR1CnOxT2U44n0u5pW6HwIDAQAB";

    //创建订单接口
    @ResponseBody
    @GetMapping("/create")
    public String create() {
        //创建连接
        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, appId, privateKey, "json", "UTF-8", alipayPublicKey, "RSA2");
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //订单信息
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", "1659488292496347137");
        bizContent.put("total_amount", 2220.00);
        bizContent.put("subject", "航空路线推荐系统");
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        request.setBizContent(bizContent.toString());

        AlipayTradePagePayResponse response = null;
        try {
            response = alipayClient.pageExecute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (response.isSuccess()) {
            //成功
            return response.getBody();
        } else {
            //失败
            return "调用失败";
        }
    }
}
