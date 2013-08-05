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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: aglover
 * Date: 8/4/13
 * Time: 8:26 PM
 */
public class PingCommandTest {
    @Test
    public void testPingRequest() throws Exception {
        QTP qtpThing = mock(QTP.class);
        when(qtpThing.ping()).thenReturn(true);
        XML xml = XML.read("etc/test-ping-req.xml");
        AdapterRequest request = new AdapterRequest(xml);
        QbosAdapter adaptor = new QbosAdapter();
        adaptor.setQtpInstance(qtpThing);
        AdapterResponse adapterResponse = adaptor.performAction(request);
        assertNotNull("adapterResponse was not null?", adapterResponse);
        verify(qtpThing, times(1)).ping();

        assertEquals("true", adapterResponse.getData().getText());
    }

    @Test
    public void testPingFalseRequest() throws Exception {
        QTP qtpThing = mock(QTP.class);
        when(qtpThing.ping()).thenReturn(false);
        XML xml = XML.read("etc/test-ping-req.xml");
        AdapterRequest request = new AdapterRequest(xml);
        QbosAdapter adaptor = new QbosAdapter();
        adaptor.setQtpInstance(qtpThing);
        AdapterResponse adapterResponse = adaptor.performAction(request);
        assertNotNull("adapterResponse was not null?", adapterResponse);
        verify(qtpThing, times(1)).ping();

        assertEquals("false", adapterResponse.getData().getText());
    }
}
