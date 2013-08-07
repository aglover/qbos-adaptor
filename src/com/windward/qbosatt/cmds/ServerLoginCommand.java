package com.windward.qbosatt.cmds;

import com.qbos.QTP.QTP;
import com.realops.common.enumeration.Status;
import com.realops.common.xml.XML;
import com.realops.foundation.adapterframework.AdapterRequest;
import com.realops.foundation.adapterframework.AdapterResponse;

/**
 * Created with IntelliJ IDEA.
 * User: aglover
 * Date: 8/1/13
 * Time: 2:20 PM
 *
 *  Class executes a log in command to QTP and returns a token
 *
 */
public class ServerLoginCommand extends AbstractCommand {
    @Override
    public AdapterResponse execute(AdapterRequest adapterRequest) {
        try {
            XML loginXML = adapterRequest.getData();
            QTP qtp = this.getQtpInstance();
            qtp.logIn(loginXML.getChild("qsi").getText(),
                    loginXML.getChild("username").getText(),
                    loginXML.getChild("password").getText());
            String token = qtp.getTicket();
            return new AdapterResponse(300, "QTP ticket: " + token,
                    new XML("response").setText(token), Status.SUCCESS);
        } catch (Exception e) {
            return exceptionResponse(e);
        }
    }
}
