package cn.mql.chatbot.api.application.job;
import cn.mql.chatbot.api.domain.ai.IOpenAI;
import cn.mql.chatbot.api.domain.xunfei.XunFeiBigModelMain;
import cn.mql.chatbot.api.domain.zsxq.IZsxqApi;
import cn.mql.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import cn.mql.chatbot.api.domain.zsxq.model.vo.Topics;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

/**
 * ClassName: XingHuoChat
 * Package: cn.mql.chatbot.api.application.job
 * Description:
 *
 * @Author lmq
 * @Create 2024/3/7 23:13
 * @Version 1.0
 */
@EnableScheduling
@Configuration
public class XingHuoChat {
    private Logger logger = LoggerFactory.getLogger(XingHuoChat.class);
    @Value("${chatbot-api.groupId}")
    private String groupId;
    @Value("${chatbot-api.cookie}")
    private String cookie;

    @Resource
    private IZsxqApi zsxqApi;
//    private IOpenAI openAI;

    @Scheduled(cron = "0/5 * * * * ? ")
    public void run(){
        try{
            if(new Random().nextBoolean()){
                logger.info("随机暂停");
                return;
            }


            //1、检索问题
            UnAnsweredQuestionsAggregates unAnsweredQuestionsAggregates = zsxqApi.queryAnsweredQuestionsTopicId(groupId,cookie);
            logger.info("测试结果：{}", JSON.toJSONString(unAnsweredQuestionsAggregates));
            List<Topics> topics = unAnsweredQuestionsAggregates.getResp_data().getTopics();
            if(topics == null || topics.isEmpty()){
                logger.info("本次检索未查询到未回答的问题");
                return;
            }
            //防止一次回答过多问题，被知识星球风控检测后封号
            Topics topic = topics.get(0);

            //2、AI回答
            String answer = XunFeiBigModelMain.xingHuoApi(topic.getQuestion().getText().trim());
//            String answer = openAI.doChatGPT(topic.getQuestion().getText().trim());

            //3、回答问题
            boolean answer1 = zsxqApi.answer(groupId, cookie, topic.getTopic_id(), answer, false);
            logger.info("编号：{} 问题：{} 回答：{} 状态：{}", topic.getTopic_id(), topic.getQuestion(),answer, answer1);
        }catch (Exception e){
            logger.error("自动回答异常",e);
        }


    }
}
