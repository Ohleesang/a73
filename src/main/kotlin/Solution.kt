//N -1j S +1j W -1i E +1i
/**
 * 3~50 park  맵의 크기는 최대 50
 * 1~50 routes 최대 50가지 루트
 */


class Solution {
    fun checkRoutes(route: String): IntArray {
        var answer = intArrayOf()
        var list = route.split(" ")
        when (list[0]) {
            "N" -> answer = intArrayOf(-1 * list[1].toInt(), 0)
            "S" -> answer = intArrayOf(list[1].toInt(), 0)
            "W" -> answer = intArrayOf(0, -1 * list[1].toInt())
            "E" -> answer = intArrayOf(0, list[1].toInt())
        }
        return answer
    }

    fun solution(park: Array<String>, routes: Array<String>): IntArray {
        var answer: IntArray = intArrayOf()
        var dog: IntArray = intArrayOf()

        fun isMove(acc: IntArray): Boolean {
            //part부분에 방해되는부분이없는지 확인하는 함수
            var acc_i = acc[0]
            var acc_j = acc[1]
            if (acc_i > 0) {//[n,0]
                for (i in dog[0]..acc_i) {
                    if (park[i][dog[1]] == 'X') return false
                }
            } else if (acc_i < 0) {//[-n,0]
                for (i in dog[0] until acc_i) {
                    if (park[-i][dog[1]] == 'X') return false
                }
            } else if (acc_j > 0) {//[0,n]
                for (j in dog[1]..acc_j) {
                    if (park[dog[0]][j] == 'X') return false
                }
            } else if (acc_j < 0) {//[0,-n]
                for (j in dog[1] until acc_j) {
                    if (park[dog[0]][-j] == 'X') return false
                }
            }

            //벽을 넘는지 도 확인
            var res = intArrayOf(dog[0],dog[1])
            res[0] += acc_i
            res[1] += acc_j
            if(res[0]<0 || res[0]>=park.size) return false
            if(res[1]<0 || res[1]>=park[0].length) return false
            dog = res
            return true
        }
        //dog 위치 찾기
        for (i in park.indices) {
            for (j in park[0].indices) {
                if (park[i][j] == 'S') {
                    dog = intArrayOf(i, j)
                    break
                }
            }
        }

        //routes 수 만큼 check 해서, 각 조건에 맞추면 시행(dog을 움직인다)
        var moveAcc = intArrayOf()
        for (k in routes.indices) {
            moveAcc = checkRoutes(routes[k])
            isMove(moveAcc)
        }
        answer = dog

        return answer
    }
}

fun main() {
    var a = Solution()
    a.solution(arrayOf("SOO","OOO","OOO"), arrayOf("E 2","S 2","W 1"))//[2,1]
    a.solution(arrayOf("SOO", "OXX", "OOO"), arrayOf("E 2", "S 2", "W 1"))//[0,1]
    a.solution(arrayOf("OSO", "OOO", "OXO", "OOO"), arrayOf("E 2", "S 3", "W 1"))//[0,0]
}