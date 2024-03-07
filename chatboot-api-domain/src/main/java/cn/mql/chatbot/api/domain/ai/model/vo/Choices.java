package cn.mql.chatbot.api.domain.ai.model.vo;

/**
 * ClassName: Choice
 * Package: cn.mql.chatbot.api.domain.ai.model.vo
 * Description:
 *
 * @Author lmq
 * @Create 2024/3/7 17:26
 * @Version 1.0
 */
public class Choices {
    private String text;
    private int index;
    private String logprobs;
    private String finish_reasom;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getLogprobs() {
        return logprobs;
    }

    public void setLogprobs(String logprobs) {
        this.logprobs = logprobs;
    }

    public String getFinish_reasom() {
        return finish_reasom;
    }

    public void setFinish_reasom(String finish_reasom) {
        this.finish_reasom = finish_reasom;
    }
}
