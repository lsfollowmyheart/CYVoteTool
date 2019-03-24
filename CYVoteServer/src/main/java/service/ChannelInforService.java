package service;

import dao.SQLImpl;
import dao.domain.ChannelInfor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.RSAUtil;

@Service
public class ChannelInforService {
    private static final Logger log = LoggerFactory.getLogger(ChannelInforService.class);
    @Autowired
    private SQLImpl sqlImpl;

    public ChannelInfor getChannelInfor() {
        return sqlImpl.getChannelInfor();
    }

    private int setChannelInfor(String channel, String infor) {
        return sqlImpl.setChannelInfor(channel, infor);
    }

    /**
     * 此方法用于添加渠道信息到数据库中
     * @param infor
     * @return
     */
    public int addInforToDB(String channel, String infor){
        int count = 0;
        try
        {
            String pubKey = "key";
            infor = RSAUtil.encryptByPublicKey(infor, pubKey);
            count = setChannelInfor(channel, infor);
        }
        catch (Exception e)
        {
            log.info("There is an error!!! When add channelInfor");
            e.printStackTrace();
        }
        return count;
    }
}
