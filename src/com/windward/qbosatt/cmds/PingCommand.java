package com.windward.qbosatt.cmds;

import com.realops.common.xml.XML;

/**
 * Created with IntelliJ IDEA.
 * User: aglover
 * Date: 8/4/13
 * Time: 8:28 PM
 */
public class PingCommand extends AbstractCommand {
    @Override
    public XML executeCommand(XML requestXML) throws Exception {
        boolean status = this.getQtpInstance().ping();
        return new XML("data").setText(Boolean.toString(status));
    }
}
