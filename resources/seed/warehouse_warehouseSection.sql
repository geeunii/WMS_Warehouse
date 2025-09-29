insert into Warehouse (`warehousename`, `warehouseAddress`, `warehouseStatus`, `warehouseCityname`, `warehouseArea`, `regdate`, `floorheight`, `mid`) values
('서울 소형 창고', '서울특별시 강남구 테헤란로 123', '운영중', '서울', 1500, '2022-08-15',8,2),
('천안 중형 창고', '충청남도 천안시 서북구 번영로 456', '운영중', '천안', 4000, '2022-05-20',10, 3),
('인천 대형 창고', '인천광역시 중구 공항로 789', '운영중', '인천',5000, '2022-01-10',15,4);



insert into WarehouseSection (sectionname,maxvol,warehouseID) values
('A',9000,1),
('B',1800,1),
('A',9000,2),
('B',9000,2),
('C',13500,2),
('D',4500,2),
('A',12960,3),
('B',19800,3),
('C',10800,3),
('D',18000,3),
('E',9000,3),
('F',3000,3);
