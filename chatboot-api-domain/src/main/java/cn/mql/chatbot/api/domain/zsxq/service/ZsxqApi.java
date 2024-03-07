package cn.mql.chatbot.api.domain.zsxq.service;

import cn.mql.chatbot.api.domain.zsxq.IZsxqApi;
import cn.mql.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import cn.mql.chatbot.api.domain.zsxq.model.req.AnswerReq;
import cn.mql.chatbot.api.domain.zsxq.model.req.ReqData;
import cn.mql.chatbot.api.domain.zsxq.model.res.AnswerRes;
import com.alibaba.fastjson.JSON;
import net.sf.json.JSONObject;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * ClassName: ZsxqApi
 * Package: cn.mql.chatbot.api.domain.zsxq.service
 * Description:
 *
 * @Author lmq
 * @Create 2024/3/7 15:07
 * @Version 1.0
 */
@Service
public class ZsxqApi implements IZsxqApi {
    private Logger logger = LoggerFactory.getLogger(ZsxqApi.class);

    @Override
    public UnAnsweredQuestionsAggregates queryAnsweredQuestionsTopicId(String groupId, String cookie) throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build(); //封装接受到的信息

        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/" + groupId + "/topics?scope=unanswered_questions&count=20");
        get.addHeader("cookie",cookie);
        get.addHeader("Content-Type","application/json;charset=utf8");

        CloseableHttpResponse response = httpClient.execute(get);
        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            //HttpClient 提供了 EntityUtils工具类，可以很好的把 响应的 HttpEntity 转换为 字节数组或者字符串
            String jsonStr = EntityUtils.toString(response.getEntity());
            logger.info("获取未回答问题数据：groupId：{} jsonStr：{}", groupId, jsonStr);
            //转换为对象
            return JSON.parseObject(jsonStr,UnAnsweredQuestionsAggregates.class);
        }else{
            throw new RuntimeException("请求未回答问题失败，错误码：" + response.getStatusLine().getStatusCode());
        }
    }

    @Override
    public boolean answer(String groupId, String cookie, String topicId,String text, boolean slienced) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build(); //封装接受到的信息
        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/" + topicId + "/answer");
        post.addHeader("cookie",cookie);
        post.addHeader("Content-Type","application/json;charset=utf8");
        //告诉服务器我这是从浏览器过去的
        post.addHeader("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36 Edg/122.0.0.0");
        /*
         * HttpClient 中发送 JSON 数据可以使用 StringHttpEntity 类实现
         * 其中下面JSON数据的格式需要参照 浏览器 发送的POST请求的请求体的写法
         * */
//        String paramJson = "{\"req_data\":{\"text\":\"我不知道\\n\",\"image_ids\":[]}}";

        //封装回答信息
        AnswerReq answerReq = new AnswerReq(new ReqData(text,slienced));
        //将回答信息转为json
        String paramJson = JSONObject.fromObject(answerReq).toString();
        //设置POST请求体
        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "utf-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);

        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String jsonStr = EntityUtils.toString(response.getEntity());
            logger.info("回答问题结果，groupId：{} topicId：{} jsonStr：{}", groupId, topicId, jsonStr);
            AnswerRes answerRes = JSON.parseObject(jsonStr,AnswerRes.class);
            return answerRes.isSucceeded();
        }else{
            throw new RuntimeException("回答问题错误码：" + response.getStatusLine().getStatusCode());
        }

    }
}
