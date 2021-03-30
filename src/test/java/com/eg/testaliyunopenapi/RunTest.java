package com.eg.testaliyunopenapi;

import com.aliyun.bssopenapi20171214.Client;
import com.aliyun.bssopenapi20171214.models.QueryBillRequest;
import com.aliyun.bssopenapi20171214.models.QueryBillResponseBody;
import com.aliyun.teaopenapi.models.Config;
import com.eg.testaliyunopenapi.bean.Bill;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class RunTest {
    public static Client createClient(String accessKeyId, String accessKeySecret) {
        Config config = new Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret);
        config.endpoint = "business.aliyuncs.com";
        try {
            return new Client(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static QueryBillResponseBody.QueryBillResponseBodyData queryBill(
            String billingCycle, int pageNum, int pageSize) {
        QueryBillRequest queryBillRequest = new QueryBillRequest();
        queryBillRequest.billingCycle = billingCycle;
        queryBillRequest.isHideZeroCharge = true;
        queryBillRequest.pageNum = pageNum;
        queryBillRequest.pageSize = pageSize;
        try {
            Client client = createClient("LTAI5tP5hka5v6HfWg3haCkW",
                    "Z3D6HDKG8GAP3fJTidnVK3prn3YYPj");
            if (client == null)
                return null;
            return client.queryBill(queryBillRequest).body.data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Bill> getMonthBills(String billingCycle) {
        System.out.println("query " + billingCycle);
        int pageNum = 1;
        int pageSize = 200;
        QueryBillResponseBody.QueryBillResponseBodyData data;
        List<Bill> result = new ArrayList<>();
        do {
            data = queryBill(billingCycle, pageNum, pageSize);
            pageNum++;
            if (data == null)
                return null;
            data.items.item.forEach(each -> {
                if (each.paymentAmount == 0)
                    return;
                Bill bill = new Bill();
                BeanUtils.copyProperties(each, bill);
                result.add(bill);
            });
        } while (data.pageNum * data.pageSize < data.totalCount);
        return result;
    }

    public static void main(String[] args) {
        float total = 0;
        int count = 0;
        for (int year = 2017; year <= 2021; year++) {
            for (int month = 1; month <= 12; month++) {
                List<Bill> monthBills = getMonthBills(year + "-" + month);
                if (monthBills == null)
                    continue;
                count += monthBills.size();
                for (Bill bill : monthBills) {
                    bill.setYear(year);
                    bill.setMonth(month);
                    System.out.println(bill);
                    total += bill.getPaymentAmount();
                }
            }
        }
        System.out.println(count);
        System.out.println(total);
    }
}
