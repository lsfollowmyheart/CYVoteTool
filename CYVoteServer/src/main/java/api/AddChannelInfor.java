package api;

import common.CommonUtil;
import domain.AddChannelInforRequest;
import domain.SimpleNormalResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.ChannelInforService;


@RestController
public class AddChannelInfor {
    @Autowired
    private ChannelInforService channelInforService;
    private static final Logger LOG = LoggerFactory.getLogger(AddChannelInfor.class);
    @RequestMapping(value = "/CYVoteServer/addChannel", method = RequestMethod.POST)
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
        String channel = request.getVoteChannelInformation().getChannel();
        channelInforService.addInforToDB(channel, infor);
        return result;
    }
}
