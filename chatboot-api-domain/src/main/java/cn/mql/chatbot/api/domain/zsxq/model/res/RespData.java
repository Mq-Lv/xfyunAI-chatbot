package cn.mql.chatbot.api.domain.zsxq.model.res;

import cn.mql.chatbot.api.domain.zsxq.model.vo.Topics;

import java.util.List;

/**
 * ClassName: RespData
 * Package: cn.mql.chatbot.api.domain.zsxq.model.res
 * Description: 未回答问题信息
 *
 * @Author lmq
 * @Create 2024/3/7 15:00
 * @Version 1.0
 */

public class RespData {

    //每个topics对应一个问题
    private List<Topics> topics;

    public List<Topics> getTopics() {
        return topics;
    }

    public void setTopics(List<Topics> topics) {
        this.topics = topics;
    }

}