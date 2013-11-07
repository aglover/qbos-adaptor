package com.windward.qbosatt.cmds;

import com.qbos.QTP.Applet;
import com.qbos.QTP.KeyNotFoundException;
import com.qbos.QTP.QTP;
import com.qbos.QTP.QuillDataRow;
import com.realops.common.enumeration.Status;
import com.realops.common.xml.InvalidXMLFormatException;
import com.realops.common.xml.XML;
import com.realops.foundation.adapterframework.AdapterRequest;
import com.realops.foundation.adapterframework.AdapterResponse;
import com.realops.foundation.adapterframework.configuration.BaseAdapterConfiguration;
import com.windward.qbosatt.QbosActorConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: aglover
 * Date: 8/1/13
 * Time: 2:00 PM
 */
public abstract class AbstractCommand {
    private QTP qtpInstance;
    private QbosActorConfiguration config;

    public void setConfig(BaseAdapterConfiguration aConfig){
        if (aConfig instanceof QbosActorConfiguration){
            this.config = (QbosActorConfiguration)aConfig;
        }
    }
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
            return new AdapterResponse(duration, Status.SUCCESS.toString(), response, Status.SUCCESS);
        } catch (Exception e){
            long duration = new Date().getTime()- startTime;
            XML response = new XML("response");
            response.addChild("status").setText(Status.ERROR.toString());
            response.addChild("message").setText(e.getLocalizedMessage());
            // If we return Status.ERROR here, the workflow engine will throw a compensation, which stops
            // the workflow. So we need to return an adapter response of success, and a mesage and let
            // the workflow deal with the response.
            return new AdapterResponse(duration, Status.ERROR.toString(), response, Status.SUCCESS);
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
        boolean shouldCompact = this.config!=null && this.config.shouldCompactXML();
        if (fields!=null && fields.hasChildren()){
            for (XML field : fields.getChildren()) {
                if (field.hasChildren()){
                    if (shouldCompact)
                        applet.add(field.getName(), field.toCompactString());
                    else
                        applet.add(field.getName(), field.toPrettyString());
                } else {
                    applet.add(field.getName(), field.getText());
                }
            }
        }
    }

    protected XML createItemFromMap(QuillDataRow row) throws KeyNotFoundException{
        XML item = new XML("item");
        for (String key : row.keySet()) {
            try {
                Document doc = row.getAsXmlDocument(key);
                Element value = doc.getDocumentElement();
                if (value!=null){
                    item.addChild(key).addChild(getXMLFromDocument(value));
                } else {
                    item.addChild(key).setText(row.getAsString(key));
                }
            } catch (Exception spe){
                item.addChild(key).setText(row.getAsString(key));
            }
        }
        return item;
    }

    //method to convert Document to XML
    private XML getXMLFromDocument(Element doc)
    {
        try
        {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty("omit-xml-declaration", "yes");
            transformer.transform(domSource, result);
            return XML.read(new StringReader(writer.toString()));
        }
        catch(TransformerException ex) {
            return null;
        } catch (InvalidXMLFormatException e) {
            return null;
        }
    }
}
