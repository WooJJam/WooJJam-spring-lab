# 최소 신장 트리(MST, Minimum Spanning Tree)


## Spanning Tree

![spanning-tree.png](https://github.com/WooJJam/WooJJam-spring-lab/blob/develop/computer-science/resources/spanning-tree.png)

그래프 내의 모든 정점을 포함하지만 사이클이 없는 트리로, 신장 트리라고도 한다.

- Spanning Tree는 그래프의 최소 연결 부분 그래프
    - 최소 연결 : 간선의 수가 가장 적음
    - n개의 정점을 가지는 그래프의 최소 간선의 수는 (n-1)개
- 즉, 그래프의 일부 간선을 선택해서 만든 트리

### 특징

- DFS, BFS를 이용하여 그래프의 신장 트리를 찾을 수 있음
- 하나의 그래프에는 여러개의 신장 트리가 존재할 수 있음
- 모든 정점들이 연결이 되어있어야 하며, 사이클을 포함해서는 안됨

### 예제

![spanning-tree-example.png](https://github.com/WooJJam/WooJJam-spring-lab/blob/develop/computer-science/resources/spanning-tree-example.png?raw=true)

대표적으로 통신 네트워크 구축을 예시로 들 수 있는데, 회사 내의 모든 전화기를 가장 적은 수의 케이블로 연결하고자 하는 경우가 그 예시이다.

## MST (Minimum Spanning Tree)

스패닝 트리 중 사용된 간선들의 **가중치 합이 최소**인 트리

- 최소 비용 신장 트리
- 간선의 수를 단순히 가장 적게 사용하는 것이 최소 비용을 보장하지는 않음
- MST는 간선의 가중치들을 고려하여 **최소 비용**의 **Spanning Tree**를 선택하는 것

### 특징

1. 간선의 가중치 합이 최소
2. n개의 정점을 가지는 그래프에 대해 반드시 (n-1)개의 간선만 사용
3. 신장 트리이므로 사이클이 포함되면 안됨

### 예시

![mst-example.png](https://github.com/WooJJam/WooJJam-spring-lab/blob/develop/computer-science/resources/mst-example.png?raw=true)

회사의 모든 전화기를 연결할 때 전화선의 길이를 최소로 구축하는 경우가 그 예시이다.

1. 도로 건설
    - 도시들을 모두 연결하면서 도로의 길이가 최소
2. 전기 회로
    - 단자를 모두 연결하면서 전서의 길이가 최소
3. 통신
    - 전화기를 모두 연결하면서 전화선의 길이를 최소
4. 배관
    - 파이프를 모두 연결하면서 파이프의 길이가 최소

### 구현 방법

#### Kruskal MST 알고리즘

탐욕적인 방법(Greedy)을 이용하여 모든 정점을 최소 비용으로 연결하는 최적 해답을 구하는 것이다.

MST가 최소 비용의 간선으로 구성되며 사이클을 포함하지 않고, 이전 단계에서 만들어진 신장 트리와는 상관없이 최소 간선만을  선택하는 방법이다.

- 과정
    1. 그래프의 간선을 가중치의 오름차순으로 정렬
    2. 사이클을 형성하지 않는 간선을 선택
        - 가중치가 낮은 간선 먼저 선택
        - 사이클을 형성하는 간선 제외
    3. 해당 간선을 현재의 MST 집합에 추가

- 시간 복잡도
    - union-find를 이용하면 시간 복잡도는 간선들을 정렬하는 시간에 좌우
    - 즉, 간선 e개를 효율적으로 정렬한다면 O(eloge)의 시간복잡도를 가짐
