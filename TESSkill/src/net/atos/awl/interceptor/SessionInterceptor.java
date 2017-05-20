package net.atos.awl.interceptor;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class SessionInterceptor implements Interceptor {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        System.out.println("Inside intercepter:::::::::");
        ActionContext context = invocation.getInvocationContext();
        Map<String, Object> sessionMap = new HashMap(context.getSession());
        if (null == sessionMap || sessionMap.isEmpty() || null == sessionMap.get("DASID")) {
            System.out.println("Inside session timeout:::::::::");
            return "sessionexpired"; // session is empty/expired
        }
        return invocation.invoke();
    }

    public void destroy() {
        System.out.println(" SessionCheckInterceptor destroy() is called...");
    }

    public void init() {
        System.out.println("SessionCheckInterceptor init() is called...");
    }

}
