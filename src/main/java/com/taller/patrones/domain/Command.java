package com.taller.patrones.domain;

/**
 * Interfaz Command para encapsular acciones ejecutables y reversibles.
 */
public interface Command {
    void execute();

    void undo();
}