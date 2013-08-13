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

import java.lang.Exception;
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
public class LiveQbosAdapterTest {

    public String getTicket() throws Exception {
        XML xml = XML.read("etc/test-login-req.xml");
        AdapterRequest request = new AdapterRequest(xml);
        QbosAdapter adaptor = new QbosAdapter();
        AdapterResponse adapterResponse = adaptor.performAction(request);
        return adapterResponse.getData().getText();
    }

    public XML loginAndLoadXML(String filePath) throws Exception {
        String ticket = this.getTicket();
        XML xml = XML.read(filePath);
        xml.getChild("request-data").getChild("create").getChild("ticket").setText(ticket);
        return xml;
    }

    @Test
    public void testLogin() throws Exception {
        String ticket = this.getTicket();
        assertNotNull("token was not null?", ticket);
    }
}
