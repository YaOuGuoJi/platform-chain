package com.bester.platform.platformchain.service;

import com.bester.platform.platformchain.dto.UserAccountDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author zhangqiang
 * @date 2018-12-05
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserAccountTest {

    @Resource
    private UserAccountService userAccountService;

    @Test
    public void testInsert() {
        UserAccountDTO dto = new UserAccountDTO();
        dto.setPhoneNum("18691021277");
        dto.setInviteCode("ZXCVBB");
        int userId = userAccountService.addUserAccountInfo(dto);
        System.out.println(userId);
    }

}
