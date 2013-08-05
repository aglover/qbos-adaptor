package com.windward.qbosatt.cmds;

import com.qbos.QTP.InvalidCredentialsException;
import com.qbos.QTP.QTP;
import com.realops.common.enumeration.Status;
import com.realops.common.xml.XML;
import com.realops.foundation.adapterframework.AdapterRequest;
import com.realops.foundation.adapterframework.AdapterResponse;

/**
 * Created with IntelliJ IDEA.
 * User: aglover
 * Date: 8/1/13
 * Time: 5:01 PM
 */
public class LogoutCommand extends AbstractCommand {
    @Override
    public AdapterResponse execute(AdapterRequest adapterRequest) {
        try {
            XML logoutXML = adapterRequest.getData();
            QTP instance = QTP.Create(logoutXML.getChild("qsi").getText(), logoutXML.getChild("ticket").getText());
            instance.logOut();
            return getSuccessResponse();
        } catch (Exception e) {
            if (e instanceof InvalidCredentialsException) {
                return getSuccessResponse();
            } else {
                return exceptionResponse(e);
            }
        }
    }

    private AdapterResponse getSuccessResponse() {
        return new AdapterResponse(300, "QTP Logout successful",
                new XML("response").setText("successful logout"), Status.SUCCESS);
    }
}
