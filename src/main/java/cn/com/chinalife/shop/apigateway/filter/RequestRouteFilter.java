package cn.com.chinalife.shop.apigateway.filter;

import cn.com.chinalife.shop.apigateway.service.RemoteService;
import cn.com.chinalife.shop.apigateway.vo.RequestVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RequestRouteFilter extends ZuulFilter {

    private final static Integer FILTER_ORDER = 10;

    private final ObjectMapper objectMapper;
    private final RemoteService remoteService;

    @Autowired
    public RequestRouteFilter(ObjectMapper objectMapper, RemoteService remoteService) {
        this.objectMapper = objectMapper;
        this.remoteService = remoteService;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletResponse servletResponse = ctx.getResponse();

        try {
            //获得请求参数并请求
            RequestVo requestVo = objectMapper.readValue(ctx.getResponseBody(), RequestVo.class);
            Object returnObj = remoteService.invoke(requestVo);

            //回写请求结果
            servletResponse.getOutputStream().write(returnObj.toString().getBytes());
            ctx.setRouteHost(null);
        } catch (IOException e) {
            throw new IllegalArgumentException("error parser json");
        }

        return null;
    }
}
