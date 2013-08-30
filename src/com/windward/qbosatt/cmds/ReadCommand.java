package com.windward.qbosatt.cmds;

import com.qbos.QTP.QTP;
import com.qbos.QTP.QuillDataTable;
import com.realops.common.xml.InvalidXMLFormatException;
import com.realops.common.xml.XML;

import java.io.StringReader;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chris
 * Date: 8/13/13
 * Time: 12:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReadCommand extends AbstractCommand {
    @Override
    public XML executeCommand(XML requestXML) throws Exception {
        QTP instance = QTP.Create(requestXML.getChild("qsi").getText(),
                requestXML.getChild("ticket").getText());
        long classId = Long.parseLong(requestXML.getChild("class").getText());
        long recordId = Long.parseLong(requestXML.getChild("record").getText());
        QuillDataTable dataTable = instance.readRecord(classId,recordId);

        XML response = new XML("data");
        response.addChild("count").setText(Long.toString(dataTable.getRecordCount()));

        // Only should be one of theses
        for (Map<String, String> row : dataTable) {
            response.addChild(this.createItemFromMap(row));
        }

        return response;
    }

}
