package domain;

import java.util.List;

public class GetChannelResponse extends SimpleNormalResponse {
    List<VoteChannelInformation> voteChannelInformationList;

    public List<VoteChannelInformation> getVoteChannelInformationList() {
        return voteChannelInformationList;
    }

    public void setVoteChannelInformationList(List<VoteChannelInformation> voteChannelInformationList) {
        this.voteChannelInformationList = voteChannelInformationList;
    }
}
