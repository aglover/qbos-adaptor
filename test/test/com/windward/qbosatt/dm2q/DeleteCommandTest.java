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
public class DeleteCommandTest extends QbosAdapterTest {
    @Test
    public void testCommand() throws Exception {
        XML xml = this.loginAndLoadXML("etc/dm2q/test-delete-req.xml");
        AdapterRequest request = new AdapterRequest(xml);
        QbosActor adaptor = new QbosActor();
        AdapterResponse adapterResponse = adaptor.performAction(request);

        assertNotNull("adapterResponse was not null?", adapterResponse);
        assertEquals(Status.SUCCESS.toString(), adapterResponse.getData().getChild("status").getText());
    }
}
