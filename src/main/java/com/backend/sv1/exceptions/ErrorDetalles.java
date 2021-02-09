/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backend.sv1.exceptions;

import java.util.Date;

/**
 *
 * @author Andy
 */
public class ErrorDetalles {
    
    private Date timestamp;
    private String mensaje;
    private String detalles;

    public ErrorDetalles(Date timestamp, String mensaje, String detalles) {
        super();
        this.timestamp = timestamp;
        this.mensaje = mensaje;
        this.detalles = detalles;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }
        
    
    
}
