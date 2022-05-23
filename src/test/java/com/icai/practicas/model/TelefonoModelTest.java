package com.icai.practicas.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TelefonoModelTest
{

    @Test
    void testTelefono(){
        Telefono telefono = new Telefono("666666666");

        assertTrue(telefono.validar());

        // probamos con espacios
        telefono=new Telefono("666 666 666");
        assertTrue(telefono.validar());
    }

    @Test
    void testTelefonoNumeroDigitos(){
        Telefono telefono = new Telefono("7777");
        assertFalse(telefono.validar(), "Pocos digitos not pass");

        telefono=new Telefono("777788888888");
        assertFalse(telefono.validar(), "Demasiados digitos not pass");
    }

    @Test
    void testTelefonoPrefijo()
    {
        Telefono telefono = new Telefono("+31 666666666");
        assertTrue(telefono.validar());

        telefono = new Telefono("+31 6666666");
        assertFalse(telefono.validar());

        telefono = new Telefono("++31 666666666");
        assertFalse(telefono.validar());
    }

    @Test
    void testTelefonoMalformed(){
        Telefono telefono = new Telefono("FF77777777");
        assertFalse(telefono.validar());

        // probamos con minuscula
        telefono=new Telefono("77777777a");
        assertFalse(telefono.validar());
    }

}
