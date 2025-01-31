## 설명

이 모드는 단순한 명령어 추가, 그리고 #로 시작하는 스코어보드 변수를 숨김 처리가 안되게 하는 모드입니다.

대부분 성능을 챙기기 위해 명령어가 많습니다.

## 명령어
### /pos [target] [location]
개체를 이동합니다. 

Default: [target] = "@s", [location] = "~ ~ ~"

### /rot [target] [rotation]
개체의 시점을 회전합니다. 최적화 잘되서 그냥 /rotate 쓰면 됩니다만...

Default: [target] = "@s", [rotation] = "~ ~"

###	/chance <Chance> [true|false]
확률 테스트. true이면 모두에게 보여주고, false이면 자신만 보여집니다.(기본 false) 1 = 100%, 0.2 = 20%

###	/get_pos <x|y|z> [scale] [target]
대상의 위치를 출력합니다. 

Default: [scale] = 1, [target] = "@s"

###	/get_rotate <x|y> [scale] [target]
대상의 시점각을 출력합니다.

Default: [scale] = 1, [target] = "@s"

###	/get_tick <mspt|nspt|tps|target_tps> [scale]
현재 서버 틱을 출력합니다.

Default: [scale] = 1

###	/truekill
실행자의 개체를 즉시 제거합니다.

## Note(참고)
1.0.3 이하 버전은 [Fabric API](https://modrinth.com/mod/fabric-api) 모드가 필요합니다.
