## ※ 이 모드를 사용하려면 [Fabric API](https://modrinth.com/mod/fabric-api) 모드가 필요합니다.

이 모드는 단순한 명령어 추가, 그리고 #로 시작하는 스코어보드 변수를 숨김 처리가 안되게 하는 모드입니다.

대부분 성능을 챙기기 위해 명령어가 많습니다.

## 명령어
### /pos [대상] [좌표]
개체를 이동합니다. [대상] 생략시 @s와 같으며, [좌표] 생략시 ~ ~ ~와 같습니다.

### /rot [대상] [시점]
개체의 시점을 회전합니다. [대상] 생략시 @s와 같으며, [시점] 생략시 ~ ~와 같습니다. 이걸 쓸바엔 /rotate 쓰면 됩니다만...

###	/chance <roll|value> <Chance>
확률 테스트. roll이면 모두에게 보여주고, value이면 자신만 보여집니다. 1 = 100%, 0.2 = 20%

###	/get_pos <x|y|z> [scale] [대상]
대상(기본 @s)의 위치를 출력합니다.

###	/get_rotate <x|y> [scale] [대상]
대상(기본 @s)의 시점각을 출력합니다.

###	/get_tick <mspt|tps|target_tps> [scale]
현재 서버 틱을 출력합니다.

