package org.example;

import java.util.LinkedList;
import java.util.Queue;

class FordFulkerson {
    private int vertices;
    private int[][] capacityMatrix; // Matriz de capacidades

    public FordFulkerson(int vertices) {
        this.vertices = vertices;
        capacityMatrix = new int[vertices][vertices];
    }

    // Método para adicionar aresta ao grafo
    public void addEdge(int source, int destination, int capacity) {
        capacityMatrix[source][destination] = capacity;
    }

    // Implementação do BFS para encontrar o caminho aumentante
    private boolean bfs(int source, int sink, int[] parent) {
        boolean[] visited = new boolean[vertices];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;
        parent[source] = -1;

        while (!queue.isEmpty()) {
            int u = queue.poll();

            for (int v = 0; v < vertices; v++) {
                if (!visited[v] && capacityMatrix[u][v] > 0) { // Se não foi visitado e há capacidade residual
                    if (v == sink) {
                        parent[v] = u;
                        return true; // Encontrou caminho aumentante
                    }
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }
        return false; // Não encontrou caminho aumentante
    }

    // Método para encontrar o fluxo máximo usando Ford-Fulkerson
    public int fordFulkerson(int source, int sink) {
        int maxFlow = 0;
        int[] parent = new int[vertices];

        // Atualizar o fluxo enquanto existe um caminho aumentante
        while (bfs(source, sink, parent)) {
            // Encontrar a capacidade residual mínima ao longo do caminho aumentante
            int pathFlow = Integer.MAX_VALUE;
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, capacityMatrix[u][v]);
            }

            // Atualizar capacidades residuais das arestas e arestas reversas
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                capacityMatrix[u][v] -= pathFlow;
                capacityMatrix[v][u] += pathFlow;
            }

            // Adicionar o fluxo do caminho aumentante ao fluxo total
            maxFlow += pathFlow;
        }
        return maxFlow;
    }

    public static void main(String[] args) {

        FordFulkerson graph = new FordFulkerson(4);

        graph.addEdge(0, 1, 20);
        graph.addEdge(0, 2, 10);
        graph.addEdge(1, 2, 5);
        graph.addEdge(1, 3, 10);
        graph.addEdge(2, 3, 15);

        int source = 0;
        int sink = 3;

        System.out.println("O fluxo máximo é: " + graph.fordFulkerson(source, sink));
        System.out.println();

        FordFulkerson graph2 = new FordFulkerson(6);

        graph2.addEdge(0, 1, 16);
        graph2.addEdge(0, 2, 13);
        graph2.addEdge(1, 2, 10);
        graph2.addEdge(1, 3, 12);
        graph2.addEdge(2, 1, 4);
        graph2.addEdge(2, 4, 14);
        graph2.addEdge(3, 2, 9);
        graph2.addEdge(3, 5, 20);
        graph2.addEdge(4, 3, 7);
        graph2.addEdge(4, 5, 4);

        int source2 = 0;
        int sink2 = 5;

        System.out.println("O fluxo máximo é: " + graph2.fordFulkerson(source2, sink2));
    }
}
