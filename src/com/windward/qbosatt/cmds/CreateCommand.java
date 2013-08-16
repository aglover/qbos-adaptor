package com.windward.qbosatt.cmds;

import com.qbos.QTP.Applet;
import com.qbos.QTP.QTP;
import com.realops.common.enumeration.Status;
import com.realops.common.xml.XML;
import com.realops.foundation.adapterframework.AdapterRequest;
import com.realops.foundation.adapterframework.AdapterResponse;

/**
 * Created with IntelliJ IDEA.
 * User: aglover
 * Date: 8/2/13
 * Time: 8:56 AM
 */
public class CreateCommand extends AbstractCommand {
    @Override
    public XML executeCommand(AdapterRequest adapterRequest) throws Exception {
        XML createXML = adapterRequest.getData();
        QTP instance = QTP.Create(createXML.getChild("qsi").getText(), createXML.getChild("ticket").getText());
        Applet applet = new Applet(Long.valueOf(createXML.getChild("class").getText()));

        for (XML field : createXML.getChild("fields").getChildren()) {
            applet.add(field.getName(), field.getText());
        }
        long recordId = instance.createRecord(applet);
        return new XML("data").setText(Long.toString(recordId));
    }

}
