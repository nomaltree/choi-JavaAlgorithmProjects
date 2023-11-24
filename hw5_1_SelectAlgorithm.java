package lab5_1;

import java.util.Scanner;

public class hw5_1_SelectAlgorithm {

	public static void main(String[] args) {
		System.out.println("hw5_1: 최성호");
		Scanner input = new Scanner(System.in);
		
		int n = input.nextInt();								//입력할 숫자의 갯수를 입력				
		int[] array = new int[n];								//입력한 갯수 크기만큼의 배열 생성
		for(int i = 0; i < array.length; i++) {					//선언한 배열에 입력한 갯수만큼의 정수를 입력받음
			array[i] = input.nextInt();
		}
		int num = select(array, 0, array.length - 1, 1);		//정수형 변수 num을 선언하고 배열에서 첫번째로 작은 수를 num안에 저장하고 출력
		System.out.println(num);	
		
		num = select(array, 0, array.length - 1, n / 2);		//배열에서 n/2번째로 작은 수를 num에 저장하고 출력
		System.out.println(num);
		
		num = select(array, 0, array.length - 1, n);			//배열에서 n번째로 작은 수를 num에 저장하고 출력
		System.out.println(num);
		
		input.close();	
	}
	//정렬되지 않은 배열에서 i번째로 작은 수를 찾아주는 함수
	private static int select(int array[], int p, int r, int i) {
		if(p == r) 												//원소가 하나일 경우 i는 반드시 1임
			return array[p];
		int q = partition(array, p, r);							//퀵정렬에서 쓰인 파티션 클래스와 동일한 클래스를 이용하여 분할함
		
		int k = q - p + 1;										//k는 기준원소가 배열에서 k번째 작은 수를 의미(q는 인덱스 번호이므로 +1을 해준다) 
		
		if(i < k)												//찾는 원소가 기준원소보다 작을경우 왼쪽 그룹으로 범위를 좁힘
			return select(array, p, q-1,i);
		else if(i == k)											//찾는 원소가 기준원소보다 같을경우 기준원소가 찾는 원소
			return array[q];
		else 													//찾는 원소가 기준원소보다 클 경우 오른쪽 그룹으로 범위를 좁힘
			return select(array, q+1, r, i-k);
	
	}
	//배열의 원소들을 오름차순으로 퀵정렬 해주는 함수
	private static int partition(int[] array, int p, int r) {
		int x = array[r];										//기준원소는 가장 오른쪽 원소로 지정				
		int i = p-1;											//기준원소보다 작은 원소가 모이는 제 1구역의 시작 지점		
		for(int j = p; j < r; j++) {							//기준원소와 아직 비교하지 않은 제 3구역의 시작지점j를 배열을 돌며 기준원소와 비교함
			if(array[j] < x) {									//기준원소보다 비교하는 단어가 작다면 1구역으로 보냄 
				swap(array, ++i, j);							
			}
																//기준원소보다 크다면 제 2구역으로 보냄
										
		}
		swap(array, i+1, r);
		return i+1;												//기준원소의 현재 인덱스번호를 반환함	
	}
	//배열의 원소들을 교환해주는 함수
	private static void swap(int[] array, int i, int j) {
		int temp = array[i];									//임시 저장 변수에 array[i]값을 저장

		array[i] = array[j];									//array[i]에 array[j]를 저장
		
		array[j] = temp;										//입시저장변수에 있던 값을 array[j]에 저장하면 교환 완료

	}
}