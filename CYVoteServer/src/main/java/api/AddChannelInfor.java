package api;

import common.CommonUtil;
import domain.AddChannelInforRequest;
import domain.SimpleNormalResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import util.RSAUtil;

public class AddChannelInfor {
    @RequestMapping
    public SimpleNormalResponse addInfor(@RequestBody AddChannelInforRequest request){
        String userName = request.getUserName();
        String accessToken = request.getAccessToken();
        SimpleNormalResponse result = new SimpleNormalResponse();
        if(!CommonUtil.checkAT(userName, accessToken))
        {
            result.setReturnCode("4004");
            result.setReturnDesc("Illegal token!!!");
            return result;
        }
        String infor = CommonUtil.toJsonString(request.getVoteChannelInformation());
        addInforToDB(infor);
        return result;
    }

    /**
     * 此方法用于添加渠道信息到数据库中
     * @param infor
     * @return
     */
    private int addInforToDB(String infor){
        int count = 0;
        String pubKey = "key";
        infor = RSAUtil.encryptByPublicKey(infor, pubKey);
        return count;
    }


}
