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
 * <p/>
 * Class executes a log in command to QTP and returns a token
 */
public class LoginCommand extends AbstractCommand {
    @Override
    public XML executeCommand(AdapterRequest adapterRequest) throws Exception {
        XML loginXML = adapterRequest.getData();
        QTP qtp = this.getQtpInstance();
        qtp.logIn(loginXML.getChild("qsi").getText(),
                loginXML.getChild("username").getText(),
                loginXML.getChild("password").getText());

        return new XML("data").setText(qtp.getTicket());
    }
}
