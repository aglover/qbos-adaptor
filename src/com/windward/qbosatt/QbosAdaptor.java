package com.windward.qbosatt;

import com.qbos.QTP.QTP;
import com.realops.common.enumeration.StateEnum;
import com.realops.common.enumeration.Status;
import com.realops.common.xml.XML;
import com.realops.foundation.adapterframework.AbstractActorAdapter;
import com.realops.foundation.adapterframework.AdapterException;
import com.realops.foundation.adapterframework.AdapterRequest;
import com.realops.foundation.adapterframework.AdapterResponse;
import com.windward.qbosatt.cmds.AbstractCommand;
import org.apache.commons.lang.WordUtils;

import static java.lang.Class.forName;

/**
 * Created with IntelliJ IDEA.
 * User: aglover
 * Date: 7/26/13
 * Time: 2:14 PM
 */
public class QbosAdaptor extends AbstractActorAdapter {

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
    private QTP getQtpInstance() throws Exception {
        if (this.qtpInstance == null) {
            return new QTP();
        } else {
            return this.qtpInstance;
        }
    }

    @Override
    public AdapterResponse performAction(AdapterRequest adapterRequest) throws AdapterException, InterruptedException {

        System.out.println("action is: " + adapterRequest.getAction());
        try {
            AbstractCommand cmd = newCommand(adapterRequest);
            cmd.setQtpInstance(this.getQtpInstance());
            return cmd.execute(adapterRequest);
        } catch (Exception e) {
            return new AdapterResponse(300, "FAILURE: " + e.getLocalizedMessage(),
                    new XML("response").setText("FAILURE"), Status.ERROR);
        }
    }

    private AbstractCommand newCommand(AdapterRequest adapterRequest) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return (AbstractCommand) forName(getCmdClassName(adapterRequest)).newInstance();
    }

    private String getCmdClassName(AdapterRequest adapterRequest) {
        return "com.windward.qbosatt.cmds." + WordUtils.capitalize(adapterRequest.getAction()) + "Command";
    }

    @Override
    public void shutdown() throws AdapterException {
        setState(StateEnum.STOPPED);
    }
}
