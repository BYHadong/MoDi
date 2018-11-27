package byh.adong.modi.service


import byh.adong.modi.data.*
import retrofit2.Call
import retrofit2.http.*

interface APIService<T> {

    //로그인 및 회원가입
    @POST("users")
    fun signup(@Body user : User) : Call<Status>

    @POST("sign")
    fun login(@Body user: User) : Call<UserGet>

    //일기 작성및 불러오기
    @POST("write")
    fun diarywrite(@Body diaries: Diaries) : Call<DiariesGet>

    @GET("write")
    fun getdiary() : Call<DiariesGet>

    @DELETE("write/{diary_id}")
    fun deleteDiary(@Path("diary_id") diary_id: Int) : Call<DiariesGet>



}