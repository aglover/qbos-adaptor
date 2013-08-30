package test.com.windward.qbosatt.dm2q;

import com.realops.common.xml.XML;
import com.realops.foundation.adapterframework.AdapterRequest;
import com.realops.foundation.adapterframework.AdapterResponse;
import com.windward.qbosatt.QbosActor;
import org.junit.Test;

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
public class FindCommandTest extends QbosAdapterTest{

    @Test
    public void testFindCommand() throws Exception {

        XML xml = this.loginAndLoadXML("etc/dm2q/test-find-req.xml");
        QbosActor adapter = new QbosActor();
        AdapterRequest request = new AdapterRequest(xml);
        AdapterResponse adapterResponse = adapter.performAction(request);
        assertNotNull("adapterResponse was not null?", adapterResponse);

        XML data = adapterResponse.getData().getChild("data");
        assertNotNull("data null?", data);
        String count = data.getChild("count").getText();
        assertTrue(Integer.parseInt(count) > 2);
        assertTrue(data.getChild("items").hasChildren());
    }
}
