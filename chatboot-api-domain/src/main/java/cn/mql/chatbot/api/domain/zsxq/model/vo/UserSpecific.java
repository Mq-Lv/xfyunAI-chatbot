package cn.mql.chatbot.api.domain.zsxq.model.vo;
/**
 * ClassName: UserSpecific
 * Package: cn.mql.chatbot.api.domain.zsxq.model.vo
 * Description: 未回答问题响应
 *
 * @Author lmq
 * @Create 2024/3/7 15:00
 * @Version 1.0
 */

public class UserSpecific {

    private boolean liked;

    private boolean subscribed;

    public void setLiked(boolean liked){
        this.liked = liked;
    }
    public boolean getLiked(){
        return this.liked;
    }
    public void setSubscribed(boolean subscribed){
        this.subscribed = subscribed;
    }
    public boolean getSubscribed(){
        return this.subscribed;
    }

}