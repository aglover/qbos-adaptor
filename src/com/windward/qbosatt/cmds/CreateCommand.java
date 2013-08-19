package com.windward.qbosatt.cmds;

import com.qbos.QTP.Applet;
import com.qbos.QTP.QTP;
import com.realops.common.xml.XML;

/**
 * Created with IntelliJ IDEA.
 * User: aglover
 * Date: 8/2/13
 * Time: 8:56 AM
 */
public class CreateCommand extends AbstractCommand {
    @Override
    public XML executeCommand(XML requestXML) throws Exception {
        QTP instance = QTP.Create(requestXML.getChild("qsi").getText(),
                requestXML.getChild("ticket").getText());
        Applet applet = new Applet(Long.valueOf(requestXML.getChild("class").getText()));
        this.setFieldsInApplet(requestXML.getChild("fields"), applet);
        long recordId = instance.createRecord(applet);
        return new XML("data").setText(Long.toString(recordId));
    }

}
