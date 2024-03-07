package cn.mql.chatbot.api.test;


import cn.mql.chatbot.api.domain.ai.IOpenAI;
import cn.mql.chatbot.api.domain.zsxq.IZsxqApi;
import cn.mql.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import cn.mql.chatbot.api.domain.zsxq.model.vo.Topics;
import cn.mql.chatbot.api.domain.zsxq.service.ZsxqApi;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * ClassName: springBootRunTest
 * Package: cn.mql.chatboot.api.test
 * Description:
 *
 * @Author lmq
 * @Create 2024/3/7 15:52
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class springBootRunTest {

    private Logger logger = LoggerFactory.getLogger(springBootRunTest.class);
    @Value("${chatbot-api.groupId}")
    private String groupId;
    @Value("${chatbot-api.cookie}")
    private String cookie;

    @Resource
    private IZsxqApi zsxqApi;
    @Resource
    private IOpenAI openAI;

    @Test
    public void test_zsxqApi() throws IOException {
        UnAnsweredQuestionsAggregates unAnsweredQuestionsAggregates = zsxqApi.queryAnsweredQuestionsTopicId(groupId, cookie);
        logger.info("测试结果：{}", JSON.toJSONString(unAnsweredQuestionsAggregates));
        List<Topics> topics = unAnsweredQuestionsAggregates.getResp_data().getTopics();
        for (Topics topic : topics) {
            //获取每个topicId
            String topicId = topic.getTopic_id();
            //获取每个问题
            String text = topic.getQuestion().getText();
            logger.info("topicId:{} text:{}", topicId, text);
        }

        //回答问题
        String topicId1 = "2855822824525151";
        String text = "回答";
        zsxqApi.answer(groupId,cookie,topicId1, text, false);
    }

    @Test
    public void test_openAi() throws IOException{
        String question = "帮我写一个冒泡算法";
        String response = openAI.doChatGPT(question);
        logger.info("测试结果：{}", response);
    }


}


