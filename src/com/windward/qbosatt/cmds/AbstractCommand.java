package com.windward.qbosatt.cmds;

import com.qbos.QTP.QTP;
import com.realops.foundation.adapterframework.AdapterRequest;
import com.realops.foundation.adapterframework.AdapterResponse;

/**
 * Created with IntelliJ IDEA.
 * User: aglover
 * Date: 8/1/13
 * Time: 2:00 PM
 */
public abstract class AbstractCommand {
    private QTP qtpInstance;

    /**
     * IoC mainly used for testing; that is, a mock instance can be provided
     * see getQtpInstance for more details
     *
     * @param instance
     */
    public void setQtpInstance(QTP instance) {
        this.qtpInstance = instance;
    }

    /**
     * This method is used internally to return either a
     * supplied QTP instance (most likely a mock one) or if none
     * if present as a member variable, a new one is instantiated
     *
     * @return QTP instance
     * @throws Exception
     */
    protected QTP getQtpInstance() throws Exception {
        if (this.qtpInstance == null) {
            return new QTP();
        } else {
            return this.qtpInstance;
        }
    }

    public abstract AdapterResponse execute(AdapterRequest adapterRequest);
}
