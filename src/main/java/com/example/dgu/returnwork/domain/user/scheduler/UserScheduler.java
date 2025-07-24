package com.example.dgu.returnwork.domain.user.scheduler;

import com.example.dgu.returnwork.domain.user.User;
import com.example.dgu.returnwork.domain.user.enums.Status;
import com.example.dgu.returnwork.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserScheduler {

    private final UserRepository userRepository;

    @Transactional
    @Scheduled(cron = "0 0 2 * * *")
    public void cleanupDeletedUser(){
        LocalDateTime threeMonthsAgo = LocalDateTime.now().minusMonths(3);

        List<User> userToDelete = userRepository.findByStatusAndDeletedAtBefore(Status.DELETED, threeMonthsAgo);

        if(userToDelete.isEmpty()){
            log.info("삭제할 사용자가 없습니다.");
            return;
        }

        log.info("삭제 대상 사용자 수: {}", userToDelete.size());
        userRepository.deleteAll(userToDelete);
        log.info("사용자 삭제 완료: {} 명", userToDelete.size());
    }
}
