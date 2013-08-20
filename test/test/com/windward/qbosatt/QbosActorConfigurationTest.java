package test.com.windward.qbosatt;


import com.realops.common.xml.XML;
import com.windward.qbosatt.QbosActorConfiguration;
import org.junit.Test;

import java.lang.Exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Created with IntelliJ IDEA.
 * User: chris
 * Date: 8/20/13
 */
public class QbosActorConfigurationTest {

    @Test
    public void testFalse () throws Exception{
        XML xml = XML.read("etc/test-config-false.xml");
        assertNotNull("xml is null?", xml);

        QbosActorConfiguration config = new QbosActorConfiguration("qboss");
        assertNotNull("config is null?", config);

        config.fromXML(xml);
        assertFalse("property shoudl be false", config.shouldCompactXML());
    }

    @Test
    public void testTrue () throws Exception{
        XML xml = XML.read("etc/test-config-true.xml");
        assertNotNull("xml is null?", xml);

        QbosActorConfiguration config = new QbosActorConfiguration("qboss");
        assertNotNull("config is null?", config);

        config.fromXML(xml);
        assertTrue("property shoudl be false", config.shouldCompactXML());
    }

}