package com.windward.qbosatt.cmds;

import com.qbos.QTP.QTP;
import com.realops.common.enumeration.Status;
import com.realops.common.xml.XML;
import com.realops.foundation.adapterframework.AdapterRequest;
import com.realops.foundation.adapterframework.AdapterResponse;

/**
 * Created with IntelliJ IDEA.
 * User: aglover
 * Date: 8/4/13
 * Time: 8:28 PM
 */
public class PingCommand extends AbstractCommand{
    @Override
    public AdapterResponse execute(AdapterRequest adapterRequest) {
        try {
            QTP qtp = this.getQtpInstance();
            boolean status = qtp.ping();
            return new AdapterResponse(300, "QTP status: " + status,
                    new XML("response").setText(Boolean.toString(status)), Status.SUCCESS);
        } catch (Exception e) {
            return new AdapterResponse(300, "FAILURE: " + e.getLocalizedMessage(),
                    new XML("response").setText("FAILURE"), Status.ERROR);
        }
    }
}
