package lab10_1;

import java.util.Scanner;

public class hw10_1_TopologicalSort {
	public static void main(String[] args) {
		System.out.println("hw10_1: 최성호");
		System.out.println("인접 리스트 - 직접 구현함");
		Scanner input = new Scanner(System.in);
		
		// (1) 정점 수를 입력받아 방향 그래프를 생성
		int numVertices = input.nextInt();
		MyGraph graph = new MyGraph(numVertices);

		// (2) 사용자가 원하는 갯수의 간선을 입력받아 방향 그래프에 삽입
		int numEdges = input.nextInt();
		for(int i = 0; i < numEdges; i++) {
			int v1 = input.nextInt();
			int v2 = input.nextInt();
			graph.insertEdge(v1, v2);
		}
		
		// (3) DAG여부 판정 후 DAG면 위상정렬 구현 및 결과출력 DAG가 아니면 그대로 종료
		graph.DAG();
		
		input.close();
	}
}
// 인접 리스트로 방향 그래프를 구현하는 클래스
class MyGraph {
	private int n;	// 정점 수
	private Node[] list;	// 인접 리스트
	private int[] Inline;	// 각 정점의 진입 간선수가 저장된 배열
	private int[] Topological;	//위상 정렬 순서대로 정점 번호가 저장된 배열
	
	//연결리스트로 구현하기 위한 노드 클래스 
	private class Node {
		int vertex;
		Node link;
	}
	// 정점 수가 n이고, 간선은 없는 그래프를 생성
	public MyGraph(int n) {
		this.n = n;
		list = new Node[n];		//인접 리스트의 크기를 전달받은 n값으로 설정
		Inline = new int[n];	//진입 간선수배열의 크기를 전달받은 n값으로 설정
		Topological = new int[n];	//위상 정렬 순서대로 저장된 배열의 크기를 전달받은 n값으로 설정
	}
	// 그래프에 간선 <v1, v2>를 삽입
	public void insertEdge(int v1, int v2) {
		if(v1<0 || v1>=n || v2<0 || v2>=n)  
			System.out.println("그래프에 없는 정점입니다!");
		else {
			Node newNode = new Node();		//새로운 노드 선언
			newNode.vertex = v2;			//전달받은 간선을 저장(뒷 간선)
			newNode.link = list[v1];		//노드에 해당 정점의 링크를 저장(처음이라면 null값 저장)
			list[v1] = newNode;				//해당 정점이 노드를 가리키게 함
		}
	}
	// 그래프 구현을 확인하기 위해 인접 정점 정보를 출력
	private void printAdjVertices() {
		for(int i = 0; i < list.length; i++) {
			System.out.print(Topological[i] + " ");	//위상 정렬된 값이 저장된 배열을 출력
		}
	}
	//그래프의 DAG여부를 판별
	public void DAG() {
		int hasInline = 0;		//각 정점의 진입간선 여부를 확인하는 변수
		for(int i = 0; i < Inline.length; i++) {
			Node temp = list[i];	//정점을 돌며 진입 간선을 기록할 노드형 변수 선언
			while(temp != null) {	//각 정점에 진출 차수가 있는 경우
				Inline[temp.vertex] += 1;	//진출하는 정점의 인덱스 값을 1증가시켜 해당 정점의 진입 간선수를 기록	
				temp = temp.link;
			}
		}
		for(int i = 0; i < Inline.length; i++) {	//각 정점의 진입 간선 여부를 확인
			if(Inline[i] != 0)			//진입 간선이 있는 경우 hasInline의 값을 1증가
				hasInline += 1;
		}
		if(hasInline == Inline.length)	//만약 모든 원소에 진입간선이 있는경우 DAG가 아님
			System.out.println("DAG가 아니므로 위상 정렬 불가능합니다.");
		else {
			System.out.println("DAG입니다.");	//그렇지 않은 경우 DAG이므로 위상정렬 및 결과 출력 실행
			Topological_sort();			//위상 정렬 메소드
		}	
	}
	//DAG로 판정된 그래프의 위상 정렬을 시행
	private void Topological_sort() {
		for(int i = 0; i < list.length; i++) {	//정점 개수만큼 반복
			for(int j = 0; j < Inline.length; j++) {	
				if(Inline[j] == 0) {	//진입 간선수가 없는 정점을 선택
					Topological[i] = j;	//위상 정렬 결과값을 저장하는 배열에 순서대로 인덱스에 저장
					Node temp = list[j];
					while(temp != null) {	//해당 정점의 모돈 진출 간선을 없앰
						Inline[temp.vertex]--;	//진출 간선을 없애는 건 진출한 정점의 진입간선이 감소하는 것
						temp = temp.link;		//그러므로 해당 정점의 진입 간선수를 1 감소시킴
					}
					Inline[j] = -1;	//해당 정점의 진출차수를 모두 없앴으니 다시 선택되지 않게 해당 인덱스 값을 -1로 지정
					break;			//중복되어 한번에 2개이상 정점의 진출차수를 없애면 안되므로 반복문을 탈출
				}
			}
		}
		printAdjVertices();	//위상 정렬된 결과 값을 출력
	}
}