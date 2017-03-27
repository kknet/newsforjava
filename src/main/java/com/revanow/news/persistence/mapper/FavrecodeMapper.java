package com.revanow.news.persistence.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.revanow.news.domain.dte.Favrecorddte;

public interface FavrecodeMapper {
	
	@Select("<script>select * from t_user_like where news_id=#{news_id} and token=#{token}</script>")
	Favrecorddte getFavourLikeRecord(@Param("news_id") String news_id,@Param("token") String token);
	
	@Insert("<script>INSERT INTO t_user_like (`id`, `news_id`, `token`, `like`, `unlike`, `subtime`) VALUES (null, #{news_id}, #{token}, #{like}, #{unlike},now());</script>")
	int insertUserlike(Favrecorddte fav);
	
	@Update("<script>UPDATE t_user_like SET `news_id`=#{news_id}, `token`=#{token}, `like`=#{like}, `unlike`=#{unlike}, `subtime`=now() WHERE id=#{id};</script>")
	int updateUserlike(Favrecorddte fav);
}
