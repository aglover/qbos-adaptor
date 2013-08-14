package test.com.windward.qbosatt.dm2q;

import com.realops.common.xml.XML;
import com.realops.foundation.adapterframework.AdapterRequest;
import com.realops.foundation.adapterframework.AdapterResponse;
import com.windward.qbosatt.QbosAdapter;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

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
        QbosAdapter adaptor = new QbosAdapter();
        AdapterResponse adapterResponse = adaptor.performAction(request);

        assertNotNull("adapterResponse was not null?", adapterResponse);
//        System.out.println(adapterResponse.getExecutionStatus().toString());
//        System.out.println(adapterResponse.getMessage().toString());
//        System.out.println(adapterResponse.getData().toPrettyString());

    }
}
