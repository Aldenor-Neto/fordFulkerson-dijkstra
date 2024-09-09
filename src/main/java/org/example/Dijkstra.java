package org.example;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

class Dijkstra {
    // Classe auxiliar para representar arestas no grafo
    static class Edge {
        int destination, weight;

        Edge(int destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }

    // Classe para representar o grafo
    static class Graph {
        int vertices;
        LinkedList<Edge>[] adjacencyList;

        Graph(int vertices) {
            this.vertices = vertices;
            adjacencyList = new LinkedList[vertices];
            for (int i = 0; i < vertices; i++) {
                adjacencyList[i] = new LinkedList<>();
            }
        }

        // Método para adicionar arestas ao grafo
        void addEdge(int source, int destination, int weight) {
            adjacencyList[source].add(new Edge(destination, weight));
            adjacencyList[destination].add(new Edge(source, weight)); // Para grafo não direcionado
        }

        // Implementação do algoritmo de Dijkstra
        void dijkstra(int source) {
            // Distância de cada nó a partir da origem
            int[] distances = new int[vertices];
            Arrays.fill(distances, Integer.MAX_VALUE); // Inicializa todas as distâncias como infinito
            distances[source] = 0;

            // Fila de prioridade para armazenar os nós, priorizando aqueles com menor distância
            PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(vertices, Comparator.comparingInt(edge -> edge.weight));

            // Adiciona o nó de origem na fila de prioridade
            priorityQueue.add(new Edge(source, 0));

            while (!priorityQueue.isEmpty()) {
                // Extrai o nó com a menor distância
                int currentVertex = priorityQueue.poll().destination;

                // Itera sobre todos os vizinhos do nó atual
                for (Edge edge : adjacencyList[currentVertex]) {
                    int neighbor = edge.destination;
                    int newDist = distances[currentVertex] + edge.weight;

                    // Se uma distância menor for encontrada, atualiza
                    if (newDist < distances[neighbor]) {
                        distances[neighbor] = newDist;
                        priorityQueue.add(new Edge(neighbor, newDist));
                    }
                }
            }

            // Imprime as distâncias mínimas
            printSolution(distances);
        }

        // Função auxiliar para imprimir o resultado
        void printSolution(int[] distances) {
            System.out.println("Distâncias mínimas do nó de origem:");
            for (int i = 0; i < vertices; i++) {
                System.out.println("Nó " + i + " - Distância: " + distances[i]);
            }
        }
    }

    public static void main(String[] args) {

        int vertices = 4;
        Graph graph = new Graph(vertices);

        graph.addEdge(0, 1, 5);
        graph.addEdge(0, 2, 3);
        graph.addEdge(1, 2, 2);
        graph.addEdge(1, 3, 6);
        graph.addEdge(2, 3, 7);

        int source = 0;
        graph.dijkstra(source);

        int vertices2 = 6;
        System.out.println();
        Graph graph2 = new Graph(vertices2);

        graph2.addEdge(0, 1, 4);
        graph2.addEdge(0, 2, 2);
        graph2.addEdge(1, 2, 1);
        graph2.addEdge(1, 3, 5);
        graph2.addEdge(2, 3, 8);
        graph2.addEdge(2, 4, 10);
        graph2.addEdge(3, 4, 2);
        graph2.addEdge(3, 5, 6);
        graph2.addEdge(4, 5, 3);

        int source2 = 0;
        graph2.dijkstra(source2);
    }
}
