package com.ellt.web.controller;
import com.github.sd4324530.fastweixin.handle.EventHandle;
import com.github.sd4324530.fastweixin.handle.MessageHandle;
import com.github.sd4324530.fastweixin.message.BaseMsg;
import com.github.sd4324530.fastweixin.message.TextMsg;
import com.github.sd4324530.fastweixin.message.req.TextReqMsg;
import com.github.sd4324530.fastweixin.servlet.WeixinControllerSupport;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

/**
 * 微信公众号控制器
 * Created by Irving on 2014/7/6.
 */
@RestController
@RequestMapping("/weixin")
public class WeixinController extends WeixinControllerSupport {
    private static final Logger logger = LoggerFactory.getLogger(WeixinController.class);
    //令牌
    private static final String TOKEN = "e_llt";
    //设置TOKEN，用于绑定微信服务器
    @Override
    protected String getToken() {
        return TOKEN;
    }
    //不再强制重写，有加密需要时自行重写该方法[使用安全模式时设置：APPID]
    @Override
    protected String getAppId() {
        return null;
    }
    //不再强制重写，有加密需要时自行重写该方法[使用安全模式时设置：密钥]
    @Override
    protected String getAESKey() {
        return null;
    }
    //重写父类方法，处理对应的微信消息
    @Override
    protected BaseMsg handleTextMsg(TextReqMsg msg) {
        String content = msg.getContent();
        logger.debug("用户发送到服务器的内容:{}", content);
        return new TextMsg(DateTime.now().toString() +" 服务器回复用户消息 by irving !");
    }
    /*1.1版本新增，重写父类方法，加入自定义微信消息处理器
     *不是必须的，上面的方法是统一处理所有的文本消息，如果业务觉复杂，上面的会显得比较乱
     *这个机制就是为了应对这种情况，每个MessageHandle就是一个业务，只处理指定的那部分消息
     */
    @Override
    protected List<MessageHandle> initMessageHandles() {
        List<MessageHandle> handles = new ArrayList<MessageHandle>();
        //handles.add(new MyMessageHandle());
        return handles;
    }

    @Override
    protected List<EventHandle> initEventHandles() {
        List<EventHandle> handles = new ArrayList<EventHandle>();
        //handles.add(new MyEventHandle());
        return handles;
    }
}