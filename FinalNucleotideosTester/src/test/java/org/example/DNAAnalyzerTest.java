package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class DNAAnalyzerTest {

    private static final String VALID_SEQ_FILE = "valid_sequence.txt";
    private static final String INVALID_SEQ_FILE = "invalid_sequence.txt";
    private static final String HIGH_ERRORS_SEQ_FILE = "high_errors_sequence.txt";

    @BeforeAll
    static void setup() throws IOException {
        // Cria arquivos de teste
        Files.write(Paths.get(VALID_SEQ_FILE), "AAAGTCTGAC".getBytes());
        Files.write(Paths.get(INVALID_SEQ_FILE), "AACTGTCGBA".getBytes());
        Files.write(Paths.get(HIGH_ERRORS_SEQ_FILE), "ABC TEM FALHA".getBytes());
    }

    @AfterAll
    static void tearDown() throws IOException {
        // Exclui arquivos de teste
        Files.deleteIfExists(Paths.get(VALID_SEQ_FILE));
        Files.deleteIfExists(Paths.get(INVALID_SEQ_FILE));
        Files.deleteIfExists(Paths.get(HIGH_ERRORS_SEQ_FILE));
    }

    @Test
    @DisplayName("Verifica sequência válida - AAAGTCTGAC")
    void testValidSequence() throws IOException {
        DNAAnalyzer analyzer = new DNAAnalyzer();
        int[] result = analyzer.calculaNucleotideos(VALID_SEQ_FILE);
        assertArrayEquals(new int[]{4, 2, 2, 2, 0}, result);
    }

    @Test
    @DisplayName("Verifica sequência com 1 caractere inválido - AACTGTCGBA")
    void testInvalidCharacter() throws IOException {
        DNAAnalyzer analyzer = new DNAAnalyzer();
        int[] result = analyzer.calculaNucleotideos(INVALID_SEQ_FILE);
        assertArrayEquals(new int[]{3, 2, 2, 2, 1}, result);
    }

    @Test
    @DisplayName("Verifica sequência com mais de 10% de caracteres inválidos - ABC TEM FALHA")
    void testHighErrorRate() throws IOException {
        DNAAnalyzer analyzer = new DNAAnalyzer();
        assertNull(analyzer.calculaNucleotideos(HIGH_ERRORS_SEQ_FILE));
    }

    @Test
    @DisplayName("Verifica exceção quando o arquivo não é encontrado")
    void testFileNotFound() {
        DNAAnalyzer analyzer = new DNAAnalyzer();
        Exception exception = assertThrows(IOException.class, () -> {
            analyzer.calculaNucleotideos("non_existent_file.txt");
        });
        assertEquals("Arquivo não encontrado: non_existent_file.txt", exception.getMessage());
    }
}
