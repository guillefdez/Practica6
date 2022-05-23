package com.icai.practicas.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class DNIModelTest
{

    @Test
    void testDNI(){
        DNI dni=new DNI("77777777B");

        assertTrue(dni.validar());

        dni=new DNI("77777777A");

        assertFalse(dni.validar(), "la letra no corresponde no esta bien implementado");
    }

    @Test
    void testDNIDigitNumber(){
        DNI dni=new DNI("7777T");
        assertFalse(dni.validar(), "Pocos digitos not pass");

        dni=new DNI("777788888888B");
        assertFalse(dni.validar(), "Demasiados digitos not pass");
    }

    @Test
    void testDNIOrderOf()
    {
        DNI dni=new DNI("Q7777777");
        assertFalse(dni.validar());
    }

    @Test
    void testDNISpecialChars(){
        DNI dni=new DNI("77777777@");
        assertFalse(dni.validar());

        // probamos con minuscula
        dni=new DNI("77777777a");
        assertFalse(dni.validar());
    }

    @Test
    void testDNIValid()
    {
        DNI dni=new DNI("00000000T");
        assertFalse(dni.validar());

        dni=new DNI("00000001R");
        assertFalse(dni.validar());

        dni=new DNI("99999999R");
        assertFalse(dni.validar());
    }
}
