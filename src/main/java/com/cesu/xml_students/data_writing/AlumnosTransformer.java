package com.cesu.xml_students.data_writing;

import com.cesu.xml_students.pjo.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;

import java.lang.IllegalArgumentException;

public class AlumnosTransformer {

    private String outFilePath = null;
    private Document document = null;
    private List<Alumno> alumList = null;

    public AlumnosTransformer(String outFilePath, Document document, List<Alumno> alumList) {
        if (outFilePath.isBlank() || outFilePath == null)
            throw new IllegalArgumentException("Must be a valid path");
        if (document == null || alumList == null)
            throw new IllegalArgumentException("Either document or list must point somewhere");
        this.outFilePath = outFilePath;
        this.document = document;
        this.alumList = alumList;
    }

    /**
     * Basically writes dom content to xml file, returns true if it succeded in writing the file
     * @return (boolean)
     */
    public boolean saveDocToFile() {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // enable indentation
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2"); // set indentation amount

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(outFilePath));
            transformer.transform(source, result);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    /**
     * Transforms Alumno Obj to it's dom counterpart
     * @param alumno (Alumno)
     * @return (Element)
     */
    public Element transformAlumno(Alumno alumno) {
        Element alumElement = document.createElement("Alumno");

        Element id = document.createElement("id");
        id.appendChild(document.createTextNode(String.valueOf(alumno.getId())));
        alumElement.appendChild(id);

        alumElement.appendChild(createStrData("Nombre", alumno.getNombre()));
        alumElement.appendChild(createStrData("Apellido", alumno.getApellido()));
        alumElement.appendChild(createStrData("Grado", alumno.getGrado()));
        alumElement.appendChild(createStrData("FechaFin", alumno.getFechaFin()));

        return alumElement;
    }

    /**
     * Creates DOM element with given tag and text node
     * @param tag (String)
     * @param data (String)
     * @return (Element)
     */
    private Element createStrData(String tag, String data) {
        Element id = document.createElement("id");
        id.appendChild(document.createTextNode(data));
        return id;
    }
}
