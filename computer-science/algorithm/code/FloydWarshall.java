public class FloydWarshall {
	static final int INF = int(10e9);

	public static void floyd(int[][] graph, int n) {
		// 경유지
		for (int k = 1; k <= n; k++) {
			// 출발지
			for (int i = 1; i <= n; i++) {
				//도착지
				for (int j = 1; j <= n; j++) {
					graph[i][j] = Math.min(graph[i][j], graph[i][k]+graph[k][j]);
				}
			}
		}

		//출력
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if(graph[i][j] == INF) System.out.print(0+" ");
				else System.out.print(graph[i][j]+" ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) throws IOException {
		//그래프 입력 받기
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		// 정점의 개수, 간선의 개수
		int n = Integer.parseInt(bf.readLine());
		int m = Integer.parseInt(bf.readLine());

		int[][] graph = new int[n + 1][n + 1];

		for (int i = 0; i < graph.length; i++) {
			for (int j = 0; j < graph.length; j++) {
				if(i == j) continue;
				graph[i][j] = INF;
			}
		}

		StringTokenizer st;
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(bf.readLine());
			int v = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());

			graph[v][w] = cost;
		}

		//플로이드 알고리즘 수행
		floyd(graph, n);
	}
}
