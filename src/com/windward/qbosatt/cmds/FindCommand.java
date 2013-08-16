package com.windward.qbosatt.cmds;

import com.qbos.QTP.QTP;
import com.qbos.QTP.QuillDataTable;
import com.realops.common.enumeration.Status;
import com.realops.common.xml.XML;
import com.realops.foundation.adapterframework.AdapterRequest;
import com.realops.foundation.adapterframework.AdapterResponse;

import java.util.Map;

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
        for (Map<String, String> row : dataTable) {
            XML item = new XML("item");
            for (String key : row.keySet()) {
                item.addChild(key).setText(row.get(key));
            }
            items.addChild(item);
        }
        response.addChild(items);

        return response;
    }

}
