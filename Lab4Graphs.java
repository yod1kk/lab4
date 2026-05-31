import java.util.*;

public class Lab4Graphs {

    static int[][] graphLab3 = {
            {1, 2}, {0, 3, 4}, {0, 5, 6}, {1}, {1}, {2}, {2}
    };

    static int[][] graphCycle = {
            {1, 3},
            {0, 2},
            {1, 3, 4},
            {2, 0},
            {2}
    };

    static int[][] graphCity = {
            {1, 3},
            {0, 2, 4},
            {1, 5},
            {0, 4},
            {1, 3, 6},
            {2, 7},
            {4, 7},
            {5, 6}
    };

    public static void main(String[] args) {

        System.out.println(" Завдання 1: Виявлення циклів ");
        System.out.println("Граф з циклом: " + (checkCycle(graphCycle) ? "цикл є" : "циклів немає"));
        System.out.println("Граф з лаби 3: " + (checkCycle(graphLab3) ? "цикл є" : "циклів немає"));

        System.out.println("\n Завдання 2: Карта міста ");
        bfsCityAnalysis(0, 7);
        System.out.print("Шлях DFS від 0 до 7: ");
        dfsFindPath(0, 7, new boolean[graphCity.length], new ArrayList<>());
        System.out.println();

        System.out.println("\n Завдання 3: Двочастковий граф ");
        System.out.println("Карта міста: " + (isBipartite(graphCity) ? "двочастковий" : "не двочастковий"));
        System.out.println("Граф з лаби 3: " + (isBipartite(graphLab3) ? "двочастковий" : "не двочастковий"));
    }

    public static boolean checkCycle(int[][] graph) {
        boolean[] visited = new boolean[graph.length];
        for (int i = 0; i < graph.length; i++) {
            if (!visited[i]) {
                if (hasCycle_DFS(graph, i, visited, -1)) return true;
            }
        }
        return false;
    }

    private static boolean hasCycle_DFS(int[][] graph, int v, boolean[] visited, int parent) {
        visited[v] = true;
        for (int u : graph[v]) {
            if (!visited[u]) {
                if (hasCycle_DFS(graph, u, visited, v)) return true;
            } else if (u != parent) {
                return true; 
            }
        }
        return false;
    }

    public static void bfsCityAnalysis(int start, int target) {
        boolean[] visited = new boolean[graphCity.length];
        int[] distance = new int[graphCity.length];
        int[] parent = new int[graphCity.length];
        Arrays.fill(parent, -1);

        Queue<Integer> queue = new LinkedList<>();
        visited[start] = true;
        queue.add(start);

        while (!queue.isEmpty()) {
            int v = queue.poll();
            for (int u : graphCity[v]) {
                if (!visited[u]) {
                    visited[u] = true;
                    distance[u] = distance[v] + 1;
                    parent[u] = v;
                    queue.add(u);
                }
            }
        }

        String path = "";
        int current = target;
        while (current != -1) {
            path = current + (path.isEmpty() ? "" : " -> " + path);
            current = parent[current];
        }

        System.out.println("Найкоротший шлях: " + path);
        System.out.println("Кількість доріг: " + distance[target]);
        System.out.println("Відстані від району " + start + ":");
        for (int i = 0; i < distance.length; i++) {
            System.out.println("  до району " + i + ": " + distance[i]);
        }
    }

    public static boolean dfsFindPath(int v, int target, boolean[] visited, List<Integer> path) {
        visited[v] = true;
        path.add(v);

        if (v == target) {
            for (int i = 0; i < path.size(); i++) {
                System.out.print(path.get(i) + (i == path.size() - 1 ? "" : " -> "));
            }
            return true;
        }

        for (int u : graphCity[v]) {
            if (!visited[u]) {
                if (dfsFindPath(u, target, visited, path)) return true;
            }
        }

        path.remove(path.size() - 1);
        return false;
    }

    public static boolean isBipartite(int[][] graph) {
        int[] colors = new int[graph.length];
        for (int i = 0; i < graph.length; i++) {
            if (colors[i] == 0) {
                Queue<Integer> q = new LinkedList<>();
                q.add(i);
                colors[i] = 1;
                while (!q.isEmpty()) {
                    int v = q.poll();
                    for (int u : graph[v]) {
                        if (colors[u] == 0) {
                            colors[u] = -colors[v];
                            q.add(u);
                        } else if (colors[u] == colors[v]) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}