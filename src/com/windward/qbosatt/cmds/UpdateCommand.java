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
 * Time: 8:18 PM
 */
public class UpdateCommand extends AbstractCommand{
    @Override
    public AdapterResponse execute(AdapterRequest adapterRequest) {
        try {
            XML updateXML = adapterRequest.getData();
            long recordId = Long.valueOf(updateXML.getChild("record-id").getText());
            QTP instance = QTP.Create(updateXML.getChild("qsi").getText(), updateXML.getChild("ticket").getText());
            Applet applet = new Applet(Long.valueOf(updateXML.getChild("class-id").getText()),recordId);

            if(updateXML.getChild("status") != null){
                instance.updateStatus(applet, Long.valueOf(updateXML.getChild("status").getText()), true);
            }
            XML notes = updateXML.getChild("notes");
            if (notes !=null ){
                for (XML note: notes.getChildren()){
                    instance.addNote(applet, note.getText());
                }
            }
            XML item = updateXML.getChild("item");
            if (item!=null && item.hasChildren()){
                for(XML element: item.getChildren()){
                    applet.add(element.getElement().getName(), element.getText());
                }
                instance.updateRecord(applet);
            }
            return new AdapterResponse(300, "QTP Update successful: " + recordId,
                    new XML("response").setText(Long.toString(recordId)), Status.SUCCESS);
        } catch (Exception e) {
            return exceptionResponse(e);
        }
    }
}
