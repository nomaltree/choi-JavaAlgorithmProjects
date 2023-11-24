package lab6_1;

import java.util.Scanner;

public class hw6_1_BinarySearchTree {
	public static void main(String[] args) {
		System.out.println("hw6_1:최성호");

		// (1) 정수 키값을 저장할 공백 이진검색트리 tree를 생성
		MyBinarySearchTree tree = new MyBinarySearchTree();

		// (2) 사용자가 원하는 갯수의 정수 키값을 입력받아 tree에 삽입한 후, tree 출력
		Scanner input = new Scanner(System.in);
		int n = input.nextInt();
		for(int i = 0; i < n; i++) {
			tree.add(input.nextInt());
		}
		System.out.println(tree);

		// (3) 정수 키값 x, y, z를 입력받아 각각 트리에 있는지 여부를 출력
		int x = input.nextInt();
		int y = input.nextInt();
		int z = input.nextInt();
		System.out.println(tree.contains(x) + " " + tree.contains(y) + " " + tree.contains(z));
		
		// (4) tree의 최대 키값을 삭제하고, tree 출력
		tree.removeMax();
		System.out.println(tree);
		
		// (5) tree에서 앞의 정수 키값 x, y, z를 삭제하고, tree 출력(키값이 없으면 삭제하지 않으면 됨)
		tree.remove(x);
		tree.remove(y);
		tree.remove(z);
		System.out.println(tree);
		
		input.close();
	}
}

// 정수 키값을 갖는 이진검색트리를 구현하는 클래스
class MyBinarySearchTree {
	// 루트 노드를 가리키는 인스턴스 변수 root (알고리즘 연습을 위해 root 만 둘 것)
	private Node root = null;

	// 노드의 구조를 정의하는 클래스 Node (알고리즘 연습을 위해 노드에 key, left, right 필드만 둘 것)
	private class Node {
		int key;
		Node left;
		Node right;
	}

	// 키 값을 매개변수로 받아 그 키값이 존재하는지 여부(true/false)를 리턴
	public boolean contains(int x) {
		Node contain = root;				//키 값을 찾기 위해 순회할 노드를 하나 선언하고 루트로 선언
		while(contain != null) {			//특정 길을 순회할때까지 시행
			if(contain.key > x)				//해당 노드의 키값이 x보다 크면 왼쪽으로 이동
				contain = contain.left;
			else if (contain.key == x)		//해당 노드의 키값이 x와 같으면 true를 리턴
				return true;
			else if(contain.key < x)		//해당 노드의 키값이 x보다 작으면 오른쪽으로 이동
				contain = contain.right;
		}
		return false;						//만약 반복문을 다 돌고나면 키값이 없으므로 false를 리턴
	}

	//매개변수 없이 최대 키값을 삭제하는 메소드
	public void removeMax() {
		Node removeMax = root;
		if(root.right == null) {			//root가 오른쪽에 자식노드가 아예 없는 특수한 경우
			if(root.left != null) 			//왼쪽 자식이 있으면 루트노드를 없애고 왼쪽 자식을 저장
				root = root.left;
			else 							//왼쪽 자식도 없으면 루트노드 자체를 null로 저장
				root = null;
			return;
		}
		while(removeMax.right != null) {			//오른쪽 링크가 null이 될때까지 오른쪽으로 계속 순회함
			if(removeMax.right.right == null) {	//자식노드의 자식노드가 오른쪽 링크가 null일 경우 자식의 자식노드가 최대원소 
				if(removeMax.right.left == null) 	//자식의 자식노드의 왼쪽 링크도 null일 경우
					removeMax.right = null;		//자식의 자식노드가 최대 원소이므로 자식 노드의 오른쪽 링크를 null로 만듬
				else 
					removeMax.right = removeMax.right.left;	//자식의 오른쪽 링크에 자식의 자식노드의 왼쪽 링크를 넣어 최대원소 삭제
				return;	
			}
			else
				removeMax = removeMax.right;		//자식노드의 자식노드가 null이 아닐경우 오른쪽으로 한단계 이동
		}
	}
	
	//매개변수로 전달받은 키값을 삭제하는 메소드
	public void remove(int key) {
		Node remove = root;							//삭제할 노드
		Node parents = root;						//삭제할 노드의 부모노드
		while(remove != null) {						//한 루트를 모두 순회할때까지 반복
			if(root.key == key) {					//삭제할 노드가 root노드인 특수한 경우
				if(root.right == null && root.left == null) 	//루트노드밖에 없는경우
					root = null;								//루트를 null로 지정
				else if(root.right != null && root.left == null) 	//루트노드에 오른쪽 자식만 있는 경우
					root = root.right;								//루트노드의 오른쪽 자식을 루트노드로 저장
				
				else if(root.right == null && root.left != null) 	//루트노드에 왼쪽 자식만 있는 경우
					root = root.left;								//루트노드의 왼쪽 자식을 루트노드로 지정
				else {												//루트노드에 자식이 둘다 있는 경우 
					Node nextroot = root.right;						//루트노드의 오른쪽 서브트리에서 가장 작은 값을 저장할 변수 선언
					Node nextrootparent = root.right;
					while(nextroot.left != null) {						//오른쪽 서브트리에서 가장작은 값을 찾을때까지 반복
						nextrootparent = nextroot;					//현재노드를 부모노드로 저장
						nextroot = nextroot.left;					//왼쪽으로 이동	
					}
					root.key = nextroot.key;						//루트노드의 키값과 nextroot의 키값을 교환	
					if(nextroot == root.right) 						//만약 nextroot가 루트노드의 오른쪽 자식이면
						root.right = nextroot.right;				//루트노드의 오른쪽 링크에 nextroot의 오른쪽 링크를 저장
					else 
						nextrootparent.left = nextroot.right;		//아닐 경우 nextrootparent의 왼쪽 링크에 nextroot의 오른쪽 링크를 저장
				}
			return;	
			}
			if(remove.key == key) {					//삭제할 값과 노드의 키값이 같은 경우
				if(remove.right == null && remove.left == null) {	//삭제할 노드의 자식이 없는 경우
					if(parents.right == remove) 					//부모노드의 오른쪽 자식이면 부모노드의 오른쪽 링크를 null로 지정
						parents.right = null;
					else 											//부모노드의 왼쪽 자식이면 부모노드의 왼쪽 링크를 null로 저장
						parents.left = null;
					return;
				}
				else if(remove.right != null && remove.left == null) {	//삭제할 노드가 오른쪽 자식만 있는 경우
					if(parents.right == remove) 
						parents.right = remove.right;					//부모노드의 오른쪽 자식이면 부모노드의 오른쪽 링크에 삭제할 노드의 오른쪽 링크를 저장
					else
						parents.left = remove.right;					//부모노드의 왼쪽 자식이면 부모노드의 왼쪽 링크에 삭제할 노드의 오른쪽 링크를 저장
					return;
				}
				else if(remove.right == null && remove.left != null) {	//삭제할 노드가 왼쪽 자식만 있는 경우
					if(parents.right == remove) 
						parents.right = remove.left;	 				//부모노드의 오른쪽 자식이면 부모노드의 오른쪽 링크에 삭제할 노드의 왼쪽 링크를 저장
					else
						parents.left = remove.left;						//부모노드의 왼쪽 자식이면 부모노드의 왼쪽 링크에 삭제할 노드의 왼쪽 링크를 저장
					return;
				}
				else {													//삭제할 노드가 자식이 둘다 있는 경우
					Node Small = remove.right;							//삭제할 노드의 오른쪽 서브트리에서 최소인 값을 저장할 노드 변수 선언
					Node Smallparent = remove.right;					//오른쪽 서브트리에서 가장작은 값의 부모노드
					while(Small.left != null) {							//가장 작은 값을 찾을때까지 반복
						Smallparent = Small;							//현재노드를 부모노드로 저장
						Small = Small.left;								//왼쪽으로 이동
					}
					remove.key = Small.key;								//삭제할 노드의 키값에 오른쪽 서브트리에서 가장 작은 노드값을 저장
					if(Small == remove.right) 							//만약 오른쪽 서브트리에서 가장작은 값이 삭제할 노드의 자식노드이면 바로 삭제할 노드의 오른쪽 링크에 자식의 자식노드를 저장
						remove.right = Small.right;
					else 												//그렇지 않은 경우 오른쪽 서브트리에서 가장작은값의 부모노드의 왼쪽 링크에 가장작은값의 오른쪽 링크를 저장
						Smallparent.left = Small.right;
					return;	
				}
			}
			else if(remove.key > key) {									//노드의 키값이 전달받은 값보다 크면 왼쪽으로 이동
				parents = remove;										//현재 노드를 부모노드로 저장
				remove = remove.left;									//왼쪽으로 이동
			}
			else if(remove.key < key) {									//노드의 키값이 전달받은 값보다 작으면 오른쪽으로 이동
				parents = remove;										//현재 노드를 부모노드로 저장
				remove = remove.right;									//오른쪽으로 이동
			}
		}
		return;															//일치하는 값이 없어 순회가 끝나면 그대로 return					
	}
	
	// 매개변수로 받은 키값을 트리에 삽입
	public void add(int key) {
		root = treeInsert(root, key);
	}

	// t를 루트로 하는 트리에 key를 삽입하고, 삽입 후 루트 노드를 리턴하는 재귀 메소드
	private Node treeInsert(Node t, int key) {
		if(t == null) {
			Node r = new Node();
			r.key = key;
			return r;
		}
		else if(key < t.key) {
			t.left = treeInsert(t.left, key);
			return t;
		}
		else if(key > t.key) {
			t.right = treeInsert(t.right, key);
			return t;
		}
		else {
			return t;  // 이미 존재하는 키값인 경우 삽입하지 않음
		}
	}
	// 트리의 키값들을 중순위 순회하여 오름차순으로 나열한 하나의 문자열을 만들어 리턴
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer("tree = ");
		if(root != null) {
			inorder(result, root);
		}
		return result.toString();
	}

	// t를 루트로 하는 트리를 중순위 순회
	private void inorder(StringBuffer result, Node t) {
		if(t != null) {
			inorder(result, t.left);
			result.append(t.key + " ");
			inorder(result, t.right);
		}
	}
}
