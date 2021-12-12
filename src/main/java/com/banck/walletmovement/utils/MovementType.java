/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banck.walletmovement.utils;

/**
 *
 * @author jonavcar
 */
public enum MovementType {
    CARGO("CARGO") {
        @Override
        public boolean equals(String movementType) {
            return value.equals(movementType);
        }
    },
    ABONO("ABONO") {
        @Override
        public boolean equals(String movementType) {
            return value.equals(movementType);
        }
    };

    public final String value;

    public boolean equals(String movementType) {
        return value.equals(movementType);
    }

    private MovementType(String value) {
        this.value = value;
    }
}
