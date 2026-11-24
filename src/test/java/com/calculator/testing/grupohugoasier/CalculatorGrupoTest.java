package com.calculator.testing.grupohugoasier;

import org.example.Calculator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Bateria de tests unitarios de la clase Calculator.
 * Grupo: Hugo Valera y Asier Sanz.
 *
 * Los tests estan agrupados con @Nested por tipo de operacion,
 * asi en el informe de JUnit se ve un arbol ordenado.
 */
@DisplayName("Calculator - grupo Hugo y Asier")
public class CalculatorGrupoTest {

    // Un objeto nuevo para cada test, para que un test no afecte a otro
    private Calculator calculator;
    private static int testsEjecutados;

    // ---------- CICLO DE VIDA ----------

    // @BeforeAll: se ejecuta UNA vez antes de todos los tests (tiene que ser static)
    @BeforeAll
    static void antesDeTodo() {
        testsEjecutados = 0;
        System.out.println("== Empiezan los tests de Calculator ==");
    }

    // @BeforeEach: se ejecuta antes de CADA test -> calculadora limpia
    @BeforeEach
    void antesDeCadaTest() {
        calculator = new Calculator();
    }

    // @AfterEach: se ejecuta despues de CADA test
    @AfterEach
    void despuesDeCadaTest() {
        testsEjecutados++;
    }

    // @AfterAll: se ejecuta UNA vez al final de todo
    @AfterAll
    static void despuesDeTodo() {
        System.out.println("== Fin: " + testsEjecutados + " tests ejecutados ==");
    }

    @Test
    @DisplayName("Se puede crear la calculadora")
    void calculadoraNoEsNull() {
        // assertNotNull: comprobamos que @BeforeEach ha creado el objeto
        assertNotNull(calculator);
    }

    // ---------- SUMA ----------

    @Nested
    @DisplayName("Suma")
    @Tag("basicas")
    class Suma {

        // @ParameterizedTest + @CsvSource: el mismo test con muchos datos.
        // Cada linea es: num1, num2, resultado esperado
        @ParameterizedTest(name = "{0} + {1} = {2}")
        @CsvSource({
                "2, 3, 5",
                "-4, -6, -10",   // dos negativos
                "-5, 5, 0",      // negativo + positivo
                "0, 0, 0",       // el cero
                "7, 0, 7"        // elemento neutro
        })
        @DisplayName("Suma de enteros")
        void sumaEnteros(int a, int b, int esperado) {
            assertEquals(esperado, calculator.sum(a, b));
        }

        @ParameterizedTest(name = "{0} + {1} = {2}")
        @CsvSource({
                "0.1, 0.2, 0.3",     // el clasico problema de los decimales
                "2.5, -1.25, 1.25",
                "-0.5, -0.5, -1.0"
        })
        @DisplayName("Suma de decimales (con margen de error)")
        void sumaDecimales(double a, double b, double esperado) {
            // con double hay que usar un delta, porque 0.1 + 0.2 da 0.30000000000000004
            assertEquals(esperado, calculator.sum(a, b), 0.0001);
        }

        @Test
        @DisplayName("Desbordamiento: MAX_VALUE + 1 da la vuelta a MIN_VALUE")
        void desbordamiento() {
            // caso limite: un int no puede pasar de 2.147.483.647
            assertEquals(Integer.MIN_VALUE, calculator.sum(Integer.MAX_VALUE, 1));
        }

        @Test
        @DisplayName("La suma es conmutativa")
        void conmutativa() {
            // assertAll: comprueba todo aunque falle una y muestra todos los fallos juntos
            assertAll("a + b == b + a",
                    () -> assertEquals(calculator.sum(3, 8), calculator.sum(8, 3)),
                    () -> assertEquals(calculator.sum(-2, 9), calculator.sum(9, -2)),
                    () -> assertEquals(calculator.sum(1.5, 2.5), calculator.sum(2.5, 1.5), 0.0001)
            );
        }
    }

    // ---------- CUADRADO ----------

    @Nested
    @DisplayName("Cuadrado")
    @Tag("basicas")
    class Cuadrado {

        // @ValueSource: un solo dato por ejecucion
        @ParameterizedTest(name = "el cuadrado de {0} es positivo o cero")
        @ValueSource(ints = {-10, -3, 0, 4, 9})
        @DisplayName("El cuadrado nunca es negativo")
        void nuncaNegativo(int numero) {
            assertTrue(calculator.square(numero) >= 0);
        }

        @Test
        @DisplayName("Cuadrado de un negativo y de un decimal")
        void negativoYDecimal() {
            assertAll(
                    () -> assertEquals(25, calculator.square(-5)),
                    () -> assertEquals(6.25f, calculator.square(2.5f), 0.001f)
            );
        }
    }

    // ---------- EXPONENCIAL ----------

    @Nested
    @DisplayName("Exponencial")
    @Tag("avanzadas")
    class Exponencial {

        @ParameterizedTest(name = "{0} ^ {1} = {2}")
        @CsvSource({
                "2, 10, 1024",
                "3, 3, 27",
                "-2, 3, -8",    // base negativa con exponente impar
                "-2, 2, 4",     // base negativa con exponente par
                "5, 0, 1",      // cualquier numero elevado a 0 es 1
                "1.5, 2, 2.25"  // base decimal
        })
        @DisplayName("Potencias")
        void potencias(float base, int exponente, float esperado) {
            assertEquals(esperado, calculator.exponential(base, exponente), 0.001f);
        }

        // @Timeout: si el bucle tarda mas de 1 segundo el test falla.
        // Sirve para detectar bucles demasiado lentos o infinitos.
        @Test
        @Timeout(value = 1, unit = TimeUnit.SECONDS)
        @DisplayName("Un exponente grande termina rapido")
        void exponenteGrande() {
            assertEquals(1.0f, calculator.exponential(1.0f, 10_000_000), 0.001f);
        }

        // @Disabled: el metodo no soporta exponentes negativos (el bucle no entra
        // y devuelve 1). Lo dejamos escrito y desactivado hasta que se corrija.
        @Test
        @Disabled("Pendiente: exponential no soporta exponentes negativos, devuelve 1")
        @DisplayName("2 ^ -1 deberia ser 0.5")
        void exponenteNegativo() {
            assertEquals(0.5f, calculator.exponential(2, -1), 0.001f);
        }
    }

    // ---------- DIVISION ----------

    @Nested
    @DisplayName("Division")
    @Tag("basicas")
    class Division {

        @ParameterizedTest(name = "{0} / {1} = {2}")
        @CsvSource({
                "10, 2, 5",
                "7, 2, 3",      // division entera: se pierden los decimales
                "-9, 3, -3",
                "0, 5, 0"
        })
        @DisplayName("Division de enteros")
        void divisionEnteros(int a, int b, int esperado) {
            assertEquals(esperado, calculator.divide(a, b));
        }

        @Test
        @DisplayName("Division de decimales")
        void divisionDecimales() {
            assertEquals(3.5, calculator.divide(7.0, 2.0), 0.0001);
        }

        // assertThrows: comprobamos que dividir entre cero lanza la excepcion
        @Test
        @DisplayName("Dividir un entero entre cero lanza ArithmeticException")
        void divisionEntreCeroEnteros() {
            ArithmeticException e = assertThrows(ArithmeticException.class,
                    () -> calculator.divide(8, 0));
            assertEquals("No se puede dividir entre cero", e.getMessage());
        }

        @ParameterizedTest(name = "{0} / 0 lanza excepcion")
        @ValueSource(doubles = {1.0, -3.5, 0.0})
        @DisplayName("Dividir un decimal entre cero lanza ArithmeticException")
        void divisionEntreCeroDecimales(double numero) {
            assertThrows(ArithmeticException.class, () -> calculator.divide(numero, 0.0));
        }

        @Test
        @DisplayName("Dividir entre un numero distinto de cero no lanza nada")
        void divisionSinExcepcion() {
            assertDoesNotThrow(() -> calculator.divide(9, 3));
            assertFalse(calculator.divide(9, 3) == 0);
        }
    }
}
