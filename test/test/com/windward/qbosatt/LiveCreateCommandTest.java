package test.com.windward.qbosatt;

import com.realops.common.xml.XML;
import com.realops.foundation.adapterframework.AdapterRequest;
import com.realops.foundation.adapterframework.AdapterResponse;
import com.windward.qbosatt.QbosAdapter;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: aglover
 * Date: 8/2/13
 * Time: 8:58 AM
 */
public class LiveCreateCommandTest extends LiveQbosAdapterTest{

    @Test
    public void testCreateRequest() throws Exception {
        XML xml = this.loginAndLoadXML("etc/test-create-req.xml");
        AdapterRequest request = new AdapterRequest(xml);
        QbosAdapter adaptor = new QbosAdapter();
        AdapterResponse adapterResponse = adaptor.performAction(request);

        assertNotNull("adapterResponse was not null?", adapterResponse);
        System.out.println(adapterResponse.getExecutionStatus().toString());
        System.out.println(adapterResponse.getMessage().toString());
        System.out.println(adapterResponse.getData().toPrettyString());
    }

}
