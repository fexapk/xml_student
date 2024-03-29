package com.cesu.xml_students.pojo;

import java.util.Objects;

public class Alumno implements Comparable<Alumno> {
    private int id;
    private String nombre, apellido, grado, fechaFin;
    private boolean isGraduado = false;

    public Alumno(int id, String nombre, String apellido, String grado, String fechaFin) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.grado = grado;
        this.fechaFin = fechaFin;
    }

    public Alumno(Alumno source) {
        this(source.id, source.nombre, source.apellido, source.grado, source.fechaFin);
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getGrado() {
        return this.grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getFechaFin() {
        return this.fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public boolean isGraduado() {
        return isGraduado;
    }

    public void setGraduado(boolean isGraduado) {
        this.isGraduado = isGraduado;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", apellido='" + getApellido() + "'" +
            ", grado='" + getGrado() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", isGraduado='" + isGraduado() + "'" +
            "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Alumno)) {
            return false;
        }
        Alumno alumno = (Alumno) o;
        return id == alumno.id && Objects.equals(nombre, alumno.nombre) && Objects.equals(apellido, alumno.apellido) && Objects.equals(grado, alumno.grado) && Objects.equals(fechaFin, alumno.fechaFin) && isGraduado == alumno.isGraduado;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, apellido, grado, fechaFin, isGraduado);
    }

    @Override
    public int compareTo(Alumno o) {
        return Integer.compare(this.id, o.id);
    }
}
