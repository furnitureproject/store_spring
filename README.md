# 공지

ENTITY, REPOSITORY, MAPPER, SERVICE 는 따로 추가하지마시고 요구사항 모아서 한번에 처리하도록 하겠습니다.

# 행동강령?

0. 프로젝트명은 가급적이면 대문자 작성

0.1 패키지명은 소문자만 사용
    가급적 한단어로 표시
<com.springteam.상위패키지.하위패키지>

1. 메소드 명은 명령어(동사로시작) 형식으로 작성
   메소드 명은 카멜 표기법으로 작성
<insertBoard(O), boardInsert(X)>

2. 클래스명은 명사로 시작
   클래스 명은 파스칼 표기법으로 작성
<BoardController, BoardEntity>

3. 변수명은 카멜 표기법으로 작성
<totalCount, pageNum>

4. 상수는 전부 대문자로 작성
   언더바_ 로 구분(스네이크 케이스 명명법)
<MAX_PRIORITY , LIFESPAN>

4. DB 컬럼명은 언더바_로 작성
<board_name, seller_phone>

5. SQL문은 대문자로 작성

6. 회원가입같이 유효성 검사가 필요한 경우엔 백엔드에서도 유효성 검사를 할것

7. 짧은거는 3항연산자쓰는것을 고려해볼것
//IF ELSE

int a;
if(5<4) {
    a = 50;
}else {
    a = 40;
}
System.out.println(a); //결과 = 40 
		
//삼항연산자

int b = (5 < 4) ? 50 : 40; 
System.out.println(b); //결과 = 40

8. long 타입은 숫자 마지막에 대문자 L 표시

9. url에 CRUD 함수명 사용 X

10. url 주소에서 파라미터 같은것이 길 경우 하이폰 - 사용, 언더바 _ 사용 X

11. url 주소 대문자 사용 X

12. url주소에 .json, .xml 같은 확장자명 표시 X

13. 계층적 관계일 경우엔 슬래시 / 사용

14. url 주소 마지막에 트레일링 슬래시 / 사용 X

15. 테스팅은 Junit, Postman 둘다 사용한다. 
 
