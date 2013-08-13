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
    public AdapterResponse execute(AdapterRequest adapterRequest) {
        try {
            XML queryXML = adapterRequest.getData();
            QTP instance = QTP.Create(queryXML.getChild("qsi").getText(), queryXML.getChild("ticket").getText());
            QuillDataTable dataTable = instance.query(queryXML.getChild("query").getText());
            XML rows = new XML("rows");

            for(Map<String,String> row : dataTable){
                XML xmlRow = new XML("row");
                for(String key : row.keySet()){
                    xmlRow.addChild(key).setText(row.get(key));
                }
                rows.addChild(xmlRow);
            }
            XML response = new XML("response");
            response.addChild("count").setText(Long.toString(dataTable.getRecordCount()));
            response.addChild(rows);
            return new AdapterResponse(300, "QTP Create successful: " ,
                    response, Status.SUCCESS);
        } catch (Exception e) {
            System.out.println("Exception: " +e.getLocalizedMessage());
            return exceptionResponse(e);
        }
    }

}
