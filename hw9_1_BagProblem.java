package lab9_1;

import java.util.Scanner;

public class hw9_1_BagProblem {

	public static void main(String[] args) {
		System.out.println("hw9_1: 최성호");
		Scanner scanner = new Scanner(System.in);
		MaxValue bag = new MaxValue();			//물건의 최대가치를 구해주는 클래스의 객체를 선언
		
		int weight;		//물건의 총 무게를 나타내는 필드
		int num;		//물건의 총 개수를 나타내는 필드
		int obj[][];	//물건의 무게와 가치를 나타낸 2차원 배열
		int MaxValueTable[][];	//정해진 무게당 최대 가치를 물건 개수에 따라 순차적으로 값을 넣을 2차원 배열

		weight = scanner.nextInt();			//물건의 총 무게를 입력받음
		num = scanner.nextInt();			//물건의 총 개수를 입력받음
		obj = new int[num][2];				//물건의 개수만큼 행을 생성
		MaxValueTable = new int[num+1][weight+1];	//첫번째 행과 열을 0으로 넣기 위해 무게와 갯수보다 1더 크게 설정
		for(int i = 0; i < obj.length; i++) {
			for(int j = 0; j < obj[0].length; j++) {
				obj[i][j] = scanner.nextInt();			//입력한 물건 갯수만큼 각 물건의 무게와 가치를 입력받음
			}
		}
		for(int i = 0; i < MaxValueTable.length; i++)			
			MaxValueTable[i][0] = 0;					//최대가치 테이블의 첫 열은 모두 0으로 설정
		for(int i = 0; i < MaxValueTable[0].length; i++)
			MaxValueTable[0][i] = 0;					//최대가치 테이블의 첫 행은 모두 0으로 설정
	
		System.out.println("최대 가치 = " + bag.getMaxValue(MaxValueTable, obj, num, weight));	//물건의 최대가치를 반환받아 출력
		System.out.print("선택 물건 = ");
		bag.objects(MaxValueTable, obj, num, weight);		//선택된 물건을 출력
		scanner.close();

	}
}
//물건의 최대 가치와 선택 물건을 출력하는 클래스
class MaxValue {	
	//물건의 최대가치를 반환하는 메소드 여기서는 MaxValueTable의 마지막 행열 칸이 최대가치이므로 그 값을 반환
	public int getMaxValue(int[][] MaxValueTable, int[][] obj, int num, int weight) {
		for(int i = 1; i < MaxValueTable.length; i++) {
			for(int j = 1; j < MaxValueTable[0].length; j++) {		//인덱스번호 [1][1]부터 순차적으로 최대가치를 저장
				if(i == 1) {										//배낭에 넣을 물건이 1개인 특수한 경우 
					if(j >= obj[0][0])								//현재 열(허용가능무게)가 물건1의 무게보다 클 경우
						MaxValueTable[i][j] = obj[0][1];			//물건1의 무게를 저장
					else
						MaxValueTable[i][j] = 0;					//아닐경우 넣을 수 없으므로 0을 저장
				}
				else {													//배낭에 넣을 물건이 2개 이상인 경우
					if(j - obj[i-1][0] < 0) {							//현재 넣을수 있는 무게에서 현재 행번호 물건의 무게를 뺀 값이 0보다 작은 경우
						MaxValueTable[i][j] = MaxValueTable[i-1][j];	//바로 윗칸의 값을 저장(현재 물건(i번째)을 제외했을때 넣을수 있는 최대가치값) 
					}
					else {												//현재 넣을수 있는 무게에서 현재 행번호 물건의 무게를 뺀 값이 0보다 같거나 큰 경우
						if(MaxValueTable[i-1][j] >= MaxValueTable[i-1][j-obj[i-1][0]] + obj[i-1][1]) {	//현재 칸의 바로 윗칸과 현재 허용가능 무게(열번호)에서 해당 물건의 무게를 뺀 칸 + 해당 물건의 가치를 더한 값을 비교
							MaxValueTable[i][j] = MaxValueTable[i-1][j];								//바로 윗칸이 클경우 윗칸의 값을 저장(현재 물건을 빼고 배낭에 넣었을 때의 최대값을 저장)
						}	
						else																			//허용가능 무게(열번호)에서 해당 물건의 무게를 뺀 칸 + 해당물건의 가치가 클경우				
							MaxValueTable[i][j] = MaxValueTable[i-1][j-obj[i-1][0]] + obj[i-1][1];		//해당 칸의 값을 저장(해당 물건을 배낭에 넣었을 때 최대가치가 더 크므로)
					}
				}
			}
		}
		return MaxValueTable[num][weight]; //최대가치 테이블의 가장 마지막행열 칸이 문제에서 원하는 답이므로 그 값을 반환
	}
	//선택된 물건을 알려주는 메소드
	public void objects(int[][] MaxValueTable, int[][] obj, int num, int weight) {		//인자로 최대가치 테이블 물건 테이블 물건 갯수 허용 무게를 전달받음
		if(MaxValueTable[num][weight] == 0)												//최대가치 값이 0이면 리턴
			return;
		if(weight - obj[num-1][0] < 0)													//현재 허용 무게가 현재 물건의 무게보다 작으면 바로 윗칸의 값으로 재귀적 호출
			objects(MaxValueTable, obj, num-1, weight);
		else {																			//아닐 경우 바로 윗칸의 값과 현재 허용 가능 무게에서 현재 물건의 무게를 뺀값의 열의 값을 비교
			if(MaxValueTable[num-1][weight] >= MaxValueTable[num-1][weight - obj[num-1][0]] + obj[num-1][1]) {
				objects(MaxValueTable, obj, num-1, weight);								//윗칸의 값이 클경우 윗칸의 행 열 번호를 전달해 재귀적 호출
			}
			else {																		//아닐경우
				System.out.print(num + " ");											//해당 물건은 선택된 물건이므로 출력	
				objects(MaxValueTable, obj, num-1, weight - obj[num-1][0]);				//현재허용 가능 무게에서 현재 물건의 무게를 뺀 값의 열의 인덱스번호를 전달하여 재귀적 호출
			}
		}
		
	}
}