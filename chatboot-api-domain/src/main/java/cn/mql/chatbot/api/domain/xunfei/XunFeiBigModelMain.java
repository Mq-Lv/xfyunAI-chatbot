package cn.mql.chatbot.api.domain.xunfei;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.HMac;
import cn.mql.chatbot.api.domain.zsxq.model.res.AnswerRes;
import com.alibaba.fastjson.JSONObject;
import com.xufei.domain.RoleContent;
import com.xufei.domain.request.Request;
import com.xufei.domain.request.header.Header;
import com.xufei.domain.request.parameter.Chat;
import com.xufei.domain.request.parameter.Parameter;
import com.xufei.domain.request.payload.Message;
import com.xufei.domain.request.payload.Payload;
import com.xufei.domain.response.Result;
import com.xufei.domain.response.payload.Text;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 讯飞大模型测试类
 *
 * @author Linzj
 * @date 2023/10/19/019 16:25
 */
public class XunFeiBigModelMain {

    private static final Logger log = LoggerFactory.getLogger(XunFeiBigModelMain.class);

    /**
     * 请求地址
     */
    private static final String HOST_URL = "https://spark-api.xf-yun.com/v3.5/chat";

    /**
     * v2版本
     */
    private static final String DOMAIN_2 = "generalv2";

    /**
     * APPID
     */
    private static final String APPID = "a01aacd2";

    /**
     * APISecret
     */
    private static final String API_SECRET = "NDExOGVjNWZkZDg2NzY1MTM2NzE0YmI1";

    /**
     * APIKey
     */
    private static final String API_KEY = "7a4f189b14810840a185219033366905";

    /**
     * user表示是用户的问题
     */
    private static final String ROLE_USER = "user";

    /**
     * assistant表示AI的回复
     */
    private static final String ROLE_ASSISTANT = "assistant";

    /**
     * 接口响应内容集合
     */
    private static final LinkedList<Result> RESULT_LINKED_LIST = new LinkedList<>();

    /**
     * 对话历史存储集合
     */

    public static String answer = "";

    public static boolean resultEnd = true;

    public static String xingHuoApi(String content) throws MalformedURLException, URISyntaxException {
            resultEnd = true;
            websocketClient(getAuthUrl(), createReqParams(content));
            return answer;
    }


    /**
     * websocket 连接
     *
     * @param authUrl   鉴权地址
     * @param reqParams 请求参数
     * @throws URISyntaxException 异常
     */

    private static void websocketClient(String authUrl, String reqParams) throws URISyntaxException {
        String url = authUrl.replace("http://", "ws://").replace("https://", "wss://");
        URI uri = new URI(url);
        String answer = "";
        /*
        * http和websocket的区别
        * https://cloud.tencent.com/developer/article/2168215
        * */
        WebSocketClient webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                RESULT_LINKED_LIST.clear();
                send(reqParams);
            }

            @Override
            public void onMessage(String s) {
                // 错误码，0表示正常
                final int successCode = 0;
                // 会话状态，2代表最后一个结果
                final int lastStatus = 2;

                Result result = JSONObject.parseObject(s, Result.class);
                com.xufei.domain.response.header.Header header = result.getHeader();
                if (Objects.equals(successCode, header.getCode())) {
                    RESULT_LINKED_LIST.add(result);
                } else {
                    log.error("大模型接口响应异常，错误码：{}，sid：{}", header.getCode(), header.getSid());
                }

                // 如果是最后的结果，整合答复数据打印出来
                if (Objects.equals(lastStatus, header.getStatus())) {
                    printReply();
                }
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                // log.info("WebSocket连接已关闭，原因：{}，状态码：{}，是否远程关闭：{}", i, s, b);
            }

            @Override
            public void onError(Exception e) {
                log.error("大模型接口调用发生异常，异常原因:{},异常位置:{}", e.getMessage(), e.getStackTrace()[0]);
            }
        };
        webSocketClient.connect();
    }

    /**
     * 生成请求参数
     *
     * @param content 对话内容
     * @return 请求参数
     */
    public static String createReqParams(String content) {
        // 组装接口请求参数
        Header header = new Header();
        header.setAppId(APPID);

        Chat chat = new Chat();
        chat.setDomain(DOMAIN_2);
        Parameter parameter = new Parameter();
        parameter.setChat(chat);

        Message message = new Message();

        com.xufei.domain.request.payload.Text text = new com.xufei.domain.request.payload.Text();
        text.setRole(ROLE_USER);
        text.setContent(content);
        message.setText(Collections.singletonList(text));


        Payload payload = new Payload();
        payload.setMessage(message);
        Request request = new Request();
        request.setHeader(header);
        request.setParameter(parameter);
        request.setPayload(payload);
        return JSONObject.toJSONString(request);
    }


    /**
     * URL鉴权
     *
     * @return 请求url
     * @throws MalformedURLException 异常
     */
    private static String getAuthUrl() throws MalformedURLException {
        //获取当前日期时间，并将其格式化为 HTTP 时间格式
        String date = DateUtil.format(new Date(), DatePattern.HTTP_DATETIME_FORMAT);
        //创建url对象
        URL url = new URL(HOST_URL);
        //利用上方的date动态拼接生成字符串 preStr
        String preStr = "host: " + url.getHost() + "\n" +
                "date: " + date + "\n" +
                "GET " + url.getPath() + " HTTP/1.1";
        //利用hmac-sha256算法结合APISecret对上一步的 preStr 签名，获得签名后的摘要digest。
        HMac hMac = SecureUtil.hmacSha256(API_SECRET.getBytes(StandardCharsets.UTF_8));
        byte[] digest = hMac.digest(preStr);

        //将上方的digest进行base64编码生成signature
        String signature = Base64.encode(digest);
        //拼接上api-key，签名算法等
        String authorizationOrigin = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"",
                API_KEY, "hmac-sha256", "host date request-line", signature);
        //再进行base64编码
        String authorization = Base64.encode(authorizationOrigin);
        //将将鉴权参数组合成最终的键值对，生成最终的url
        return UriComponentsBuilder.fromUriString(HOST_URL)
                .queryParam("authorization", authorization)
                .queryParam("date", date)
                .queryParam("host", url.getHost()).toUriString();
    }



    /**
     * 打印星火认知大模型回复内容
     */
    private static void printReply() {
        String content = RESULT_LINKED_LIST.stream()
                .map(item -> item.getPayload().getChoices().getText())
                .flatMap(Collection::stream)
                .map(Text::getContent)
                .collect(Collectors.joining());
        RoleContent roleContent = new RoleContent();
        roleContent.setRole(ROLE_ASSISTANT);
        roleContent.setContent(content);
        answer = content;
    }
}
