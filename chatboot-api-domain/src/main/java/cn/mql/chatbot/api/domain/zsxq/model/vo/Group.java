package cn.mql.chatbot.api.domain.zsxq.model.vo;

/**
 * ClassName: Group
 * Package: cn.mql.chatbot.api.domain.zsxq.model.vo
 * Description: 未回答问题响应
 *
 * @Author lmq
 * @Create 2024/3/7 15:00
 * @Version 1.0
 */

public class Group {
    private String group_id;


private String name;

private String type;

private String background_url;

public void setGroup_id(String group_id){
    this.group_id = group_id;
}
public String getGroup_id(){
    return this.group_id;
}
public void setName(String name){
    this.name = name;
}
public String getName(){
    return this.name;
}
public void setType(String type){
    this.type = type;
}
public String getType(){
    return this.type;
}
public void setBackground_url(String background_url){
    this.background_url = background_url;
}
public String getBackground_url(){
    return this.background_url;
}

}