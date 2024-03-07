package cn.mql.chatbot.api.domain.zsxq.model.aggregates;

import cn.mql.chatbot.api.domain.zsxq.model.res.RespData;

/**
 * ClassName: UnAnsweredQuestionsAggregates
 * Package: cn.mql.chatbot.api.domain.zsxq.model.aggregates
 * Description: 未回答问题的聚合信息
 *
 * @Author lmq
 * @Create 2024/3/7 15:00
 * @Version 1.0
 */
public class UnAnsweredQuestionsAggregates {
    private boolean succeeded;
    private RespData resp_data;

    public boolean isSucceeded() {
        return succeeded;
    }

    public void setSucceeded(boolean succeeded) {
        this.succeeded = succeeded;
    }

    public RespData getResp_data() {
        return resp_data;
    }

    public void setResp_data(RespData resp_data) {
        this.resp_data = resp_data;
    }
}
