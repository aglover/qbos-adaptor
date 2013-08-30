package test.com.windward.qbosatt.dm2q;

import com.realops.common.xml.XML;
import com.realops.foundation.adapterframework.AdapterRequest;
import com.realops.foundation.adapterframework.AdapterResponse;
import com.windward.qbosatt.QbosActor;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: aglover
 * Date: 8/2/13
 * Time: 8:58 AM
 */
public class CreateCommandTest extends QbosAdapterTest{

    @Test
    public void testCreateRequest() throws Exception {
        XML xml = this.loginAndLoadXML("etc/dm2q/test-create-req.xml");
        AdapterRequest request = new AdapterRequest(xml);
        QbosActor adaptor = new QbosActor();
        AdapterResponse adapterResponse = adaptor.performAction(request);

        assertNotNull("adapterResponse was not null?", adapterResponse);

        assertNotNull("item not null?", adapterResponse.getData());
        String idString = adapterResponse.getData().getChild("data").getText();
        assertNotNull("id was null?", idString);
        assertTrue("id was set?", idString.length()>0);
        assertTrue("id greater than zero?", Long.parseLong(idString)>0);
    }

    @Test
    public void testCreateEmbeddedXML() throws Exception {
        XML xml = this.loginAndLoadXML("etc/dm2q/test-create-embedded-xml.xml");
        AdapterRequest request = new AdapterRequest(xml);
        QbosActor adaptor = new QbosActor();
        AdapterResponse adapterResponse = adaptor.performAction(request);

        assertNotNull("adapterResponse was not null?", adapterResponse);

        assertNotNull("item not null?", adapterResponse.getData());
        String idString = adapterResponse.getData().getChild("data").getText();
        assertNotNull("id was null?", idString);
        assertTrue("id was set?", idString.length()>0);
        assertTrue("id greater than zero?", Long.parseLong(idString)>0);
    }

}
