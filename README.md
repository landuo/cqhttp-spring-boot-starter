# cqhttp-spring-boot-starter

用于处理 [go-cqhttp](https://github.com/Mrs4s/go-cqhttp) 上报信息的 spring-boot-starter

## 使用

* 使用 `@Command` 注解添加命令

```java
// 方式一
@Command(value = "/查看武器", sample = "/查看武器 1 sda 2.3")
public String weapons(Integer version,String name,Double num) {
    return"查看武器 "+version;
}
        
// 方式二
// 私聊和群聊都可以使用该指令
@Command("/all")
public String ssss(MessageMsg msg) {
    return msg.getUserId()+" all ";
}
// 仅私聊可以使用该指令
@Command("/pr")
public String ssss(PrivateMessageMsg msg) {
    return msg.getUserId()+" pr ";
}
// 仅群聊可以使用该指令
@Command("/gr")
public String ssss(GroupMessageMsg msg) {
    return msg.getUserId()+" gr ";
}
```

* 使用 `@Listener` 注解监听事件

```java
@Listener(GroupFileUploadMsg.class)
public void groupFileUploadEvent(GroupFileUploadMsg msg) {
    System.out.println("上传文件事件, 群号: "+msg.getGroupId());
}
```
