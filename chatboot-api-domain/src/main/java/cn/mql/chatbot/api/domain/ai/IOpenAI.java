package cn.mql.chatbot.api.domain.ai;

import java.io.IOException;

/**
 * ClassName: IOpenAI
 * Package: cn.mql.chatbot.api.domain.ai
 * Description:
 * openai接口
 *
 * @Author lmq
 * @Create 2024/3/7 17:21
 * @Version 1.0
 */
public interface IOpenAI {

    String doChatGPT(String question) throws IOException;
}
