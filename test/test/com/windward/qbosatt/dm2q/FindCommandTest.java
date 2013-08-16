package test.com.windward.qbosatt.dm2q;

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
public class FindCommandTest extends QbosAdapterTest{

    @Test
    public void testFindCommand() throws Exception {

        XML xml = this.loginAndLoadXML("etc/dm2q/test-find-req.xml");
        QbosAdapter adapter = new QbosAdapter();
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
