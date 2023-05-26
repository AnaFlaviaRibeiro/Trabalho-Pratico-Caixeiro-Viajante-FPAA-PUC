package forcabruta;

import java.util.Arrays;

public class TesteAutomatizado {
    public static void main(String[] args) {
        int iteracoesMaximas = 70;
        int limiteTempo = 4 * 60 * 1000; // 4 minutos em milissegundos
        int numVertices = 5;
        
        while (true) {
            long tempoTotalForcaBruta = 0;
            long tempoTotalGuloso = 0;
            int numGrafosTestados = 0;
            
            for (int i = 0; i < iteracoesMaximas; i++) {
                int[][] grafo = GeradorDeGrafos.grafoCompletoPonderado(numVertices);
                
                CaixeiroViajanteForcaBruta caixeiroForcaBruta = new CaixeiroViajanteForcaBruta(grafo);
                long tempoInicioForcaBruta = System.currentTimeMillis();
                caixeiroForcaBruta.encontrarCaminhoMinimo();
                long tempoFimForcaBruta = System.currentTimeMillis();
                long tempoForcaBruta = tempoFimForcaBruta - tempoInicioForcaBruta;
                tempoTotalForcaBruta += tempoForcaBruta;
                
                CaixeiroViajanteGuloso caixeiroGuloso = new CaixeiroViajanteGuloso(grafo);
                long tempoInicioGuloso = System.currentTimeMillis();
                caixeiroGuloso.encontrarCaminhoMinimo();
                long tempoFimGuloso = System.currentTimeMillis();
                long tempoGuloso = tempoFimGuloso - tempoInicioGuloso;
                tempoTotalGuloso += tempoGuloso;
                
                numGrafosTestados++;
                
                System.out.println("Grafo " + numGrafosTestados);
                System.out.println("Tempo Força Bruta: " + tempoForcaBruta + "ms");
                System.out.println("Tempo Guloso: " + tempoGuloso + "ms");
                System.out.println("----------------------");
                
                if (tempoForcaBruta > limiteTempo) {
                    break; // Interrompe o teste se exceder o limite de tempo
                }
            }
            
            long tempoMedioForcaBruta = tempoTotalForcaBruta / numGrafosTestados;
            long tempoMedioGuloso = tempoTotalGuloso / numGrafosTestados;
            
            System.out.println("Número de vértices: " + (numVertices - 1));
            System.out.println("Tempo médio Força Bruta: " + tempoMedioForcaBruta + "ms");
            System.out.println("Tempo médio Guloso: " + tempoMedioGuloso + "ms");
            System.out.println("=======================");
            
            if (tempoMedioForcaBruta < 3500) {
                break; // Interrompe o teste quando o tempo médio for menor que 3.5 segundos
            }
            
            numVertices++;
        }
        
        // Etapa b: Gerar 1000 grafos aleatórios com N-1 vértices
        int numGrafosAleatorios = 1000;
        int[][][] grafosAleatorios = new int[numGrafosAleatorios][][];
        
        for (int i = 0; i < numGrafosAleatorios; i++) {
            int[][] grafo = GeradorDeGrafos.grafoCompletoPonderado(numVertices - 1);
            grafosAleatorios[i] = grafo;
        }
        
        // Etapa c: Executar solução Força Bruta e solução Gulosa para cada grafo aleatório
        long[] temposForcaBruta = new long[numGrafosAleatorios];
        long[] temposGuloso = new long[numGrafosAleatorios];
        int numSolucoesIguais = 0;
        
        for (int i = 0; i < numGrafosAleatorios; i++) {
            int[][] grafo = grafosAleatorios[i];
            
            CaixeiroViajanteForcaBruta caixeiroForcaBruta = new CaixeiroViajanteForcaBruta(grafo);
            long tempoInicioForcaBruta = System.currentTimeMillis();
            int[] caminhoMinimoForcaBruta = caixeiroForcaBruta.encontrarCaminhoMinimo();
            long tempoFimForcaBruta = System.currentTimeMillis();
            temposForcaBruta[i] = tempoFimForcaBruta - tempoInicioForcaBruta;
            
            CaixeiroViajanteGuloso caixeiroGuloso = new CaixeiroViajanteGuloso(grafo);
            long tempoInicioGuloso = System.currentTimeMillis();
            int[] caminhoMinimoGuloso = caixeiroGuloso.encontrarCaminhoMinimo();
            long tempoFimGuloso = System.currentTimeMillis();
            temposGuloso[i] = tempoFimGuloso - tempoInicioGuloso;
            
            if (Arrays.equals(caminhoMinimoForcaBruta, caminhoMinimoGuloso)) {
                numSolucoesIguais++;
            }
        }
        
        // Imprimir resultados
        System.out.println("Relatório de Teste Automatizado");
        System.out.println("-------------------------------");
        System.out.println("Número de vértices: " + (numVertices - 1));
        System.out.println("Tempo médio Força Bruta: " + Arrays.stream(temposForcaBruta).average().orElse(0) + "ms");
        System.out.println("Tempo médio Guloso: " + Arrays.stream(temposGuloso).average().orElse(0) + "ms");
        System.out.println("Número de soluções iguais (Força Bruta vs. Guloso): " + numSolucoesIguais);
    }
}
