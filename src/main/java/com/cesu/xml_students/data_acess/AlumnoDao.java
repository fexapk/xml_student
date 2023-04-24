package com.cesu.xml_students.data_acess;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import java.util.HashMap;

import java.lang.IllegalArgumentException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import com.cesu.xml_students.pojo.*;

public class AlumnoDao implements Dao<Alumno> {

    private Document document = null;
    private Map<Integer , Element> queryData;

    public AlumnoDao(Document document) {
        if (document == null)
            throw new IllegalArgumentException("document must point to a dom");
        this.document = document;
        queryData = new HashMap<>();
        populateStructures();
    }

    /**
     * Load all the Data to memory in a ArrayList
     * @return
     */
    @Override
    public List<Alumno> getAll() {
        List<Alumno> alumnos = new ArrayList<>();
        NodeList nodes = document.getElementsByTagName("Alumno");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element aElement = (Element) nodes.item(i);
            alumnos.add(parse(aElement));
        }
        return alumnos;
    }

    @Override
    public Alumno get(int id) {
        Element queryE = queryData.get(id);
        if (queryE == null)
            return null;
        return parse(queryE);
    }

    @Override
    public void save(Alumno t) {

        Element tElement = transform(t);
        document.getDocumentElement().appendChild(tElement);

        queryData.putIfAbsent(t.getId(), tElement);
    }

    @Override
    public void delete(int id) {
        Element rmElement = queryData.get(id);
        if (rmElement == null) {
            return;
        }
        rmElement.getParentNode().removeChild(rmElement);
        queryData.remove(id);
    }

    @Override
    public void update(int id, Alumno t) {
        Element root = document.getDocumentElement();
        root.removeChild(queryData.get(id));

        Element updatedAlumno = transform(t);
        root.appendChild(updatedAlumno);

        queryData.put(id, updatedAlumno);
    }

    private void populateStructures() {
        if (document == null)
            throw new IllegalStateException("Can't load info to memory if file has not been read");

        NodeList nodes = document.getElementsByTagName("Alumno");
        for (int i = 0; i < nodes.getLength(); i++) {

            Element alumno = (Element) nodes.item(i);
            Alumno tmp = parse(alumno);

            queryData.put(tmp.getId(), alumno);
        }
    }

    /**
     * Parses the data inside of each element to an Alumno Obj
     * @param alumno
     * @return
     */
    private Alumno parse(Element alumnoElement) {
        int id = Integer.parseInt(alumnoElement.getElementsByTagName("id").item(0).getTextContent());
        String nombre = alumnoElement.getElementsByTagName("Nombre").item(0).getTextContent();
        String apellido = alumnoElement.getElementsByTagName("Apellido").item(0).getTextContent();
        String grado = alumnoElement.getElementsByTagName("Grado").item(0).getTextContent();
        String fechaFin = alumnoElement.getElementsByTagName("FechaFin").item(0).getTextContent();

        Alumno alumno = new Alumno(id, nombre, apellido, grado, fechaFin);
        alumno.setGraduado(defineGraduado(alumnoElement.getAttribute("Graduado")));
        return alumno;
    }

    /**
     * Return wether the Gradudado attribute exist and should be modified
     * @param data
     * @return
     */
    private boolean defineGraduado(String data) {
        if (data == null) {
            return false;
        } else if (data.equalsIgnoreCase("Si")) {
            return true;
        } 
        return false;
    }

    /**
     * Transforms Alumno Obj to it's dom counterpart
     * @param alumno (Alumno)
     * @return (Element)
     */
    private Element transform(Alumno alumno) {
        Element alumElement = document.createElement("Alumno");

        Element id = document.createElement("id");
        id.appendChild(document.createTextNode(String.valueOf(alumno.getId())));
        alumElement.appendChild(id);

        alumElement.appendChild(createData("Nombre", alumno.getNombre()));
        alumElement.appendChild(createData("Apellido", alumno.getApellido()));
        alumElement.appendChild(createData("Grado", alumno.getGrado()));
        alumElement.appendChild(createData("FechaFin", alumno.getFechaFin()));

        return alumElement;
    }

    /**
     * Creates DOM element with given tag and text node
     * @param tag (String)
     * @param data (String)
     * @return (Element)
     */
    private Element createData(String tag, String data) {
        Element e = document.createElement(tag);
        e.appendChild(document.createTextNode(data));
        return e;
    }
}
