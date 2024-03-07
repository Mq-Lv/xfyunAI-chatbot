package cn.mql.chatbot.api.domain.zsxq.model.req;

/**
 * ClassName: AnswerReq
 * Package: cn.mql.chatbot.api.domain.zsxq.model.req
 * Description:请求问答信息
 *
 * @Author lmq
 * @Create 2024/3/7 15:24
 * @Version 1.0
 */
public class AnswerReq {
    private ReqData req_data;

    public AnswerReq(ReqData req_data) {
        this.req_data = req_data;
    }

    public ReqData getReq_data() {
        return req_data;
    }

    public void setReq_data(ReqData req_data) {
        this.req_data = req_data;
    }
}
