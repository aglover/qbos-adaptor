package com.windward.qbosatt;

import com.realops.common.enumeration.StateEnum;
import com.realops.common.enumeration.Status;
import com.realops.common.xml.InvalidXMLFormatException;
import com.realops.common.xml.XML;
import com.realops.foundation.adapterframework.AbstractActorAdapter;
import com.realops.foundation.adapterframework.AdapterException;
import com.realops.foundation.adapterframework.AdapterRequest;
import com.realops.foundation.adapterframework.AdapterResponse;

/**
 * Created with IntelliJ IDEA.
 * User: aglover
 * Date: 7/26/13
 * Time: 2:14 PM
 */
public class QbosAdaptor extends AbstractActorAdapter {

    @Override
    public AdapterResponse performAction(AdapterRequest adapterRequest) throws AdapterException, InterruptedException {
        XML stuff = adapterRequest.toXML();
//        XML stuff = adapterRequest.getData();
        String xmlraw = stuff.toCompactString();
        System.out.println("raw in adaptor is " + xmlraw);

        System.out.println("action is: " + adapterRequest.getAction());


        System.out.println("action is: " + adapterRequest.getData().toPrettyString());
        XML datastuff = adapterRequest.getData();

        System.out.println("param 1: " + datastuff.getChild("param1").getText());
//        <adapter-response>
//          <execution-duration>3000</execution-duration>
//          <status>success</status>
//          <message/>
//          <data>
//            <response>foo</response>
//          </data>
//        </adapter-response>

        XML parent = new XML("adapter-response");
        parent.addChild(new XML("execution-duration").setText("0"));
        parent.addChild(new XML("status").setText("success"));
        parent.addChild(new XML("message"));
        XML child = new XML("data");
        child.addChild(new XML("response").setText("FOO"));
        parent.addChild(child);

        try {
            return new AdapterResponse(300, "Foo", new XML("response").setText("FOO"), Status.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void shutdown() throws AdapterException {
        setState(StateEnum.STOPPED);
    }
}
