package com.revanow.news.persistence.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.revanow.news.domain.dte.Newslikedte;

public interface LikeMapper {

	@Select("<script>select * from t_news_like where news_id=#{news_id}</script>")
	public Newslikedte getNewsLikeRecord(@Param("news_id") String news_id);

	@Insert("<script>INSERT INTO t_news_like (id, news_id, like_count, unlike_count) VALUES (null, #{news_id}, 1, 0);</script>")
	public int InsertUserFavour(@Param("news_id") String news_id);

	@Insert("<script>INSERT INTO t_news_like (id, news_id, like_count, unlike_count) VALUES (null, #{news_id}, 0, 1);</script>")
	public int InsertUserUnFavour(@Param("news_id") String news_id);

	@Update("<script>UPDATE t_news_like SET like_count = like_count + 1 WHERE news_id=#{news_id};</script>")
	public int AddUserFavour(@Param("news_id") String news_id);

	@Update("<script>UPDATE t_news_like SET unlike_count = unlike_count + 1 WHERE news_id=#{news_id};</script>")
	public int AddUserUnFavour(@Param("news_id") String news_id);

	@Update("<script>UPDATE t_news_like SET like_count = like_count - 1 WHERE news_id=#{news_id};</script>")
	public int MinUserFavour(@Param("news_id") String news_id);

	@Update("<script>UPDATE t_news_like SET unlike_count = unlike_count - 1 WHERE news_id=#{news_id};</script>")
	public int MinUserUnFavour(@Param("news_id") String news_id);
}
