package api;

import common.Constants;
import domain.GetChannelResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.ChannelInforService;

@RestController
public class GetChannelInfor {
    @Autowired
    private ChannelInforService channelInforService;
    private static final Logger log = LoggerFactory.getLogger(GetChannelInfor.class);

    @RequestMapping(value = "/CYVoteServer/getChannel", method = RequestMethod.GET)
    public GetChannelResponse getChannelInfor(){
        log.info("Come in getChannelInfor");
        GetChannelResponse infor = new GetChannelResponse();
        infor.setVoteChannelInformationList(channelInforService.getInforFromDB());
        infor.setReturnCode(Constants.RETURNCODE_SUCCESS);
        infor.setReturnDesc("Success");
        return infor;
    }
}
