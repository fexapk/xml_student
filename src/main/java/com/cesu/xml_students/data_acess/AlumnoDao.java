package com.cesu.xml_students.data_acess;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import java.util.HashMap;

import java.lang.IllegalArgumentException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import com.cesu.xml_students.pojo.*;

public class AlumnoDao implements Dao<Alumno> {

    private Document document = null;

    private List<Alumno> alumnos;
    private Map<Integer , Element> queryData;

    public AlumnoDao(Document document) {
        if (document == null)
            throw new IllegalArgumentException("document must point to a dom");
        this.document = document;
        alumnos = new ArrayList<>();
        queryData = new HashMap<>();
        populateStructures();
    }

    /**
     * Load all the Data to memory in a ArrayList
     * @return
     */
    @Override
    public List<Alumno> getAll() {
        return alumnos;
    }

    @Override
    public Alumno get(int id) {
        int index = getIndexById(id);
        if (index == -1)
            return null;
        return new Alumno(alumnos.get(index));
    }

    @Override
    public void save(Alumno t) {
        if (alumnos.contains(t))
            return;
        

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
        alumnos.remove(getIndexById(id));
    }

    @Override
    public void update(int id, String[] params) {
        
    }

    private void insertIntoList(Alumno alumno) {
        int previousIndex = getIndexById(alumno.getId()-1);

    }

    /**
     * Uses binary search to get index of the required id
     * @param id
     * @return
     */
    private int getIndexById(int id) {
        int low = 0, high = alumnos.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int guessId = alumnos.get(mid).getId();
            if (guessId == id) 
                return mid;

            if (guessId > id) 
                high = mid -1;
            else 
                low = mid + 1;
        }
        return -high;
    }

    private void populateStructures() {
        if (document == null)
            throw new IllegalStateException("Can't load info to memory if file has not been read");

        NodeList nodes = document.getElementsByTagName("Alumno");
        for (int i = 0; i < nodes.getLength(); i++) {

            Element alumno = (Element) nodes.item(i);
            Alumno tmp = parse(alumno);

            alumnos.add(new Alumno(tmp));
            queryData.put(tmp.getId(), alumno);
        }
        Collections.sort(alumnos);
    }

    /**
     * Parses the data inside of each element to an Alumno Obj
     * @param alumno
     * @return
     */
    private Alumno parse(Element alumno) {
        NodeList childNodes = alumno.getChildNodes();
        // temp variables
        int id = 0;
        String nombre = null,
               apellido = null,
               grado = null,
               fechaFin = null;

        for (int i = 0; i < childNodes.getLength(); i++) {
            Element child = (Element) childNodes.item(i);
            String tagName = child.getTagName();
            String data = child.getTextContent();

            switch (tagName) {
                case "id":
                    id = Integer.parseInt(data);
                    break;
                case "Nombre":
                    nombre = data;
                    break;
                case "Apellido":
                    apellido = data;
                    break;
                case "Grado":
                    grado = data;
                case "FechaFin":
                    fechaFin = data;
                break;
                default:
            }
        }
        Alumno temp = new Alumno(id, nombre, apellido, grado, fechaFin);
        temp.setGraduado(defineGraduado(alumno.getAttribute("Graduado")));

        return temp;
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
        Element id = document.createElement("id");
        id.appendChild(document.createTextNode(data));
        return id;
    }
}
