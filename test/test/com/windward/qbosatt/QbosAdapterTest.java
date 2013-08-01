package test.com.windward.qbosatt;

import com.qbos.QTP.QTP;
import com.qbos.QTP.UnknownQtpException;
import com.realops.common.xml.InvalidXMLFormatException;
import com.realops.common.xml.XML;
import com.realops.foundation.adapterframework.AdapterRequest;
import com.realops.foundation.adapterframework.AdapterResponse;
import com.windward.qbosatt.QbosAdapter;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: aglover
 * Date: 7/26/13
 * Time: 3:23 PM
 */
public class QbosAdapterTest {

    @Test
    public void testLoginRequest() throws Exception {
        QTP qtpThing = mock(QTP.class);
        when(qtpThing.getTicket()).thenReturn("test-ticket");
        XML xml = XML.read("etc/test-login-req.xml");
        AdapterRequest request = new AdapterRequest(xml);
        QbosAdapter adaptor = new QbosAdapter();
        adaptor.setQtpInstance(qtpThing);
        AdapterResponse adapterResponse = adaptor.performAction(request);
        assertNotNull("adapterResponse was not null?", adapterResponse);
        verify(qtpThing, times(1)).logIn("dm2q", "cdale@windwardits.com", "Rilda411");

        assertEquals("test-ticket", adapterResponse.getData().getText());
    }

    @Test
    public void testFailureLoginRequest() throws Exception {
        QTP qtpThing = mock(QTP.class);
        //ensure that an exception is thrown w/bab qtpticket
        doThrow(new UnknownQtpException()).when(qtpThing).logIn("", "cdale@windwardits.com", "Rilda411");
        XML xml = XML.read("etc/test-login-req-err.xml");
        AdapterRequest request = new AdapterRequest(xml);
        QbosAdapter adaptor = new QbosAdapter();
        adaptor.setQtpInstance(qtpThing);
        AdapterResponse adapterResponse = adaptor.performAction(request);
        assertNotNull("adapterResponse was not null?", adapterResponse);
        verify(qtpThing, times(1)).logIn("", "cdale@windwardits.com", "Rilda411");
        assertEquals("FAILURE", adapterResponse.getData().getText());
    }

    /**
     * <adapter-response>
     * <execution-duration>3000</execution-duration>
     * <status>success</status>
     * <message/>
     * <data>
     * <response>foo</response>
     * </data>
     * </adapter-response>
     */
    @Test
    public void testXMLObject() {
        XML parent = new XML("adapter-response");
        parent.addChild(new XML("execution-duration").setText("0"));
        parent.addChild(new XML("status").setText("success"));
        parent.addChild(new XML("message"));
        XML child = new XML("data");
        child.addChild(new XML("response").setText("FOO"));
        parent.addChild(child);
        assertEquals("FOO", parent.getChild("data").getChild("response").getText());
    }

    @Test
    public void testRequestObjectCalls() throws InvalidXMLFormatException, IOException {
        XML xml = XML.read("etc/test-login-req.xml");
        AdapterRequest request = new AdapterRequest(xml);
        assertEquals("serverLogin", request.getAction());
        assertNotNull(request.getData());
        assertEquals("dm2q", request.getData().getChild("qsi").getText());
    }

    @Test
    public void testNotNullResponse() throws Exception {
        XML xml = XML.read("etc/test-invalid-req.xml");
        AdapterRequest request = new AdapterRequest(xml);
        QbosAdapter adaptor = new QbosAdapter();
        AdapterResponse adapterResponse = adaptor.performAction(request);
        assertNotNull("adapterResponse was not null?", adapterResponse);
        assertEquals("FAILURE", adapterResponse.getData().getText());
    }

}
