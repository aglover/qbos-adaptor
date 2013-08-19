package com.windward.qbosatt.cmds;

import com.qbos.QTP.Applet;
import com.qbos.QTP.QTP;
import com.realops.common.enumeration.Status;
import com.realops.common.xml.XML;
import com.realops.foundation.adapterframework.AdapterRequest;
import com.realops.foundation.adapterframework.AdapterResponse;

import java.util.Date;

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
        return (this.qtpInstance == null) ? new QTP() : this.qtpInstance;
    }

    /**
     * Used by the factory to execute the command. This keeps track of execution time and
     * handles all exceptions thrown by the commands. Appropriately wrapping the response
     * in an adaptor response object.
     *
     * @param adapterRequest - the request made by the workflow
     * @return AdapterResponse - the response handed back to the adapter manager.
     */
    public AdapterResponse execute(AdapterRequest adapterRequest){
        long startTime = new Date().getTime();
        try {
            XML data = this.executeCommand(adapterRequest.getData());
            long duration = new Date().getTime()- startTime;
            XML response = new XML("response");
            response.addChild("status").setText(Status.SUCCESS.toString());
            response.addChild("message").setText("Command successful");
            response.addChild(data);
            return new AdapterResponse(duration, "SUCCESS", response, Status.SUCCESS);
        } catch (Exception e){
            long duration = new Date().getTime()- startTime;
            XML response = new XML("response");
            response.addChild("status").setText(Status.ERROR.toString());
            response.addChild("message").setText(e.getLocalizedMessage());
            // If we return Status.ERROR here, the workflow engine will throw a compensation, which stops
            // the workflow. So we need to return an adapter response of success, and a mesage and let
            // the workflow deal with the response.
            return new AdapterResponse(duration, "FAILURE", response, Status.SUCCESS);
        }
    }

    /**
     * Abostract method for the concrete class to implement the command.
     *
     * @param  XML the XML provided by the adapter cal in workflow
     * @return XML Object to return in the data portion of the adatper response.
     * @throws Exception
     */
    public abstract XML executeCommand(XML requestXML) throws Exception;

    protected void setFieldsInApplet(XML fields, Applet applet){
        applet.clear();
        if (fields!=null && fields.hasChildren()){
            for (XML field : fields.getChildren()) {
                if (field.hasChildren()){
                    applet.add(field.getName(), field.toRawString());
                } else {
                    applet.add(field.getName(), field.getText());
                }
            }
        }

    }

}
