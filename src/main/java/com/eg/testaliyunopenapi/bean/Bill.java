package com.eg.testaliyunopenapi.bean;

import com.alibaba.fastjson.JSON;
import lombok.Data;

@Data
public class Bill {
    private Float afterTaxAmount;
    private String commodityCode;
    private String currency;
    private Float deductedByCashCoupons;
    private Float deductedByCoupons;
    private Float deductedByPrepaidCard;
    private Float invoiceDiscount;
    private String item;
    private Float outstandingAmount;
    private String ownerID;
    private Float paymentAmount;
    private String paymentCurrency;
    private String paymentTime;
    private String paymentTransactionID;
    private String pipCode;
    private Float pretaxAmount;
    private Float pretaxAmountLocal;
    private Float pretaxGrossAmount;
    private String productCode;
    private String productDetail;
    private String productName;
    private String productType;
    private String recordID;
    private String roundDownDiscount;
    private String status;
    private String subOrderId;
    private String subscriptionType;
    private Float tax;
    private String usageEndTime;
    private String usageStartTime;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
