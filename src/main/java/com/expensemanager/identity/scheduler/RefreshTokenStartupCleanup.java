package com.expensemanager.identity.scheduler;

import com.expensemanager.identity.auth.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenStartupCleanup {

    private final RefreshTokenService refreshTokenService;

    @EventListener(ApplicationReadyEvent.class)
    public void cleanupExpiredTokens() {

        int deletedTokens = refreshTokenService.deleteExpiredTokens();

        if (deletedTokens > 0) {
            log.info("Startup cleanup deleted {} expired refresh token(s).", deletedTokens);
        } else {
            log.debug("Startup cleanup found no expired refresh tokens.");
        }
    }
}