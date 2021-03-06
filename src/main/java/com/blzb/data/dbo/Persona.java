package com.blzb.data.dbo;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Created by apimentel on 4/25/17.
 */
@Entity
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre;
    private String apellido;
    private String boleta;
    @Temporal(value = TemporalType.DATE)
    private Date fechaNacimiento;

    private String password;

    private String sexo;

    private String carrera;

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL)
    List<Marca> marcas;

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL)
    List<Estado> estados;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getBoleta() {
        return boleta;
    }

    public void setBoleta(String boleta) {
        this.boleta = boleta;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public List<Marca> getMarcas() {
        return marcas;
    }

    public void setMarcas(List<Marca> marcas) {
        this.marcas = marcas;
    }

    public List<Estado> getEstados() {
        return estados;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Transient
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    @Transient
    public int getEdad(){
        if(fechaNacimiento != null) {
            return Period.between(
                    new Date(fechaNacimiento.getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    LocalDate.now()
            ).getYears();
        }else {
            return 0;
        }
    }
}
