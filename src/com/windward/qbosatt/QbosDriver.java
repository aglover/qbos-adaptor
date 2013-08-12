package com.windward.qbosatt;

import com.qbos.QTP.QTP;
import com.qbos.QTP.QuillDataTable;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: aglover
 * Date: 8/12/13
 * Time: 3:01 PM
 */
public class QbosDriver {

    public static void main(String[] args) throws Exception {
        QTP qtp = new QTP();
        System.out.println("about to login");
        qtp.logIn("dm2q", "cdale@windwardits.com", "Rilda411");
        System.out.println("done w/login");
        String token = qtp.getTicket();
        System.out.println("ticket is: " + token);
        String ql = "Select  RecordId,  Role, Install_Order_No, Type, Vendor, Name, Architecture, Version, Build, Release from <<610858.APPCAT>>;";
        System.out.println("about to query" );
        QuillDataTable dt = qtp.query(ql);
        System.out.println(dt.getRecordCount());
        for(Map<String,String> row : dt){
            for(String s : row.keySet()){
                System.out.println(s + "=" + row.get(s));
            }
            System.out.println("-----------------------------------");
        }
    }
}
