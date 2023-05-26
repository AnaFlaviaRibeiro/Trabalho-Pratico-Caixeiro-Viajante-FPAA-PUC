package forcabruta;

import java.util.Random;

public class CaixeiroViajanteGuloso {
    private int[][] grafo; // Matriz de custos de viagem
    private int numVertices; // Número de cidades/vertices

    public CaixeiroViajanteGuloso(int[][] grafo) {
        this.grafo = grafo;
        this.numVertices = grafo.length;
    }

    public int[] encontrarCaminhoMinimo() {
        int[] caminhoMinimo = new int[numVertices + 1]; // Caminho mínimo com volta para a cidade de partida
        boolean[] visitado = new boolean[numVertices];
        
        int cidadeAtual = 0; // Começa na cidade 0
        caminhoMinimo[0] = cidadeAtual;
        visitado[cidadeAtual] = true;
        
        for (int i = 1; i <= numVertices; i++) {
            int proximaCidade = encontrarProximaCidade(cidadeAtual, visitado);
            caminhoMinimo[i] = proximaCidade;
            visitado[proximaCidade] = true;
            cidadeAtual = proximaCidade;
        }
        
        return caminhoMinimo;
    }
    
    private int encontrarProximaCidade(int cidadeAtual, boolean[] visitado) {
        int proximaCidade = -1;
        int menorCusto = Integer.MAX_VALUE;
        
        for (int i = 0; i < numVertices; i++) {
            if (!visitado[i] && grafo[cidadeAtual][i] < menorCusto) {
                menorCusto = grafo[cidadeAtual][i];
                proximaCidade = i;
            }
        }
        
        if (proximaCidade == -1) {
            // Nenhuma cidade não visitada foi encontrada, selecionar aleatoriamente uma cidade não visitada
            Random random = new Random();
            while (proximaCidade == -1) {
                int cidadeAleatoria = random.nextInt(numVertices);
                if (!visitado[cidadeAleatoria]) {
                    proximaCidade = cidadeAleatoria;
                }
            }
        }
        
        return proximaCidade;
    }

}
