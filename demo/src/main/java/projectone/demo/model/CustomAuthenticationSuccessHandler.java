package projectone.demo.model;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
/**
 * @author Koby Kruvulla
 */
/**
 * Handles redirection after successful authentication based on user roles.
 * Extends {@link SimpleUrlAuthenticationSuccessHandler} to implement custom
 * redirection logic.
 */
@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
     /**
     * Called when a user has been successfully authenticated.
     * Redirects users to different URLs based on their authority.
     *
     * @param request The request during which the authentication occurred.
     * @param response The response to manipulate after successful authentication.
     * @param authentication The {@link Authentication} object which was created during
     *                       the authentication process.
     * @throws IOException If an input or output exception occurs.
     * @throws ServletException If a servlet exception occurs.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String redirectUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + redirectUrl);

            return;
        }

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
    /**
     * Determines the URL to redirect a user to after login based on their roles.
     *
     * @param authentication The authentication object containing the user's granted authorities.
     * @return the URL to redirect the user to.
     */
    protected String determineTargetUrl(Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        boolean isManager = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_MANAGER"));

        boolean isCashier = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_CASHIER"));

        if (isAdmin) {
            return "/greet/admin";
        }
        else if(isManager){
            return "/greet/manager";
        }
        else if(isCashier){
            return "/greet/cashier";
        }
        else {
            return "/greet/customer";
        }
    }
}

