package cn.mql.chatbot.api.domain.zsxq;

import cn.mql.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;

import java.io.IOException;

/**
 * ClassName: ZsxqApi
 * Package: cn.mql.chatbot.api.domain.zsxq
 * Description:
 *知识星球API接口
 * @Author lmq
 * @Create 2024/3/6 22:16
 * @Version 1.0
 */
public interface IZsxqApi {
    /**
     * 查询未回答的信息
     * @param groupId 知识星球的ID
     * @param cookie   因为没有做模拟登录，所以需要自己上传cookie
     * @throws IOException
     */
    UnAnsweredQuestionsAggregates queryAnsweredQuestionsTopicId(String groupId, String cookie) throws IOException;

    /**
     * 回答问题
     * @param groupId 知识星球的ID
     * @param cookie  浏览器缓存中的cookie
     * @param text    回答的信息
     * @param slienced  是否可见
     * @return
     */
    boolean answer(String groupId, String cookie, String topicId,String text, boolean slienced)throws IOException;
}
