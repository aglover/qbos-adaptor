package com.windward.qbosatt.cmds;

import com.qbos.QTP.Applet;
import com.qbos.QTP.QTP;
import com.realops.common.xml.XML;

/**
 * Created with IntelliJ IDEA.
 * User: aglover
 * Date: 8/4/13
 * Time: 8:35 PM
 */
public class UndeleteCommand extends AbstractCommand {
    @Override
    public XML executeCommand(XML requestXML) throws Exception {
        QTP instance = QTP.Create(requestXML.getChild("qsi").getText(),
                requestXML.getChild("ticket").getText());
        Applet applet = new Applet(Long.valueOf(requestXML.getChild("class").getText()),
                Long.valueOf(requestXML.getChild("record").getText()));

        long recordId = instance.undeleteRecord(applet);
        return new XML("data").setText(Long.toString(recordId));
    }
}
