package com.windward.qbosatt.cmds;

import com.qbos.QTP.QTP;
import com.qbos.QTP.QuillDataRow;
import com.qbos.QTP.QuillDataTable;
import com.realops.common.xml.XML;
import java.util.Map;

import java.io.StringReader;

/**
 * Created with IntelliJ IDEA.
 * User: chris
 * Date: 8/13/13
 * Time: 12:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class FindCommand extends AbstractCommand {
    @Override
    public XML executeCommand(XML requestXML) throws Exception {
        QTP instance = QTP.Create(requestXML.getChild("qsi").getText(),
                requestXML.getChild("ticket").getText());
        QuillDataTable dataTable = instance.query(requestXML.getChild("query").getText());

        XML response = new XML("data");
        response.addChild("count").setText(Long.toString(dataTable.getRecordCount()));

        XML items = new XML("items");
        for (QuillDataRow row : dataTable) {
            items.addChild(this.createItemFromMap(row));
        }
        response.addChild(items);

        return response;
    }

}
