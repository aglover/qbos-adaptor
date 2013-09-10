package test.com.windward.qbosatt;


import com.qbos.QTP.QTP;
import com.qbos.QTP.QuillDataTable;
import com.realops.common.enumeration.Status;
import com.realops.common.xml.XML;
import com.realops.foundation.adapterframework.AdapterRequest;
import com.realops.foundation.adapterframework.AdapterResponse;
import com.windward.qbosatt.QbosActor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.BufferedReader;
import java.io.FileReader;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


/**
 * Created with IntelliJ IDEA.
 * User: aglover
 * Date: 8/2/13
 * Time: 8:58 AM
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(QTP.class)
public class ReadCommandTest {

    @Test
    public void testReadRequest() throws Exception {
        PowerMockito.mockStatic(QTP.class);
        QTP qtpThing = mock(QTP.class);
        when(QTP.Create("dm2q", "0C4F7501U1143U5955UDC8C1EB43B06C988")).thenReturn(qtpThing);

        String json = readFileAsString("etc/test-read-response.json");
//        System.out.println("JSON: "+json);
        QuillDataTable quilThing = new QuillDataTable(json, 1);
        when(qtpThing.readRecord(any(long.class), any(long.class))).thenReturn(quilThing);


        XML xml = XML.read("etc/test-read-req.xml");
        AdapterRequest request = new AdapterRequest(xml);
        QbosActor adaptor = new QbosActor();
        adaptor.setQtpInstance(qtpThing);
        AdapterResponse adapterResponse = adaptor.performAction(request);
        PowerMockito.verifyStatic(Mockito.times(1));
        QTP.Create("dm2q", "0C4F7501U1143U5955UDC8C1EB43B06C988");
        assertNotNull("adapterResponse was not null?", adapterResponse);
        verify(qtpThing, times(1)).readRecord(any(long.class), any(long.class));

        XML data = adapterResponse.getData();
//        System.out.println(data.toPrettyString());
        assertEquals(Status.SUCCESS.toString(), data.getChild("status").getText());
        assertTrue("xml node should have children", data.getChild("data").getChild("item").getChild("xml").hasChildren());
        assertFalse("string node should NOT have children", data.getChild("data").getChild("item").getChild("string").hasChildren());
    }



    private static String readFileAsString(String filePath)
            throws java.io.IOException{
        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(
        new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        return fileData.toString();
    }

}
