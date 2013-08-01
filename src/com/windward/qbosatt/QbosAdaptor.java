package com.windward.qbosatt;

import com.qbos.QTP.QTP;
import com.realops.common.enumeration.StateEnum;
import com.realops.common.enumeration.Status;
import com.realops.common.xml.InvalidXMLFormatException;
import com.realops.common.xml.XML;
import com.realops.foundation.adapterframework.AbstractActorAdapter;
import com.realops.foundation.adapterframework.AdapterException;
import com.realops.foundation.adapterframework.AdapterRequest;
import com.realops.foundation.adapterframework.AdapterResponse;

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

        if (adapterRequest.getAction().equalsIgnoreCase("serverLogin")) {
            XML loginXML = adapterRequest.getData();
            String qsi = loginXML.getChild("qsi").getText();
            String account = loginXML.getChild("account").getText();
            String psswrd = loginXML.getChild("password").getText();
            try {
                QTP qtp = this.getQtpInstance();
                qtp.logIn(qsi, account, psswrd);
                String token = qtp.getTicket();

                return new AdapterResponse(300, "QTP ticket: " + token,
                        new XML("response").setText(token), Status.SUCCESS);
            } catch (Exception e) {
                //do something!
            }
        } else {
//            System.out.println("action is: " + adapterRequest.getData().toPrettyString());
            XML datastuff = adapterRequest.getData();

//            System.out.println("param 1: " + datastuff.getChild("param1").getText());
        }

        try {
            return new AdapterResponse(300, "Foo", new XML("response").setText("FOO"), Status.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void shutdown() throws AdapterException {
        setState(StateEnum.STOPPED);
    }
}
