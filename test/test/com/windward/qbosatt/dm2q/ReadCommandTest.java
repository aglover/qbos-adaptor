package test.com.windward.qbosatt.dm2q;

import com.realops.common.xml.XML;
import com.realops.foundation.adapterframework.AdapterRequest;
import com.realops.foundation.adapterframework.AdapterResponse;
import com.windward.qbosatt.QbosActor;
import org.junit.Test;

import java.lang.System;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;

/**
 * Created with IntelliJ IDEA.
 * User: aglover
 * Date: 8/4/13
 * Time: 8:21 PM
 */
public class ReadCommandTest extends QbosAdapterTest{

    @Test
    public void testReadCommand() throws Exception {

        XML xml = this.loginAndLoadXML("etc/dm2q/test-read-req.xml");
        QbosActor adapter = new QbosActor();
        AdapterRequest request = new AdapterRequest(xml);
        AdapterResponse adapterResponse = adapter.performAction(request);
        assertNotNull("adapterResponse was not null?", adapterResponse);


//        System.out.println(adapterResponse.getData().toPrettyString());
        XML data = adapterResponse.getData().getChild("data");
        assertNotNull("data null?", data);

        String count = data.getChild("count").getText();
        assertTrue(Integer.parseInt(count) == 1);

        assertNotNull(data.getChild("item"));
        assertTrue(data.getChild("item").hasChildren());
    }

    @Test
    public void testReadCommandInvalidID() throws Exception {

        XML xml = this.loginAndLoadXML("etc/dm2q/test-read-invalid-id-req.xml");
        QbosActor adapter = new QbosActor();
        AdapterRequest request = new AdapterRequest(xml);
        AdapterResponse adapterResponse = adapter.performAction(request);
        assertNotNull("adapterResponse was not null?", adapterResponse);

//        System.out.println(adapterResponse.getData().toPrettyString());

        XML data = adapterResponse.getData().getChild("data");
        assertNotNull("data null?", data);

        String count = data.getChild("count").getText();
        assertTrue(Integer.parseInt(count) == 0);

        assertNull(data.getChild("item"));
    }

}
