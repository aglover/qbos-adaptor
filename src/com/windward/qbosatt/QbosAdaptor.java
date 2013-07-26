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
public class QbosAdaptor extends BaseAdapterConfiguration {

    public QbosAdaptor(String adapterId) {
        super(adapterId);
    }

    public QbosAdaptor(String id, Hashtable defaults) {
        super(id, defaults);
    }

    public QbosAdaptor(String id, Hashtable defaults, Set validKeys, Set requiredKeys) throws AdapterConfigurationException {
        super(id, defaults, validKeys, requiredKeys);
    }
}
