package test.com.windward.qbosatt;

import com.qbos.QTP.Applet;
import com.qbos.QTP.QTP;
import com.realops.common.xml.XML;
import com.realops.foundation.adapterframework.AdapterRequest;
import com.realops.foundation.adapterframework.AdapterResponse;
import com.windward.qbosatt.QbosAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.System;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: aglover
 * Date: 8/4/13
 * Time: 8:21 PM
 */
public class FindCommandTest {

    @Test
    public void testFindCommand() throws Exception {
        XML xml = XML.read("etc/test-login-req.xml");
        AdapterRequest request = new AdapterRequest(xml);
        QbosAdapter adaptor = new QbosAdapter();
        AdapterResponse adapterResponse = adaptor.performAction(request);
        String ticket = adapterResponse.getData().getText();
//        System.out.println("Got ticket: "+ticket);

        xml = XML.read("etc/test-find-req.xml");
        xml.getChild("request-data").getChild("create").getChild("ticket").setText(ticket);
        request = new AdapterRequest(xml);
        adapterResponse = adaptor.performAction(request);
//        System.out.println(adapterResponse.getData().toPrettyString());
        assertNotNull("adapterResponse was not null?", adapterResponse);
        String count = adapterResponse.getData().getChild("count").getText();
        assertTrue(Integer.parseInt(count) > 2);
    }
}
