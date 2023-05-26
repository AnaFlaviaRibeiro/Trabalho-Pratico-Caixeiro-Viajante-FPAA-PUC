package forcabruta;

public class CaixeiroViajanteForcaBruta {

    private int[][] grafo; // Matriz de custos de viagem
    private int numVertices; // Número de cidades/vertices

    public CaixeiroViajanteForcaBruta(int[][] grafo) {
        this.grafo = grafo;
        this.numVertices = grafo.length;
    }

    public int[] encontrarCaminhoMinimo() {
        int[] caminhoAtual = new int[numVertices];
        int[] melhorCaminho = new int[numVertices];
        boolean[] visitado = new boolean[numVertices];
        int custoAtual = 0;
        int melhorCusto = Integer.MAX_VALUE;

        caminhoAtual[0] = 0; // Começar a partir da cidade 0
        visitado[0] = true;

        encontrarCaminhoMinimoRecursivo(1, caminhoAtual, visitado, custoAtual, melhorCaminho, melhorCusto);

        return melhorCaminho;
    }

    private void encontrarCaminhoMinimoRecursivo(int nivel, int[] caminhoAtual, boolean[] visitado, int custoAtual, int[] melhorCaminho, int melhorCusto) {
        if (nivel == numVertices) { // Todos os vértices foram visitados
            int custoTotal = custoAtual + grafo[caminhoAtual[nivel - 1]][caminhoAtual[0]]; // Custo total incluindo o retorno à cidade de partida

            if (custoTotal < melhorCusto) {
                // Encontrou um caminho de menor custo, atualiza o melhor caminho
                melhorCusto = custoTotal;
                System.arraycopy(caminhoAtual, 0, melhorCaminho, 0, numVertices);
            }
            return;
        }

        for (int i = 1; i < numVertices; i++) {
            if (!visitado[i]) {
                caminhoAtual[nivel] = i;
                visitado[i] = true;
                int custoAresta = grafo[caminhoAtual[nivel - 1]][i];
                custoAtual += custoAresta;

                if (custoAtual < melhorCusto) {
                    encontrarCaminhoMinimoRecursivo(nivel + 1, caminhoAtual, visitado, custoAtual, melhorCaminho, melhorCusto);
                }

                // Backtracking
                custoAtual -= custoAresta;
                visitado[i] = false;
            }
        }
    }

    public static void main(String[] args) {
        int[][] grafo = GeradorDeGrafos.grafoCompletoPonderado(4); // Exemplo com 4 cidades/vertices
        CaixeiroViajanteForcaBruta caixeiro = new CaixeiroViajanteForcaBruta(grafo);
        int[] caminhoMinimo = caixeiro.encontrarCaminhoMinimo();

        System.out.println("Caminho de menor custo:");
        for (int cidade : caminhoMinimo) {
            System.out.print(cidade + " ");
        }
        System.out.println();
    }
}
