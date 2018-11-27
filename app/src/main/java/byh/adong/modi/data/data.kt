package byh.adong.modi.data

//서버 상태 및 메시지
data class Result(val success: Boolean, val message: String)

//회원가입 및 로그인
data class Status(val status: Result, val user: User)

data class User(val username: String, val password: String)
data class UserGet(val status: Result, val user: User, val token: Token)

//토큰 인증으로 자동 로그인 기능 구연
data class Token(val data: String = "")

//다이얼리 포스트
//tag는 stringbuffe로 합쳐서 string으로 형변환 해서 보내기
data class Diaries(
    val weather: String,
    val feel: String,
    val contents: String,
    val createdAt: String = "",
    val diary_id: Int = 0
)

data class DiariesGet(val status: Result, val diaries: ArrayList<Diaries>)

//mainactivity Recyclerview data class
class Diarielist(val days: String, val feel: String, val weather: String, val contents: String, val diary_id: Int)