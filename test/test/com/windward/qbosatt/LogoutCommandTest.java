package test.com.windward.qbosatt;

import com.qbos.QTP.InvalidCredentialsException;
import com.qbos.QTP.QTP;
import com.realops.common.xml.XML;
import com.realops.foundation.adapterframework.AdapterRequest;
import com.realops.foundation.adapterframework.AdapterResponse;
import com.windward.qbosatt.QbosAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: aglover
 * Date: 8/1/13
 * Time: 5:02 PM
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(QTP.class)
public class LogoutCommandTest {

    @Test
    public void testLogoutRequest() throws Exception {
        PowerMockito.mockStatic(QTP.class);
        QTP qtpThing = mock(QTP.class);
        when(QTP.Create("dm2q", "0C4F7501U1143U5955UDC8C1EB43B06C988")).thenReturn(qtpThing);
        XML xml = XML.read("etc/test-logout-req.xml");
        AdapterRequest request = new AdapterRequest(xml);
        QbosAdapter adaptor = new QbosAdapter();
        adaptor.setQtpInstance(qtpThing);
        AdapterResponse adapterResponse = adaptor.performAction(request);
        assertNotNull("adapterResponse was not null?", adapterResponse);
        verify(qtpThing, times(1)).logOut();
        assertEquals("true", adapterResponse.getData().getChild("data").getText());
    }

    @Test
    public void testLogoutRequestWithExpiredTicket() throws Exception {
        PowerMockito.mockStatic(QTP.class);
        QTP qtpThing = mock(QTP.class);
        when(QTP.Create("dm2q", "0C4F7501U1143U5955UDC8C1EB43B06C988")).thenThrow(new InvalidCredentialsException("test"));
        XML xml = XML.read("etc/test-logout-req.xml");
        AdapterRequest request = new AdapterRequest(xml);
        QbosAdapter adaptor = new QbosAdapter();
        adaptor.setQtpInstance(qtpThing);
        AdapterResponse adapterResponse = adaptor.performAction(request);
        assertNotNull("adapterResponse was not null?", adapterResponse);
        verify(qtpThing, times(0)).logOut(); //logout never called b/c ticket expired
        assertEquals("true", adapterResponse.getData().getChild("data").getText());
    }
}
