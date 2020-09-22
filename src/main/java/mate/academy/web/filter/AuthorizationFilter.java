package mate.academy.web.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.lb.Injector;
import mate.academy.model.Role;
import mate.academy.model.User;
import mate.academy.service.UserService;

public class AuthorizationFilter implements Filter {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final String USER_ID = "user_id";
    private final UserService userService =
            (UserService) injector.getInstance(UserService.class);
    private Map<String, List<Role.RoleName>> protectedUrls = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        protectedUrls.put("/users/all", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/order/all/admin", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/products/all/admin", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/products/add", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/order/remove", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/products/delete", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/users/delete", List.of(Role.RoleName.ADMIN));

        protectedUrls.put("/order/all", List.of(Role.RoleName.USER));
        protectedUrls.put("/shopping-carts/products/add", List.of(Role.RoleName.USER));
        protectedUrls.put("/shopping-cart/products/get", List.of(Role.RoleName.USER));
        protectedUrls.put("/shopping-cart/products/remove", List.of(Role.RoleName.USER));
        protectedUrls.put("/order/complete", List.of(Role.RoleName.USER));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String requestedUrl = req.getServletPath();

        if (!protectedUrls.containsKey(requestedUrl)) {
            filterChain.doFilter(req, resp);
            return;
        }

        Long userId = (Long) req.getSession().getAttribute(USER_ID);
        User user = userService.get(userId);

        if (isAuthorized(user, protectedUrls.get(requestedUrl))) {
            filterChain.doFilter(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp").forward(req, resp);
        }
    }

    @Override
    public void destroy() {
    }

    private boolean isAuthorized(User user, List<Role.RoleName> authorizedRoles) {
        for (Role.RoleName authorizedRole : authorizedRoles) {
            for (Role userRole : user.getRoles()) {
                if (authorizedRole.equals(userRole.getRoleName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
