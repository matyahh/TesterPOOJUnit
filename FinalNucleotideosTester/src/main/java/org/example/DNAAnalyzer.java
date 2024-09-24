/*                                                                                                      23/09/24
@Author: Mateus Henrique de Jesus Oliveira && Rithiellen Kariny Alves Maxima
            "Classe que tem o método que recebe uma String
                                 do Arquivo txt"
 */


package org.example;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class DNAAnalyzer {
    
    public int[] calculaNucleotideos(String filePath) throws IOException {
        String sequence;
        try {
            sequence = new String(Files.readAllBytes(Paths.get(filePath))).trim().toUpperCase();
        } catch (IOException e) {
            throw new IOException("Arquivo não encontrado: " + filePath);
        }
        
        int countA = 0, countC = 0, countG = 0, countT = 0, errors = 0;
        int length = sequence.length();
        
        for (char nucleotide : sequence.toCharArray()) {
            switch (nucleotide) {
                case 'A':
                    countA++;
                    break;
                case 'C':
                    countC++;
                    break;
                case 'G':
                    countG++;
                    break;
                case 'T':
                    countT++;
                    break;
                default:
                    errors++;
                    break;
            }
        }
        
        // Verifica se mais de 10% dos caracteres são inválidos
        if ((double) errors / length > 0.1) {
            return null;
        }
        
        return new int[] { countA, countC, countG, countT, errors };
    }
}
