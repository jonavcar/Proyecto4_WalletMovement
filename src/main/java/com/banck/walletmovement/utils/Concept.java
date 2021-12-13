/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banck.walletmovement.utils;

/**
 *
 * @author jonavcar
 */
public enum Concept {
    RETIRO("RETIRO"),
    DEPOSITO("DEPOSITO"),
    TRANSFERENCIA("TRANSFERENCIA"),
    AUTOMATICO("AUTOMATICO"),
    ENVIO_MOVIL("ENVIO-MOVIL");

    public final String value;

    @Override
    public String toString() {
        return value;
    }

    public boolean equalsName(String otherValue) {
        return value.equals(otherValue);
    }

    private Concept(String value) {
        this.value = value;
    }
}
