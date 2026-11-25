package com.calculator.testing.grupohugoasier;

import org.example.Calculator2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests de la clase Calculator2 (metodos y variables static).
 * Grupo: Hugo Valera y Asier Sanz.
 *
 * Aqui no hace falta @BeforeEach ni "new": los metodos static
 * se llaman con el nombre de la clase.
 */
@DisplayName("Calculator2 (static) - grupo Hugo y Asier")
@Tag("static")
public class Calculator2GrupoTest {

    @ParameterizedTest(name = "Calculator2.sum({0}, {1}) = {2}")
    @CsvSource({
            "2, 3, 5",
            "-1, -1, -2",
            "0, 0, 0",
            "2147483647, 1, -2147483648"   // desbordamiento de int
    })
    @DisplayName("Suma static")
    void sumaStatic(int a, int b, int esperado) {
        assertEquals(esperado, Calculator2.sum(a, b));
    }

    @Test
    @DisplayName("PI vale 3.14 y es una constante")
    void constantePI() {
        assertEquals(3.14f, Calculator2.PI, 0.0001f);
    }

    // @RepeatedTest: el mismo test 5 veces. Comprueba que un metodo static
    // da siempre el mismo resultado (no guarda nada entre llamadas).
    @RepeatedTest(value = 5, name = "Repeticion {currentRepetition} de {totalRepetitions}")
    @DisplayName("La suma static siempre da lo mismo")
    void siempreIgual(RepetitionInfo info) {
        int n = info.getCurrentRepetition();
        assertEquals(n * 2, Calculator2.sum(n, n));
    }
}
