package com.windward.qbosatt.cmds;

import com.qbos.QTP.Applet;
import com.qbos.QTP.QTP;
import com.realops.common.xml.XML;

/**
 * Created with IntelliJ IDEA.
 * User: aglover
 * Date: 8/4/13
 * Time: 8:18 PM
 */
public class UpdateCommand extends AbstractCommand {
    @Override
    public XML executeCommand(XML requestXML) throws Exception {

        XML item = requestXML.getChild("item");
        long recordId = Long.valueOf(item.getChild("record").getText());
        QTP instance = QTP.Create(requestXML.getChild("qsi").getText(), requestXML.getChild("ticket").getText());
        Applet applet = new Applet(Long.valueOf(requestXML.getChild("class").getText()), recordId);

        if (item.getChild("status") != null) {
            instance.updateStatus(applet, Long.valueOf(item.getChild("status").getText()), true);
        }
        XML notes = item.getChild("notes");
        if (notes != null) {
            for (XML note : notes.getChildren()) {
                instance.addNote(applet, note.getText());
            }
        }
        XML fields = item.getChild("fields");
        if (fields != null && fields.hasChildren()) {
            this.setFieldsInApplet(fields, applet);
            instance.updateRecord(applet);
        }
        return new XML("data").setText(Long.toString(recordId));
    }
}
