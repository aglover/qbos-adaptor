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
 * Date: 8/4/13
 * Time: 8:35 PM
 */
public class DeleteCommand extends AbstractCommand {
    @Override
    public AdapterResponse execute(AdapterRequest adapterRequest) {
        try {
            XML createXML = adapterRequest.getData();
            QTP instance = QTP.Create(createXML.getChild("qsi").getText(), createXML.getChild("ticket").getText());
            Applet applet = new Applet(Long.valueOf(createXML.getChild("classId").getText()),
                    Long.valueOf(createXML.getChild("recordId").getText()));
            long recordId = instance.deleteRecord(applet);
            return new AdapterResponse(300, "QTP Delete successful: " + recordId,
                    new XML("response").setText(Long.toString(recordId)), Status.SUCCESS);
        } catch (Exception e) {
            return exceptionResponse(e);

        }
    }
}
