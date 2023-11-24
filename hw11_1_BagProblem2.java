package lab11_1;

import java.util.Scanner;

public class hw11_1_BagProblem2 {

	public static void main(String[] args) {
		System.out.println("hw11_1: 최성호");
		Scanner scanner = new Scanner(System.in);
		
		int weight;		//물건의 총 무게를 나타내는 필드
		int num;		//물건의 총 개수를 나타내는 필드
		double obj[][];	//물건의 무게와 가치를 나타낸 2차원 배열
		double unitobj[];	//물건의 단위무게당 가치를 저장할 배열
		int MaxValue = 0;	//물건의 최대가치를 나타내는 변수
		double MaxValueTable[][];	//선택된 물건의 번호와 무게와 가치를 저장할 2차원 배열
		int MaxValueTablerow = 0;	//MaxValueTable의 행번호를 나타내는 변수
		
		weight = scanner.nextInt();			//물건의 총 무게를 입력받음
		num = scanner.nextInt();			//물건의 총 개수를 입력받음
		obj = new double[num][2];				//물건의 개수만큼 행을 생성
		unitobj = new double[num];				//물건의 개수만큼의 크기로 생성
		MaxValueTable = new double[num][3];
		
		for(int i = 0; i < obj.length; i++) {
			for(int j = 0; j < obj[0].length; j++) {
				obj[i][j] = scanner.nextInt();			//입력한 물건 갯수만큼 각 물건의 무게와 가치를 입력받음
			}
		}
		for(int i = 0; i < obj.length; i++) {
			unitobj[i] = obj[i][1] / obj[i][0];		//입력한 물건의 단위무게당 가치 입력
		}
		//무게가 가득 찰 때까지 단위무게당 가치가 높은 물건을 계속해서 넣음
		while(true) {
			double unitnum = 0;		//가장 높은 단위 무게를 저장할 변수
 			int unitindex = 0;		//가장 높은 단위 무게를 가진 인덱스를 저장할 변수
			for(int i = 0; i < unitobj.length; i++) {	//가장 높은 단위무게당 가치를 가진 값을 찾음	
				if(unitnum < unitobj[i]) {
					unitnum = unitobj[i];			//가장 높은 값을 unitnum에 저장
					unitindex = i;					//그 물건의 번호를 unitindex에 저장
				}
				if(unitnum == unitobj[i]) {			//만약 단위무게당 가치가 같은 경우
					if(obj[unitindex][0] > obj[i][0]) {	//해당 물건의 무게를 비교해 무게가 더 큰쪽의 단위무게당 가치를 저장함
						continue;
					}
					else {
						unitnum = unitobj[i];
						unitindex = i;
					}
				}
			}
			if(weight > obj[unitindex][0]) {	//남은 허용 무게가 해당 물건의 무게보다 높은 경우
				MaxValue += obj[unitindex][1]; 	//MaxValue에 해당 물건의 가치를 더해줌
				weight -= obj[unitindex][0];	//허용 무게에서 해당 물건의 무게를 뺌
				//MaxValueTable에 해당 물건의 번호와 무게, 가치를 저장	
				MaxValueTable[MaxValueTablerow][0] = unitindex + 1;	
				MaxValueTable[MaxValueTablerow][1] = obj[unitindex][0];
				MaxValueTable[MaxValueTablerow][2] = obj[unitindex][1];
				MaxValueTablerow++;
			}
			else if(weight == obj[unitindex][0]) {	//남은 허용 무게가 해당 물건의 무게가 같은 경우
				MaxValue += obj[unitindex][1];	//MaxValue에 해당 물건의 가치를 더해줌
				//MaxValueTable에 해당 물건의 번호와 무게, 가치를 저장
				MaxValueTable[MaxValueTablerow][0] = unitindex + 1;
				MaxValueTable[MaxValueTablerow][1] = obj[unitindex][0];
				MaxValueTable[MaxValueTablerow][2] = obj[unitindex][1];
				break;	//무게가 가득 찼으므로 반복문을 탈출
			}
			else {		//남은 허용 무게가 해당 물건의 무게보다 낮은 경우
				MaxValue += weight * unitnum;	//MaxValue에 남은 무게에 해당 물건의 단위 무게당 가치를 곱한 값을 더해줌
				//MaxValueTable에 해당 물건의 번호와 무게, 가치를 저장	
				MaxValueTable[MaxValueTablerow][0] = unitindex + 1;
				MaxValueTable[MaxValueTablerow][1] = weight;
				MaxValueTable[MaxValueTablerow][2] = weight * unitnum;
				break;	//무게가 가득 찼으므로 반복문을 탈출
			}
			unitobj[unitindex] = -1;	//해당 단위 무게 가치를 -1로 만들어 제거
			int overobj = 0;			//물건이 다 떨어진 경우를 검사할 변수
			for(int i = 0; i < unitobj.length; i++) {
				if(unitobj[i] == -1)	//이미 넣은 물건일 경우 overobj의 값 1증가
					overobj++;
			}
			if(overobj == unitobj.length)	//만약 모든 물건을 다 넣었음에도 허용 무게가 남은 경우
				break;	//반복문을 탈출
		}
		System.out.println(MaxValue);	//최대 가치 출력
		for(int i = 0; i < MaxValueTable.length;i++) {	//선택된 물건 번호와 그 물건에 대해 선택된 무게와 가치 출력
			if(MaxValueTable[i][0] == 0)	//처음 선언할 때 물건 개수만큼의 크기로 설정해서 필요한 부분만 출력하게 설정
				break;
			System.out.println((int)MaxValueTable[i][0] + " " + (int)MaxValueTable[i][1] + " " + (int)MaxValueTable[i][2]);
		}
		scanner.close();
	}
}