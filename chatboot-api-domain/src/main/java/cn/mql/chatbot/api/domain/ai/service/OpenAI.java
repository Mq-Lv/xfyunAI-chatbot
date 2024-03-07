package cn.mql.chatbot.api.domain.ai.service;

import cn.mql.chatbot.api.domain.ai.IOpenAI;
import cn.mql.chatbot.api.domain.ai.model.aggregates.AIAnswer;
import cn.mql.chatbot.api.domain.ai.model.vo.Choices;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * ClassName: OpenAI
 * Package: cn.mql.chatbot.api.domain.ai.service
 * Description:
 *
 * @Author lmq
 * @Create 2024/3/7 17:23
 * @Version 1.0
 */
@Service
public class OpenAI implements IOpenAI {
    private Logger logger = LoggerFactory.getLogger(OpenAI.class);
    @Value("${chatbot-api.openAiKey}")
    private String OpenAIKey;

    @Override
    public String doChatGPT(String question) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build(); //封装接受到的信息
        /*
         * 相关url 以及 请求头的配置信息可以查看
         * https://platform.openai.com/docs/api-reference/making-requests
         * */
        HttpPost post = new HttpPost("https://api.openai.com/v1/chat/completions");
        post.addHeader("Content-Type","application/json");
        post.addHeader("Authorization","Bearer " + OpenAIKey);
        String paramJson = "{\"model\": \"text-ada-001\", \"prompt\": \"" + question + "\", \"temperature\": 0, \"max_tokens\": 1024}";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "utf-8"));
        post.setEntity(stringEntity);
        CloseableHttpResponse response = httpClient.execute(post);
        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String jsonStr = EntityUtils.toString(response.getEntity());
            logger.info("chatGPT回答： jsonStr：{}", jsonStr);
            AIAnswer aiAnswer = JSON.parseObject(jsonStr, AIAnswer.class);
            StringBuilder answers = new StringBuilder();
            List<Choices> choices =aiAnswer.getChoices();
            for(Choices choice : choices){
                answers.append(choice.getText());
            }
            return answers.toString();
        }else{
            throw new RuntimeException("api.openai.com Err Code is " + response.getStatusLine().getStatusCode());
        }
    }
}
