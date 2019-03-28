package service;

import common.CommonUtil;
import dao.SQLImpl;
import dao.domain.ChannelInfor;
import domain.VoteChannelInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.RSAUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChannelInforService {
    private static final Logger log = LoggerFactory.getLogger(ChannelInforService.class);
    @Autowired
    private SQLImpl sqlImpl;

    private List<ChannelInfor> getChannelInfor() {
        return sqlImpl.getChannelInfor();
    }

    private int setChannelInfor(String channel, String infor) {
        return sqlImpl.setChannelInfor(channel, infor);
    }

    /**
     * 此方法用于添加渠道信息到数据库中
     *
     * @param infor
     * @return
     */
    public int addInforToDB(String channel, String infor) {
        int count = 0;
        try {
            //String pubKey = "key";
            //infor = RSAUtil.encryptByPublicKey(infor, pubKey);
            count = setChannelInfor(channel, infor);
        } catch (Exception e) {
            log.info("There is an error!!! When add channelInfor");
            e.printStackTrace();
        }
        return count;
    }

    public List<VoteChannelInformation> getInforFromDB() {
        List<VoteChannelInformation> listVoteChannel = new ArrayList<VoteChannelInformation>();
        try {
            List<ChannelInfor> listInfor = getChannelInfor();
            for (ChannelInfor infor : listInfor) {
                String inforStr = infor.getInfor();
                VoteChannelInformation information = (VoteChannelInformation) CommonUtil.fromJsonString(inforStr, VoteChannelInformation.class);
                listVoteChannel.add(information);
            }
        }
        catch (Exception e)
        {
            log.info("Get information Error!Last infor length is" + listVoteChannel.size());
            e.printStackTrace();
        }
        return listVoteChannel;
    }
}
