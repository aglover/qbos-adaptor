package test.com.windward.qbosatt.dm2q;

import com.realops.common.enumeration.Status;
import com.realops.common.xml.XML;
import com.realops.foundation.adapterframework.AdapterRequest;
import com.realops.foundation.adapterframework.AdapterResponse;
import com.windward.qbosatt.QbosActor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: cschroed
 * Date: 8/4/13
 * Time: 8:16 PM
 */
public class UpdateCommandTest extends QbosAdapterTest {
    @Test
    public void testUpdateCommand() throws Exception {
        XML xml = this.loginAndLoadXML("etc/dm2q/test-update-req.xml");
        AdapterRequest request = new AdapterRequest(xml);
        QbosActor adaptor = new QbosActor();
        AdapterResponse adapterResponse = adaptor.performAction(request);

        assertNotNull("adapterResponse was not null?", adapterResponse);
        assertEquals("Adaptor was successful?", Status.SUCCESS.toString(), adapterResponse.getData().getChild("status").getText());

        String idString = adapterResponse.getData().getChild("data").getText();
        assertNotNull("id was null?", idString);
        assertTrue("id was set?", idString.length()>0);
        assertTrue("id greater than zero?", Long.parseLong(idString)>0);
    }
}
