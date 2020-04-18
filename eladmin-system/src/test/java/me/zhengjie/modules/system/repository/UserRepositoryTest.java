package me.zhengjie.modules.system.repository;

import me.zhengjie.AppRun;
import me.zhengjie.modules.system.domain.Task;
import me.zhengjie.utils.TimeStampUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.Date;
import java.sql.Timestamp;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserRepositoryTest {
    @Resource
    private UserRepository userRepository;
    @Test
    public void getMaxUserNumTest(){
        System.out.println(userRepository.getMaxUserNum());
    }
}
