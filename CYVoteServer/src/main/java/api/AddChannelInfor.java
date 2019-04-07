package api;

import common.CommonUtil;
import common.Constants;
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
    private static final Logger log = LoggerFactory.getLogger(AddChannelInfor.class);

    @RequestMapping(value = "/addChannel", method = RequestMethod.POST)
    public SimpleNormalResponse addInfo(@RequestBody AddChannelInforRequest request) {
        log.info("Coming addInfo");
        String userName = request.getUserName();
        String accessToken = request.getAccessToken();
        SimpleNormalResponse result = new SimpleNormalResponse();
        if (!CommonUtil.checkAT(userName, accessToken)) {
            result.setReturnCode(Constants.RETURNCODE_ILLEGAL_TOKEN);
            result.setReturnDesc("Illegal token!!!");
            return result;
        }
        String infor = CommonUtil.toJsonString(request.getVoteChannelInformation());
        String channel = request.getVoteChannelInformation().getChannel();
        int count = channelInforService.addInforToDB(channel, infor);
        if (0 == count) {
            result.setReturnCode(Constants.RETURNCODE_INNER_ERROR);
            result.setReturnDesc("Errro while add channel to DB");
        } else {
            result.setReturnCode(Constants.RETURNCODE_SUCCESS);
            result.setReturnDesc("Success");
        }
        log.info("end of addinfo");
        return result;
    }
}
