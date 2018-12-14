package com.bester.platform.platformchain.controller;

import com.bester.platform.platformchain.common.CommonResult;
import com.bester.platform.platformchain.constant.PowerSource;
import com.bester.platform.platformchain.constant.PowerStatus;
import com.bester.platform.platformchain.constant.RedisKeys;
import com.bester.platform.platformchain.dto.UserInfoDTO;
import com.bester.platform.platformchain.service.PowerRecordService;
import com.bester.platform.platformchain.service.UserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bester.platform.platformchain.service.RedisClientService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author lizhi
 */
@RestController
public class BindpublicnumController {
    @Resource
    private RedisClientService redisClientService;

    @Resource
    private PowerRecordService powerRecordService;

    @Resource
    private UserInfoService userInfoService;

    List<String> randomnumbers= new ArrayList<>(1000);

    List verificodes;

    /**
     * 公众号获取验证码
     * @return
     */
    @GetMapping("/user/getVerificode")
    public  CommonResult getVerificode(){
        String str="abcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789";
        Random r= new Random();
        String[] rnums= new String [4];
        String b="";
        for(int i=0;i<4;i++){
            int n=r.nextInt(62);
            rnums[i]=str.substring(n,n+1);
            b+=rnums[i];
        }
        randomnumbers.add(b);
        redisClientService.set(RedisKeys.BINDPUBLNUM_VERIFY_CODE,randomnumbers,180000L);
        return CommonResult.success(b);
    }

    /**
     * APP验证公众号绑定
     * @param verifiCode
     * @return
     */
    @PostMapping("/user/checkVerificode")
    public synchronized CommonResult checkVerificode(HttpServletRequest request, String verifiCode){
        if (StringUtils.isBlank(verifiCode) || (verifiCode.replace(" ", "").length()!=4)){
            return  CommonResult.fail("请正确输入验证码");
        }
        if ((verificodes=(List)redisClientService.get(RedisKeys.BINDPUBLNUM_VERIFY_CODE))==null){
            return CommonResult.fail("验证码过期，请重新获取");
        }
        for (Object i:verificodes){
            if(verifiCode.equals(i)){
                int id = Integer.parseInt(request.getParameter("userId"));
                UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserId(id);
                if(userInfoDTO!=null){
                    powerRecordService.addUserPower(id, PowerSource.BINDPUBLIC,PowerSource.BINDPUBLIC_POWER, PowerStatus.NO_TEMPORARY);
                    userInfoDTO.setBindPublicNum(1);
                    userInfoService.updateUserInfo(userInfoDTO);
                    return CommonResult.success();
                }
                return CommonResult.fail("用户无效，请重新获取");
            }
        }
        return CommonResult.fail("验证码错误或过期，请重新获取");
    }

}
