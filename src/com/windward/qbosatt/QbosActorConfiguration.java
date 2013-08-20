package com.windward.qbosatt;

import com.realops.foundation.adapterframework.configuration.AdapterConfigurationException;
import com.realops.foundation.adapterframework.configuration.BaseAdapterConfiguration;

import java.util.Hashtable;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: aglover
 * Date: 7/26/13
 * Time: 2:05 PM
 *
 */
public class QbosActorConfiguration extends BaseAdapterConfiguration {

    private final String COMPACT_XML_KEY = "compact-xml";

    public QbosActorConfiguration(String adapterId) {
        super(adapterId);
        addValidKey(COMPACT_XML_KEY);
    }

    public QbosActorConfiguration(String id, Hashtable defaults) {
        super(id, defaults);
        addValidKey(COMPACT_XML_KEY);
    }

    public QbosActorConfiguration(String id, Hashtable defaults, Set validKeys, Set requiredKeys) throws AdapterConfigurationException {
        super(id, defaults, validKeys, requiredKeys);
        addValidKey(COMPACT_XML_KEY);
    }
    public boolean shouldCompactXML(){
       return this.getBooleanProperty(COMPACT_XML_KEY);
    }
}
