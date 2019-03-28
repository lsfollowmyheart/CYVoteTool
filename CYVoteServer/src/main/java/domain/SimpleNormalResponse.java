package domain;

import common.CommonUtil;

public class SimpleNormalResponse {
    String returnCode;
    String returnDesc;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnDesc() {
        return returnDesc;
    }

    public void setReturnDesc(String returnDesc) {
        this.returnDesc = returnDesc;
    }

    @Override
    public String toString(){
        SimpleNormalResponse response = new SimpleNormalResponse();
        response.setReturnDesc(returnDesc);
        response.setReturnCode(returnCode);
        return CommonUtil.toJsonString(response);
    }
}
