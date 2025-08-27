
## 프로젝트 이름

### 당신 근처에, U:Near

--- 

## 프로젝트 개요

> 주제 : 오프라인 매장과 사용자 간 연결을 돕는 참여형 혜택 플랫폼
> 
> 목적 : AI 기반 소비 성향과 실시간 위치를 반영한 참여형 멤버십 혜택 추천
---

## 시스템 아키텍쳐
<img width="1129" height="495" alt="스크린샷 2025-08-07 오후 8 56 23" src="https://github.com/user-attachments/assets/c24c1696-15b1-43b7-8d5f-81fb1c334c31" />


---

## ERD
<img width="800" height="1017" alt="스크린샷 2025-08-07 오후 9 06 20" src="https://github.com/user-attachments/assets/a90fae99-a296-462e-8608-1d6e4ea84083" />


---

## 기술 스택

| 구성 요소 | 기술 |
|-----------|------|
| Backend | Java, Spring Boot, JPA , OAuth2, JWT, Session , PostgreSQL, PostGIS, PGVector , Redis, Redis Stream, Apache Airflow|
| DevOps | AWS EC2, ELB, S3, CloudFront, Docker, Docker Hub, GitHub Actions|
| Tools | Swagger, Postman , DBeaver, Coderabbit|
| AI | OpenAI, RAG |

---

## 프로젝트 구조

<pre>
unear-infra/
├── unear-user-backend/          # 사용자 서비스 API 서버
├── unear-pos-backend/           # 가맹점 POS 연동 서버
├── unear-admin-backend/         # 관리자 웹 대시보드 백엔드
├── unear-log-consumer/          # Redis Stream 로그 컨슈머 (로그 적재)
└── unear-airflow-analysis/      # 사용자 행동 로그 요약 및 통계 분석 (Airflow DAG)
</pre>


## 주요 기능

---

### 1. 혜택 및 할인 정책
- 일반 장소 및 프랜차이즈에 대한 혜택 정보 제공
- 사용자의 멤버십 등급에 따른 혜택 구분
- 장소별 적용 가능한 할인 정책 조회 가능

### 2. 장소 탐색 및 즐겨찾기
- 필터 조건에 따른 장소 검색 및 리스트 제공
- 현재 위치 기반 주변 장소 탐색
- 장소별 상세 정보 제공 (혜택, 카테고리, 태그 등 포함)
- 사용자별 즐겨찾기 추가/삭제 기능 제공

### 3.  쿠폰 발급 및 조회
- 일반 다운로드 및 선착순 쿠폰 발급 처리
- 사용자 보유 쿠폰 조회 및 상세 확인
- 장소 기반 쿠폰 템플릿 제공
- 프랜차이즈/개별 장소와 연동된 쿠폰 정책 구성

### 4. 사용자 계정 및 인증
- 회원가입, 로그인, 로그아웃 
- 이메일/비밀번호 기반 인증 및 복구
- OAuth 로그인 후 프로필 등록 지원

### 5.  사용자 통계 및 이용 내역
- 사용자 개인 통계 요약 및 상세 제공
- 월별 사용 내역 기반 통계 계산
- 행동 로그 기반 분석 연동 (Airflow)

### 6.  바코드 및 출석 시스템
- 사용자 바코드 제공 (제휴처 리더기로 사용 가능)
- 주간 출석 기반 스탬프 기능
- 이벤트 참여를 통한 출석 상태 확인

### 7.  룰렛 이벤트 시스템
- 특정 이벤트에 대한 룰렛 참여 기능
- 룰렛 결과 확인 및 경품 처리 흐름

### 8.  제휴처 POS 연동 (Internal API)
- 쿠폰 사용 알림 등 POS 시스템과의 내부 통신
- 외부 연동이 필요한 알림/결제 상태 전송 기능 제공

### 9.관리자 대시보드 연동
- 행동 로그 요약(user_action_summary) 기반 통계 제공


![CodeRabbit Pull Request Reviews](https://img.shields.io/coderabbit/prs/github/ureca-poject-unear/unear-admin-backend?utm_source=oss&utm_medium=github&utm_campaign=ureca-poject-unear%2Funear-admin-backend&labelColor=171717&color=FF570A&link=https%3A%2F%2Fcoderabbit.ai&label=CodeRabbit+Reviews)
