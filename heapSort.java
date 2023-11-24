package lab4_1;

public class heapSort {
	public static void main(String[] args) {
			 int[] array = {1, 2, 3, 4, 5, 4, 5, 9, 8};		//n개의 원소가 저장된 배열을 선언 여기서는 총 9개로 선언
	 		 HeapSort(array, array.length - 1);				//힙정렬을 수행
			 	
			 for(int i = 0; i < array.length; i++) {		//오름차순으로 힙정렬된 배열을 출력
				 System.out.print(array[i] + " ");
			 }
	}
	public static void HeapSort(int array[], int n) {		//array[]를 힙성질을 만족하도록 만든 뒤 오름차순으로 정렬하는 클래스
		buildHeap(array, n);								//먼저 배열을 최대힙으로 만들어줌
		
		for(int i = n ; i > 0; i--) {						//최대힙이 된 배열을 마지막 원소와 첫번째 원소를 교환하여 제거하는 방식으로 오름차순 정렬수행
			int temp = array[0];
			array[0] = array[i];
			array[i] = temp;
			heapify(array, 0, i-1);							//총 n(배열크기)번 반복하여 오름차순으로 힙재구성
		}
	}
	public static void buildHeap(int array[], int n) {		//전달받은 배열을 최대힙으로 만들어주는 클래스
		for(int i = n / 2 ; i > -1; i-- ) {
			heapify(array, i, n);							//배열 크기의 절반의 해당하는 인덱스부터 자식노드와 크기를 비교하며 힙재구성
		}
	}
	public static void heapify(int array[], int k, int n) {	//array[k]를 루트로 하는 트리를 힙성질을 만족하도록 수선해주는 클래스 n은 최대 인덱스 번호
		int left = 2*k+1;									//검사할 인덱스의 왼쪽 자식노드의 인덱스 번호
		int right = 2*k+2;									//검사한 인덱스의 오른쪽 자식노드의 인덱스 번호
		int bigger = k;										//부모노드에 저장할 제일 큰값, 초기값을 원래 부모노드의 인덱스번호로 지정
		if(right <= n ) {									//array[k]가 두 자식을 가지는 경우	
			if(array[left] > array[right]) {				//두 자식의 값을 비교후 더 큰값을 bigger에 저장
				bigger = left;
			}
			else {						
				bigger = right;
			}
		}
		else if(left <= n) {								//만약 array[k]의 자식이 하나일 경우 그 값을 bigger에 저장
			bigger = left;
		}
		else return;										//자식이 없을 경우 리프노드이므로 종료
		
		if(array[bigger] > array[k]) {						//만약 큰 자식이 부모노드보다 크면 힙성질 위반이므로 재귀적으로 조정
			int temp = array[k];							//부모노드와 큰자식의 값을 교환	
			array[k] = array[bigger];
			array[bigger] = temp;
			heapify(array, bigger, n);						//이제 교환된 값을 기준으로 자식 노드의 힙재구성을 재귀적으로 시행
					
		}
	}
}