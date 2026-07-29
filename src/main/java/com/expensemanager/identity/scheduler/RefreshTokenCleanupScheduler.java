package com.expensemanager.identity.scheduler;

import com.expensemanager.identity.auth.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenCleanupScheduler {

    private final RefreshTokenService refreshTokenService;

    @Scheduled(cron = "${expense-manager.scheduler.refresh-token-cleanup-cron}")
    public void cleanupExpiredRefreshTokens() {

        int deletedTokens = refreshTokenService.deleteExpiredTokens();

        if (deletedTokens > 0) {
            log.info("Deleted {} expired refresh token(s).", deletedTokens);
        } else {
            log.debug("No expired refresh tokens found.");
        }
    }
}