# 📦 창고 관리 시스템 (WMS) - 창고 관리 기능

WMS 프로젝트의 기능인 **창고(Warehouse), 창고 구역(Section)** 관리 기능에 대한 기술적인 개요와 구조를 설명합니다.

## 아키텍처 (Architecture)

본 기능은 **MVC (Model-View-Controller) 디자인 패턴**을 기반으로 설계되었습니다.

- **View**: 사용자 인터페이스(Console UI)를 담당하며, 사용자의 입력을 받고 결과를 출력합니다.
- **Controller**: View와 DAO(Model) 사이의 흐름을 제어하고, 권한 확인 등 비즈니스 로직을 처리합니다.
- **DAO (Data Access Object)**: 데이터베이스와의 모든 통신을 전담합니다.
- **VO (Value Object)**: 각 계층 간에 데이터를 전달하기 위한 데이터 객체입니다.
  
[사용자] ↔ [ View ] ↔ [ Controller ] ↔ [ DAO ] ↔ [ Database ]

## 주요 기능 (Core Features)

- **창고 관리 (Warehouse)**
   - 창고 등록, 수정, 삭제, 조회 (CRUD)
   - 창고 등록 시 `면적`과 `층고`를 기반으로 `최대 수용량(maxCapacity)` 자동 계산 (DB Generated Column)
   - 창고 수정 시 변경할 필드만 입력하여 '부분 수정' 가능 (DB `COALESCE` 함수 활용)

- **창고 구역 관리 (Warehouse Section)**
   - 구역 등록, 수정, 삭제, 조회 (CRUD)
   - 구역 등록/수정 시 부모 창고의 `maxCapacity`를 초과하지 않도록 용량 실시간 확인 (DB Stored Procedure)
   - 대화형 구역 등록: 사용자가 원할 때까지 구역을 연속으로 추가하고, 등록 시마다 남은 용량 표시

- **권한 관리 (Authorization)**
   - `Master`(총 관리자)와 `Admin`(일반 관리자) 역할(Role) 구분
   - 등록, 수정, 삭제 기능은 `Master` 권한을 가진 관리자만 실행 가능 (`AppSession`을 통한 권한 확인)

## 🧱 데이터베이스 설계 (Database Design)

핵심 비즈니스 로직은 데이터의 일관성과 무결성을 보장하기 위해 **MySQL Warehouse Procedure**에 집중되어 있습니다.

### 주요 테이블

- `Warehouse`: 창고의 기본 정보를 저장. `maxCapacity`는 자동 계산 컬럼.
- `WarehouseSection`: 창고 내부의 구역 정보를 저장.

### 핵심 프로시저

- `sp_InsertWarehouse`: 자동 계산 컬럼을 제외한 창고 정보를 등록합니다.
- `sp_updateWarehouse`: `COALESCE`를 사용하여 전달된 값만 선택적으로 수정합니다.
- `sp_InsertSection`: 창고의 남은 용량을 확인하고, 용량이 충분할 경우에만 구역을 등록합니다.
- `sp_UpdateSection`: 구역의 `maxVol` 변경 시, 용량 변화량을 계산하여 창고의 `maxCapacity`를 초과하지 않는지 확인합니다.

## 주요 클래스 구조 (Key Class Structure)

- **`controller` 패키지**
   - `WarehouseMain_Controller`: 창고 관련 기능들의 상위 메뉴를 제어하는 **라우터 컨트롤러**.
   - `Warehouse_Controller`: **창고** CRUD 전문 컨트롤러.
   - `Section_Controller`: **구역** CRUD 전문 컨트롤러.
- **`view` 패키지**
   - `WarehouseMainView`: 상위 메뉴 UI.
   - `WarehouseAdminView`: 창고 CRUD UI.
   - `WarehouseSectionAdminView`: 구역 CRUD UI.
- **`model.warehouse_service` 패키지**
   - `WarehouseDAO`: 창고 DB 작업 전담.
   - `WarehouseSectionDAO`: 구역 DB 작업 전담.
- **`vo.Warehouses` 패키지**
   - `WarehouseBaseVO`, `WarehouseChildVO`: 상속을 통한 필드 재사용을 위한 부모 클래스.
   - `Warehouse`, `WarehouseSection`: 각 테이블에 매핑되는 데이터 객체.

## 사용한 기술 (Tech Stack)

- **Language**: Java 24 - Oracle
- **Database**: MySQL 8.0
- **Library**:
   - `mysql-connector-java`: MySQL JDBC Driver
   - `Lombok`: VO 클래스 코드 자동 생성
   - `org.json`: (필요 시) 데이터 전송을 위한 JSON 처리