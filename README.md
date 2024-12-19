### API Documentation

---

## 유저 관리

### 회원 가입

**URI**  
`POST /user/register`

**설명**  
사용자 회원 가입을 처리합니다.

**요청 파라미터**

| 이름        | 타입     | 설명     | 필수 여부 |
|-------------|----------|----------|-----------|
| email       | String   | 이메일   | O         |
| password    | String   | 비밀번호 | O         |
| nickname    | String   | 닉네임   | O         |

**응답**

| 코드 값 | 설명               |
|---------|--------------------|
| 1       | 회원 가입 성공     |
| 2       | 회원 가입 실패: 아이디 중복 |

---

### 로그인

**URI**  
`POST /user/login`

**설명**  
사용자 로그인을 처리합니다.

**요청 파라미터**

| 이름        | 타입     | 설명     | 필수 여부 |
|-------------|----------|----------|-----------|
| email       | String   | 이메일   | O         |
| password    | String   | 비밀번호 | O         |

**응답**

| 코드 값 | 설명                        |
|---------|-----------------------------|
| 1       | 로그인 성공                 |
| 2       | 로그인 실패: 비밀번호 오류 |
| 3       | 로그인 실패: 아이디 없음   |

**응답 예시**

```json
{
  "code": "1",
  "userId": 7
}
```

---

### 프로필 조회

**URI**  
`POST /user/info`

**설명**  
유저 프로필 정보를 반환합니다.

**요청 예시**

```json
{
  "userId": 7
}
```

**응답 예시**

```json
{
  "userName": "난정",
  "introduction": "난정이다"
}
```

---

## 문제 관리

### 문제 만들기

**URI**  
`POST /problem/creation`

**설명**  
문제를 생성합니다.

**요청 예시**

```json
{
  "userId": 1,
  "title": "연예",
  "field": "연예",
  "detailedField": "소녀시대",
  "category": "객관식",
  "count": 10,
  "difficulty": 3
}
```

**응답 예시**

```json
{
  "problemPaperId": 1
}
```

---

### 문제 출력

**URI**  
`POST /problem/all`

**설명**  
생성된 문제를 반환합니다.

**요청 예시**

```json
{
  "problemPaperId": 1
}
```

**응답 예시**

```json
[
  "1. 문제: 배열(Array)과 연결 리스트(Linked List)의 차이점에 대해 설명해보세요.",
  "2. 문제: 스택(Stack)과 큐(Queue)의 동작 방식에 대해 설명해보세요.",
  "3. 문제: 이진 탐색 트리(Binary Search Tree)의 특징과 동작 방식에 대해 설명해보세요."
]
```

---

### 정답 및 풀이 출력

**URI**  
`POST /problem/solution`

**설명**  
문제에 대한 정답 및 풀이를 반환합니다.

**요청 예시**

```json
{
  "problemPaperId": 1
}
```

**응답 예시**

```json
[
  {
    "answer": "답: 스택은 후입선출(LIFO) 구조입니다...",
    "explanation": "스택은 함수 호출 등에 사용됩니다."
  },
  {
    "answer": "답: 배열은 연속된 메모리 구조이고...",
    "explanation": "배열은 빠른 접근이 가능하지만 고정 크기입니다."
  }
]
```

---

## 문제지 관리

### 문제지 정보 조회

**URI**  
`POST /paper/info`

**설명**  
사용자가 생성한 문제지 정보를 반환합니다.

**요청 예시**

```json
{
  "userId": 7
}
```

**응답 예시**

```json
[
  {
    "problemPaperId": 78,
    "count": 2,
    "title": "영어 노트",
    "field": "영어",
    "detailedField": "초등 영어",
    "date": "2023-10-19T21:57:45"
  }
]
```

---

### 문제지 북마크하기

**URI**  
`POST /paper/bookmark`

**설명**  
문제지를 북마크하거나 북마크를 해제합니다.

**요청 예시**

```json
{
  "problemPaperId": 95
}
```

**응답 예시**

```json
{
  "message": "Successful processing",
  "bookmarked": true
}
```

---

### 북마크된 문제지 조회

**URI**  
`POST /paper/bookmarked/info`

**설명**  
북마크된 문제지를 조회합니다.

**요청 예시**

```json
{
  "userId": 7
}
```

**응답 예시**

```json
[
  {
    "problemPaperId": 62,
    "count": 3,
    "title": "수학 노트",
    "field": "수학",
    "detailedField": "삼각 함수"
  }
]
```

---

### 문제지 삭제

**URI**  
- 개별 삭제: `POST /paper/deletion`  
- 전체 삭제: `POST /paper/clear`  
- 선택 삭제: `POST /paper/selection`

**설명**  
문제지를 삭제합니다.

**요청 예시**

- **개별 삭제**
```json
{
  "userId": 7,
  "problemPaperId": 94
}
```

- **전체 삭제**
```json
{
  "userId": 7
}
```

- **선택 삭제**
```json
{
  "userId": 7,
  "problemPaperIds": [94, 97, 100]
}
```

**응답 예시**

```json
true
```

---
