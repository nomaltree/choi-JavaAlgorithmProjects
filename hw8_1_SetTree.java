package lab8_1;

public class hw8_1_SetTree {
	public static void main(String[] args) {
		System.out.println("hw8_1 : 최성호");

		// 상호배타적 집합을 구현하는 MyDisjointSet 객체를 생성하고 테스트 연산을 수행
		MyDisjointSet set = new MyDisjointSet();
		for(int i = 0; i < 10; i++)			//각 원소에 대해 하나의 노드로 구성된 10개 집합 생성
			set.makeSet(i);	
		set.test();							//각 노드의 부모를 출력
		set.union(0, 1);					//아래와 같은 union연산을 수행	
		set.union(2, 0);
		set.union(3, 2);
		set.union(4, 3);
		set.union(5, 6);
		set.union(7, 8);
		set.union(5, 7);
		System.out.println();				
		set.test();							//각 노드의 부모를 출력
		System.out.println();
		System.out.print("대표 노드 = ");
		for(int i = 0; i < 10; i++)			//각 노드의 대표노드를 findSet을 이용해 알아내어 출력
			System.out.print(set.findSet(i) + " ");
		System.out.println();
		set.test();							//각 노드의 부모를 출력
	}
}

// 트리를 이용하여 상호배타적 집합을 구현하는 클래스
class MyDisjointSet {
	private int n = 10;  // 원소 개수(원소는 0, 1, 2, ..., n-1) 

	// private 인스턴스 변수 선언 - 트리 구현을 위한 자료구조
	private int[] parent;

	public MyDisjointSet() {  // 트리 구현을 위해 필요한 자료구조를 초기화
		parent = new int[n];	//크기 n만큼의 배열 생성 여기서는 10으로 설정
	}

	// 하나의 원소 x로 구성된 집합 생성
	public void makeSet(int x) {
		parent[x] = x;				//x를 유일한 원소로 하는 집합을 생성
	}

	// x의 대표 원소를 알아냄 
	public int findSet(int x) {				//x가 속한 집합을 알아낸 후 대표 노드를 리턴
		if(x != parent[x])					//x가 대표원소가 아니면
			parent[x] = findSet(parent[x]);	//x의 parent의 대표원소를 찾음
		return parent[x];   				//그후 x의 parent로 삼음
	}

	// x가 속한 집합과 y가 속한 집합을 합침 
	public void union(int x, int y) {
		parent[findSet(y)] = findSet(x);	//x가 속한 집합에 y가 속한 집합을 합침
	}

	public void test() {// 트리 구현을 테스트하기 위한 연산 수행
		// 트리 구현이 제대로 되었는지 확인하기 위해 각 노드의 부모를 출력
		System.out.print("부모 = ");
		for(int i = 0; i < parent.length; i++) {
			System.out.print(parent[i] + " ");		//배열안에 저장된 각 원소들의 부모를 출력
		}
	}
}