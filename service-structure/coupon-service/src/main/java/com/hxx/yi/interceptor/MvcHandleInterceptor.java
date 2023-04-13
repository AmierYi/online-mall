package com.hxx.yi.interceptor;


import com.yi.common.entity.Storekeeper;
import com.yi.common.utils.AppJwtUtil;
import com.yi.common.utils.StorekeeperThreadLocal;
import io.jsonwebtoken.Claims;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * 自定义拦截器
 */
public class MvcHandleInterceptor implements HandlerInterceptor {
    //方法之前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //TODO 未知 BUG，在登录的时候就已经携带了token来到这拦截器中（与案例中不符）。
        /**
         * 解决除登录外的请求跳过了设置 ThreadLocal 的愚笨方法：
         * 在request对象中获取到请求的信息。如果是登录请求直接放行；若不是登录，则需要设置 ThreadLocal
         */
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/login")) {
            return true;
        }

        //1.从请求头中获取到token
        String token = request.getHeader("token");
        Optional<Object> optional = Optional.ofNullable(token);
        if (optional.isPresent()) {
            //2.从token中解析出user_id
            Claims claimsBody = AppJwtUtil.getClaimsBody(token);
            String storekeeperId = (String) claimsBody.get("id");
            Storekeeper storekeeper = new Storekeeper();
            storekeeper.setStorekeeperId(storekeeperId);

            //3.存入到threadLocal中
            StorekeeperThreadLocal.setStorekeeperThreadLocal(storekeeper);
        }

        return true;
    }

    //方法之后执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //从threadLocal中清除用户信息
        StorekeeperThreadLocal.remove();
    }
}
