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
 * Time: 8:16 PM
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(QTP.class)
public class UpdateCommandTest {
    @Test
    public void testUpdateCommand() throws Exception {
        PowerMockito.mockStatic(QTP.class);
        QTP qtpThing = mock(QTP.class);
        when(QTP.Create("dm2q", "0C4F7501U1143U5955UDC8C1EB43B06C988")).thenReturn(qtpThing);
        when(qtpThing.updateRecord(any(Applet.class))).thenReturn(new Long(10000000000L));
        XML xml = XML.read("etc/test-update-req.xml");
        AdapterRequest request = new AdapterRequest(xml);
        QbosAdapter adaptor = new QbosAdapter();
        adaptor.setQtpInstance(qtpThing);
        AdapterResponse adapterResponse = adaptor.performAction(request);
        PowerMockito.verifyStatic(Mockito.times(1));
        QTP.Create("dm2q", "0C4F7501U1143U5955UDC8C1EB43B06C988");
        assertNotNull("adapterResponse was not null?", adapterResponse);
        verify(qtpThing, times(1)).updateRecord(any(Applet.class));
        assertEquals("10000000000", adapterResponse.getData().getText());
    }
}
