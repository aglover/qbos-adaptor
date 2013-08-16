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
public class PingCommand extends AbstractCommand {
    @Override
    public XML executeCommand(AdapterRequest adapterRequest) throws Exception {
        boolean status = this.getQtpInstance().ping();
        return new XML("data").setText(Boolean.toString(status));
    }
}
