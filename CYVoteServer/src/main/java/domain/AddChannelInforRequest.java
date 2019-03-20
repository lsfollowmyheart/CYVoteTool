package domain;

public class AddChannelInforRequest {
    String userName;
    String accessToken;
    VoteChannelInformation voteChannelInformation;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public VoteChannelInformation getVoteChannelInformation() {
        return voteChannelInformation;
    }

    public void setVoteChannelInformation(VoteChannelInformation voteChannelInformation) {
        this.voteChannelInformation = voteChannelInformation;
    }
}
