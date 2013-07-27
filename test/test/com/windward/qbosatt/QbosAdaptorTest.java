package test.com.windward.qbosatt;

import com.realops.common.xml.XML;
import com.realops.foundation.adapterframework.AdapterRequest;
import com.realops.foundation.adapterframework.AdapterResponse;
import com.windward.qbosatt.QbosAdaptor;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: aglover
 * Date: 7/26/13
 * Time: 3:23 PM
 */
public class QbosAdaptorTest {

    @Test
    public void testNotNullResponse() throws Exception {
        String dummyXML = "<adapter-request>\n" +
                "   <target-adapter>responseAdapter</target-adapter>\n" +
                "   <peer-location>\n" +
                "      <location>this</location>\n" +
                "      <peer-name/>\n" +
                "   </peer-location>\n" +
                "   <request-action/>\n" +
                "   <request-data>\n" +
                "</request-data>\n" +
                "</adapter-request>";
        XML xml = XML.read("etc/testreq.xml");
//        xml.setAttribute("test", "value");
//        xml.setText(dummyXML);
        System.out.println(xml.toPrettyString());
        AdapterRequest request = new AdapterRequest(xml);
        QbosAdaptor adaptor = new QbosAdaptor();
        AdapterResponse adapterResponse = adaptor.performAction(request);

        Assert.assertNotNull("adapterResponse was not null?", adapterResponse);
        System.out.println(adapterResponse.getData().toPrettyString());
        System.out.println("\n");
        System.out.println(adapterResponse.toXML().toPrettyString());

    }

}
