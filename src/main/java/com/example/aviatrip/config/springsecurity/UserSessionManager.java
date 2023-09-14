package com.example.aviatrip.config.springsecurity;

import com.example.aviatrip.config.exception.UserAlreadyAuthenticatedException;
import com.example.aviatrip.model.User;
import com.example.aviatrip.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.context.SecurityContextRepository;

import java.util.Collection;
import java.util.List;

public class UserSessionManager {

    private final SessionRegistry sessionRegistry;
    private final UserService userService;
    private final SecurityContextRepository contextRepository;

    public UserSessionManager(SessionRegistry sessionRegistry, UserService userService, SecurityContextRepository contextRepository) {
        this.sessionRegistry = sessionRegistry;
        this.userService = userService;
        this.contextRepository = contextRepository;
    }

    public boolean isAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && !(auth instanceof AnonymousAuthenticationToken);
    }

    public void assertNotAuthenticated() {
        if(isAuthenticated())
            throw new UserAlreadyAuthenticatedException("already authenticated");
    }

    public void invalidateAllSessionsByUserId(Long userId) {
        List<SessionInformation> sessions = sessionRegistry.getAllSessions(userId, false);

        for (SessionInformation session : sessions) {
            session.expireNow();
        }
    }

    public void updateAuthoritiesOfCurrentSession(Collection<? extends GrantedAuthority> authorities) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), authorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    public void rememberUser(User user, HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getId(), null,
                userService.retrieveAuthorities(user));
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
        contextRepository.saveContext(SecurityContextHolder.getContext(), request, response);
    }

/**
 * I will fix it sometime
*/

//    public void updateAuthoritiesOfAllSessionsByUserId(Long userId, Collection<? extends GrantedAuthority> authorities) {

//        List<SessionInformation> sessions = sessionRegistry.getAllSessions(userId, false);
//
//        for(SessionInformation sessionInformation : sessions) {
//            sessionInformation.expireNow();
//
//            Authentication auth = (Authentication) sessionInformation.getPrincipal();
//            Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), authorities);
//            String sessionId = sessionInformation.getSessionId();
//
//            sessionRegistry.removeSessionInformation(sessionId);
//            sessionRegistry.registerNewSession(sessionId, newAuth);
//            sessionRegistry.getSessionInformation(sessionId).refreshLastRequest();
//        }
//    }


}
