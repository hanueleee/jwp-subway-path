# jwp-subway-path

## API 설계
- [x] 역 등록 API (post - "/stations")
```JSON
  {
    "stationName" : "잠실"
  }
```
```JSON
  {
  "stationId" : 1,
  "stationName" : "잠실"
  }
```

- [x] 최초 노선 등록 API (post - "/lines") `addInitialLine(Line line, Station s1, Station s2, int dist)`
```JSON
  {
    "lineName" : "2호선",
    "upStationId" : 1,
    "downStationId" : 2,
    "distance" : 10
   }
```
````JSON
{
    "lineId" : 1,
    "lineName" : "2호선"
}
````
- [x] 노선에 역 등록 API 신규 구현 (post - "/lines/{lineId}/stations")
```JSON
  {
    "upStationId" : 3,
    "downStationId" : 1,
    "distance" : 5
  }
```
```JSON
  {
  "lineId" : 1,
  "lineName" : "2호선",
  "stationIds" : [1, 2, 3]
  }
```

3. 역 제거 API <- 좀 비즈니스 로직 있음
4. edge 테이블 update 어떻게 할건지 .. <- db관련
5. 조회 api

1. Line의 73번째줄 (기존에 있는 역의 개수 검사) <- 테스트 내일하자
2. Line에 역을 추가할 때 거리가 에러나는경우 테스트


- [ ] 노선의 모든 역 조회 API (get - "/lines/{lineId}")
- [ ] 모든 노선 조회 API (get - "/lines")
- [x] 노선에 역 제거 API 신규 구현 (delete - "/lines/{lineId}/stations/{stationId}") <- 역

## 요구사항 정리
- [ ] 노선에 역 등록
  - [ ] 노선에 등록되는 역의 위치는 자유롭게 지정할 수 있어야 합니다.
    - [ ] A-B-C 역이 등록되어 있는 노선에 D역을 등록할 경우 A역과 B역 사이에도 등록이 가능하고, A역 앞에도 등록을 할 수 있습니다.
  - [ ] 노선에 역이 등록될 때 거리 정보도 함께 포함되어야 합니다.
    - [ ] A-B-C역이 노선에 등록되어있는 경우 A-B, B-C의 거리 정보도 함께 있어야 합니다.
    - [ ] 거리 정보는 양의 정수로 제한합니다.
  - [ ] 노선에 역이 하나도 등록되지 않은 상황에서 최초 등록 시 두 역을 동시에 등록해야 합니다.
    - [ ] 노선에 역이 하나도 없는 경우에 역 하나만 등록을 하려 할 경우 거리 정보를 가질 수 없기 때문입니다.
  - [ ] 하나의 역은 여러 노선에 등록이 될 수 있습니다.
    - [ ] 1호선: A-B-C, 2호선: Z-B-D 처럼 B역은 두개 이상의 노선에 포함될 수 있습니다.
  - [ ] 노선은 갈래길을 가질 수 없습니다.
  - [ ] 노선 가운데 역이 등록 될 경우 거리 정보를 고려해야 합니다.
    - [ ] A-B-C 노선에서 B 다음에 D 역을 등록하려고 하는데 B-C가 3km, B-D거리가 2km라면 B-D거리는 2km로 등록되어야 하고 D-C 거리는 1km로 등록되어야 합니다.
  - [ ] 노선 가운데 역이 등록 될 경우 거리는 양의 정수라는 비즈니스 규칙을 지켜야 합니다.
    - [ ] A-B-C 노선에서 B 다음에 D 역을 등록하려고 하는데
    - [ ] B-C역의 거리가 3km인 경우 B-D 거리는 3km보다 적어야 합니다.
    - [ ] B-C가 3km인데 B-D거리가 3km면 D-C거리는 0km가 되어야 하는데 거리는 양의 정수여야 하기 때문에 이 경우 등록이 불가능 해야합니다.

- [ ] 노선에 역 제거
  - [ ] 노선에서 역을 제거할 경우 정상 동작을 위해 재배치 되어야 합니다.
    - [ ] A-B-C-D 역이 있는 노선에서 C역이 제거되는 경우 A-B-D 순으로 재배치됩니다.
  - [ ] 노선에서 역이 제거될 경우 역과 역 사이의 거리도 재배정되어야 합니다.
    - [ ] A-B가 2km, B-C가 3km, C-D가 4km인 경우 C역이 제거되면 B-D 거리가 7km가 되어야 합니다.
  - [ ] 노선에 등록된 역이 2개 인 경우 하나의 역을 제거할 때 두 역이 모두 제거되어야 합니다.
    - [ ] A-B 노선에서 B를 제거할 때 거리 정보를 포함할 수 없기 때문에 두 역 모두 제거되어야 합니다.
