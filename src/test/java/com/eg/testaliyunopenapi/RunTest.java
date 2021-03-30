package com.eg.testaliyunopenapi;

import com.aliyun.bssopenapi20171214.Client;
import com.aliyun.bssopenapi20171214.models.QueryBillRequest;
import com.aliyun.bssopenapi20171214.models.QueryBillResponseBody;
import com.aliyun.teaopenapi.models.Config;

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

    public static void main(String[] args) {
        int pageNum = 1;
        int pageSize = 50;
        QueryBillResponseBody.QueryBillResponseBodyData data;
        do {
            data = queryBill("2021-03", pageNum, pageSize);
            pageNum++;
            if (data == null)
                return;
            System.out.println(data.pageNum + " " + data.pageSize + " " + data.totalCount);
            List<QueryBillResponseBody.QueryBillResponseBodyDataItemsItem> itemList
                    = data.items.item;
            for (QueryBillResponseBody.QueryBillResponseBodyDataItemsItem
                    item : itemList) {
                if (item.pretaxAmount == 0.0)
                    continue;
                System.out.println(item.usageStartTime + " " + item.recordID
                        + " " + item.productName + " " + item.pipCode + " " + item.pretaxAmount);
            }
        } while (data.pageNum * data.pageSize < data.totalCount);
    }
}
