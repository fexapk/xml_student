package com.cesu.xml_students.data_acess;

import org.w3c.dom.Document;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;

import java.lang.IllegalArgumentException;

public class DomWriter {

    private String outFilePath = null;
    private Document document = null;

    public DomWriter(String outFilePath, Document document) {
        if (outFilePath.isBlank() || outFilePath == null)
            throw new IllegalArgumentException("Must be a valid path");
        if (document == null)
            throw new IllegalArgumentException("Either document or list must point somewhere");
        this.outFilePath = outFilePath;
        this.document = document;
    }

    /**
     * Basically writes dom content to xml file, returns true if it succeded in writing the file
     * @return (boolean)
     */
    public boolean save() {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(outFilePath));
            transformer.transform(source, result);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            return false;
        }
        return true;
    }

}
