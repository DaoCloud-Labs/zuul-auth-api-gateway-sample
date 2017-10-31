package cn.com.chinalife.shop.apigateway.filter;

import cn.com.chinalife.shop.apigateway.service.AuthorizeService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationFilter extends ZuulFilter {

    private final AuthorizeService authorizeService;
    private final static Integer FILTER_ORDER = 0;

    @Autowired
    public AuthorizationFilter(AuthorizeService authorizeService) {
        this.authorizeService = authorizeService;
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
        RequestContext ctx = RequestContext.getCurrentContext();

        return isShouldFilterPath(ctx.getRequest().getRequestURI());
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();

        //拿到身份标志
        String identification = ctx.getRequest().getHeader("X-TOKEN");

        //判断是否有权限
        if (!authorizeService.hasAuth(identification)) {
            throw new IllegalArgumentException("Token is illegal");
        }

        return null;
    }

    /**
     * 判断URL是否需要权限限制
     *
     * @param url URL
     * @return Y/N
     */
    private boolean isShouldFilterPath(String url) {
        //TODO
        return false;
    }

}
