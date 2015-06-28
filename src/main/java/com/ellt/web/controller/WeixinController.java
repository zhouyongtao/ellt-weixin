package com.ellt.web.controller;
import com.alibaba.fastjson.JSON;
import com.github.sd4324530.fastweixin.api.OauthAPI;
import com.github.sd4324530.fastweixin.api.UserAPI;
import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import com.github.sd4324530.fastweixin.api.enums.OauthScope;
import com.github.sd4324530.fastweixin.api.response.GetUserInfoResponse;
import com.github.sd4324530.fastweixin.api.response.OauthGetTokenResponse;
import com.github.sd4324530.fastweixin.handle.EventHandle;
import com.github.sd4324530.fastweixin.handle.MessageHandle;
import com.github.sd4324530.fastweixin.message.BaseMsg;
import com.github.sd4324530.fastweixin.message.TextMsg;
import com.github.sd4324530.fastweixin.message.req.BaseEvent;
import com.github.sd4324530.fastweixin.message.req.TextReqMsg;
import com.github.sd4324530.fastweixin.servlet.WeixinControllerSupport;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import static com.alibaba.fastjson.JSON.toJSON;
import static com.alibaba.fastjson.JSON.toJSONString;

/**
 * 微信公众号控制器
 * Created by Irving on 2014/7/6.
 */
@Controller
@RequestMapping("/weixin")
public class WeixinController extends WeixinControllerSupport {
    private static final Logger logger = LoggerFactory.getLogger(WeixinController.class);

    private static final String AppId = "wxb762296355110c77";
    private static final String AppSecret = "5942b6bad3f2267e6750edd26b8136a6";
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
        return new TextMsg("hava fun,by irving !");
    }

    @Override
    protected BaseMsg handleSubscribe(BaseEvent event) {
        logger.info("handleSubscribe json: "+ toJSON(event));
        UserAPI userAPI =new UserAPI(new ApiConfig(AppId,AppSecret));
        GetUserInfoResponse user= userAPI.getUserInfo(event.getFromUserName());
        String msg ="Hi "+user.getNickname()+" 欢迎关注流量通!";
        return new TextMsg(msg);
    }

    @Override
    protected BaseMsg handleUnsubscribe(BaseEvent event) {
        logger.info("handleUnsubscribe json: "+ toJSON(event));
        return super.handleUnsubscribe(event);
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

    /**
     * oauth2 授权
     * @return
     */
    @RequestMapping("/authorize")
    public String authorize() {
        OauthAPI oauthAPI = new OauthAPI(new ApiConfig(AppId,AppSecret));
        String url=oauthAPI.getOauthPageUrl("http://liuliangtong.cn/wx/weixin/oauth2", OauthScope.SNSAPI_USERINFO, "ellt");
        return "redirect:"+url;
    }

    /**
     * 授权成功页
     * @param code
     * @param state
     * @return
     */
    @RequestMapping("/oauth2")
    public String oauth2(HttpServletRequest request,String code,String state) {
        logger.info("weixin oauth2 code :"+code +" state : "+state);
        OauthAPI oauthAPI = new OauthAPI(new ApiConfig(AppId,AppSecret));
        OauthGetTokenResponse oauthGetToken =oauthAPI.getToken(code);
        GetUserInfoResponse oauthUserInfo= oauthAPI.getUserInfo(oauthGetToken.getAccessToken(),oauthGetToken.getOpenid());
        request.setAttribute("userInfo", toJSONString(oauthUserInfo));
        request.setAttribute("user",oauthUserInfo);
        return "weixin/oauth2";
    }
}