package test.com.windward.qbosatt.dm2q;

import com.realops.common.xml.XML;
import com.realops.foundation.adapterframework.AdapterRequest;
import com.realops.foundation.adapterframework.AdapterResponse;
import com.windward.qbosatt.QbosActor;
import org.junit.Test;

import java.lang.Exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;

/**
 * Created with IntelliJ IDEA.
 * User: aglover
 * Date: 8/4/13
 * Time: 8:21 PM
 */
public class QbosAdapterTest {

    public String getTicket() throws Exception {
        XML xml = XML.read("etc/dm2q/test-login-req.xml");
        AdapterRequest request = new AdapterRequest(xml);
        QbosActor adaptor = new QbosActor();
        AdapterResponse adapterResponse = adaptor.performAction(request);
        return adapterResponse.getData().getText();
    }

    public XML loginAndLoadXML(String filePath) throws Exception {
        String ticket = this.getTicket();
        XML xml = XML.read(filePath);
        xml.selectNodeByXPath("//ticket").setText(ticket);
        return xml;
    }

    @Test
    public void testLogin() throws Exception {
        String ticket = this.getTicket();
        assertNotNull("token was not null?", ticket);
    }
}
