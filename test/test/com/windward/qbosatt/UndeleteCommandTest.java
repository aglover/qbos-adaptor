package test.com.windward.qbosatt;

import com.qbos.QTP.Applet;
import com.qbos.QTP.QTP;
import com.realops.common.enumeration.Status;
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
 * Time: 8:33 PM
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(QTP.class)
public class DeleteCommandTest {
    @Test
    public void testUndeleteRequest() throws Exception {
        PowerMockito.mockStatic(QTP.class);
        QTP qtpThing = mock(QTP.class);
        when(QTP.Create("dm2q", "0C4F7501U1143U5955UDC8C1EB43B06C988")).thenReturn(qtpThing);
        when(qtpThing.undeleteRecord(any(Applet.class))).thenReturn(new Long(10000000000L));
        XML xml = XML.read("etc/test-undelete-req.xml");
        AdapterRequest request = new AdapterRequest(xml);
        QbosAdapter adaptor = new QbosAdapter();
        adaptor.setQtpInstance(qtpThing);
        AdapterResponse adapterResponse = adaptor.performAction(request);
        PowerMockito.verifyStatic(Mockito.times(1));
        QTP.Create("dm2q", "0C4F7501U1143U5955UDC8C1EB43B06C988");
        assertNotNull("adapterResponse was not null?", adapterResponse);
        verify(qtpThing, times(1)).deleteRecord(any(Applet.class));
        assertEquals("10000000000", adapterResponse.getData().getChild("data").getText());
        assertEquals(Status.SUCCESS.toString(), adapterResponse.getData().getChild("status").getText());
    }

    @Test
    public void testDeleteRequestWithException() throws Exception {
        PowerMockito.mockStatic(QTP.class);
        QTP qtpThing = mock(QTP.class);
        when(QTP.Create("dm2q", "0C4F7501U1143U5955UDC8C1EB43B06C988")).thenThrow(Exception.class);
        XML xml = XML.read("etc/untest-delete-req.xml");
        AdapterRequest request = new AdapterRequest(xml);
        QbosAdapter adaptor = new QbosAdapter();
        adaptor.setQtpInstance(qtpThing);
        AdapterResponse adapterResponse = adaptor.performAction(request);
        PowerMockito.verifyStatic(Mockito.times(1));
        QTP.Create("dm2q", "0C4F7501U1143U5955UDC8C1EB43B06C988");
        assertNotNull("adapterResponse was not null?", adapterResponse);
        verify(qtpThing, times(0)).undeleteRecord(any(Applet.class));
        assertEquals(Status.ERROR.toString(), adapterResponse.getData().getChild("status").getText());
    }
}
