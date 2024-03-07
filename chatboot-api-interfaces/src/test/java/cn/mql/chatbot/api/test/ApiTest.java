package cn.mql.chatbot.api.test;

import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

/**
 * ClassName: ApiTest
 * Package: cn.mql.chatboot.api.test
 * Description:
 *单元测试
 * @Author lmq
 * @Create 2024/3/6 20:47
 * @Version 1.0
 */
public class ApiTest {
    @Test
    public void query_unanswered_questions() throws IOException {
        //关于HttpClient工具类的介绍  https://juejin.cn/post/7052900785381703694

        //1、HttpClient 是三方工具，首先需要引入依赖。如下：
        /*
        * <dependency>
            <groupId>org.apache.httpcomponents.client5</groupId>
             <artifactId>httpclient5</artifactId>
             <version>5.1.1</version>
          </dependency>
          * */

        /*
        * 2、获取客户端
        * 即获取HttpClient对象
        * HttpClientBuilder.create()返回一个HttpClientBuilder对象，.build返回一个CloseableHttpClient对象
        * */
        CloseableHttpClient httpClient = HttpClientBuilder.create().build(); //封装接受到的信息

        /*
        * 3、创建 GET  请求，并设置请求头
        * 仿照浏览器的请求设置
        * */
        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/15555558525252/topics?scope=unanswered_questions&count=20");
        get.addHeader("cookie","\n" +
                "abtest_env=product; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22212541811411521%22%2C%22first_id%22%3A%2218dcf37799910c5-0c282c632b55798-4c657b58-2073600-18dcf37799a1db4%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMThkY2YzNzc5OTkxMGM1LTBjMjgyYzYzMmI1NTc5OC00YzY1N2I1OC0yMDczNjAwLTE4ZGNmMzc3OTlhMWRiNCIsIiRpZGVudGl0eV9sb2dpbl9pZCI6IjIxMjU0MTgxMTQxMTUyMSJ9%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22212541811411521%22%7D%2C%22%24device_id%22%3A%2218dcf37799910c5-0c282c632b55798-4c657b58-2073600-18dcf37799a1db4%22%7D; tfstk=ecE2EzaeoiI4wBJQu4mZ8hWGR-nx_DCCglGsIR2ihjcDchaPSR2mhVfxclPz9JUfBVmcQ5P3gVNaBtHis5VZ1r_5A-exXcfBbMsQHF_Fdy5QoMebZcnGO_TQY4F-XWPQzqjYP4fymwuNtJhuVrixHq-DpXxMjojtYX-iMYHT4H3mT-q2jhvn3ql3nbjPtCHl_4ZTuCYZoYHrOTWrQUQngJ_CHl89W4mKUX6jhFLtoYHrOTWyWF3ovYlChx1..; zsxqsessionid=0af7a4e153f1692eeb45b0de206fd466; zsxq_access_token=40746DA0-5B52-EF32-284D-03761FD91FED_1D3B856ECC190BB7");
        get.addHeader("Content-Type","application/json;charset=utf8");


        /*
        * 4、
        * 调用 HttpClient 的 execute 方法执行请求，并获取响应
        * 响应封装成了CloseableHttpResponse对象
        * getCode() 获取响应状态
        * getEntity() 获取响应数据
        * */
        CloseableHttpResponse response = httpClient.execute(get);
        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            //HttpClient 提供了 EntityUtils工具类，可以很好的把 响应的 HttpEntity 转换为 字节数组或者字符串
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        }else{
            System.out.println(response.getStatusLine().getStatusCode());
        }

    }
    @Test
    public void answer() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build(); //封装接受到的信息
        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/5122155118412854/answer");
        post.addHeader("cookie","abtest_env=product; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22212541811411521%22%2C%22first_id%22%3A%2218dcf37799910c5-0c282c632b55798-4c657b58-2073600-18dcf37799a1db4%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMThkY2YzNzc5OTkxMGM1LTBjMjgyYzYzMmI1NTc5OC00YzY1N2I1OC0yMDczNjAwLTE4ZGNmMzc3OTlhMWRiNCIsIiRpZGVudGl0eV9sb2dpbl9pZCI6IjIxMjU0MTgxMTQxMTUyMSJ9%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22212541811411521%22%7D%2C%22%24device_id%22%3A%2218dcf37799910c5-0c282c632b55798-4c657b58-2073600-18dcf37799a1db4%22%7D; tfstk=ecE2EzaeoiI4wBJQu4mZ8hWGR-nx_DCCglGsIR2ihjcDchaPSR2mhVfxclPz9JUfBVmcQ5P3gVNaBtHis5VZ1r_5A-exXcfBbMsQHF_Fdy5QoMebZcnGO_TQY4F-XWPQzqjYP4fymwuNtJhuVrixHq-DpXxMjojtYX-iMYHT4H3mT-q2jhvn3ql3nbjPtCHl_4ZTuCYZoYHrOTWrQUQngJ_CHl89W4mKUX6jhFLtoYHrOTWyWF3ovYlChx1..; zsxqsessionid=0af7a4e153f1692eeb45b0de206fd466; zsxq_access_token=B173A80D-5587-E178-E55D-73EC36E0B5A7_1D3B856ECC190BB7");
        post.addHeader("Content-Type","application/json;charset=utf8");

        /*
        * HttpClient 中发送 JSON 数据可以使用 StringHttpEntity 类实现
        * 其中下面JSON数据的格式需要参照 浏览器 发送的POST请求的请求体的写法
        * */
        String paramJson = "{\"req_data\":{\"text\":\"我不知道\\n\",\"image_ids\":[]}}";
        //设置POST请求体
        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "utf-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);

        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        }else{
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void test_chatGPT() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build(); //封装接受到的信息
        /*
        * 相关url 以及 请求头的配置信息可以查看
        * https://platform.openai.com/docs/api-reference/making-requests
        * */

        HttpPost post = new HttpPost("https://api.openai.com/v1/chat/completions");
        post.addHeader("Content-Type","application/json");
        post.addHeader("Authorization","Bearer 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");
        String paramJson = "{\"model\": \"text-davinci-003\", \"prompt\": \"帮我写一个java冒泡排序\", \"temperature\": 0, \"max_tokens\": 1024}";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "utf-8"));
        post.setEntity(stringEntity);
        CloseableHttpResponse response = httpClient.execute(post);
        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        }else{
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

}
