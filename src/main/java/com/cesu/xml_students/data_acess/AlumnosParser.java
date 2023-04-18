package com.cesu.xml_students.data_acess;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.cesu.xml_students.pjo.Alumno;

import java.io.File;

import java.util.List;
import java.util.ArrayList;

public class AlumnosParser {

    private static final String 
        LINUX_PATH_REGEX = "^/([a-zA-Z0-9_-]+/?)+[a-zA-Z0-9_-]+\\.xml$", 
        WINDOWS_PATH_REGEX = "^(?:[a-zA-Z]:)?(?:\\(?:[a-zA-Z0-9_]+)+)*\\?[a-zA-Z0-9_]+\\.(?:xml|XML)$";
    private String filePath;
    private Document document = null;

    public AlumnosParser(String filePath) {
        if (filePath.isBlank() || filePath == null)
            throw new IllegalArgumentException("File path can not be null nor blank");
        if (!checkPath(filePath))
            throw new IllegalArgumentException("String must match path from OS");
        this.filePath = filePath;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Document getDocument() {
        if (document == null) 
            throw new IllegalStateException("Can't get the document from dom if none file has been parsed");
        return document;
    }
    
    /**
     * Using JAXP reads File and parse it's content to DOM
     * @return (boolean) 
     */
    public boolean readFile() {
        File alumnosData = new File(filePath);
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(alumnosData);
            document.getDocumentElement().normalize();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    public List<Alumno> loadAlumnos() {
        NodeList alumnos = document.getElementsByTagName("Alumno");
        List<Alumno> alumList = new ArrayList<>();
        for (int i = 0; i < alumnos.getLength(); i++) {
            Element alumno = (Element) alumnos.item(i);
            
        }
    }

    private Alumno parseData(Element alumno) {
        NodeList childNodes = alumno.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Element child = (Element) childNodes.item(i);
            String tagName = child.getTagName();
            switch (tagName) {
                case "id":

                    break;
                case ""
            }
        }
    }

    /**
     * Checks the give path to meet the path regex from linux or windows
     * @param path
     * @return
     */
    private boolean checkPath(String path) {
        String os = System.getProperty("os.name");
        if (os.equalsIgnoreCase("Windows"))
            return path.matches(WINDOWS_PATH_REGEX);
        else 
            return path.matches(LINUX_PATH_REGEX);       
    } 
}