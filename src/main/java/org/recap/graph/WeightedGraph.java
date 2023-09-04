package org.recap.graph;

import java.util.*;

public class WeightedGraph {
    private final Map<String, List<Edge>> adjacencyList;  //인접리스트를 이용한 구현방식

    public WeightedGraph() {
        adjacencyList = new LinkedHashMap<>();  //순서 보존을 위해서
    }

    public void addNode(String node) {  //노드 추가
        if (!adjacencyList.containsKey(node)) {
            adjacencyList.put(node, new ArrayList<>());
        }
    }

    public void addEdge(String sourceNode, String targetNode, double weight) {  //엣지 추가
        // 노드가 없으면 추가
        addNode(sourceNode);
        addNode(targetNode);

        // 엣지 추가
        adjacencyList.get(sourceNode).add(new Edge(targetNode, weight));
    }

    public List<String> getNodes() {  //노드 리턴
        return new ArrayList<>(adjacencyList.keySet());
    }

    public List<Edge> getEdges(String node) {  //엣지 리턴
        return adjacencyList.get(node);
    }
}
