package com.revanow.news.persistence.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.revanow.news.domain.dte.Newsdogsdte;

public interface NewsMapper {

	@Select("<script>select * from t_newsdog where news_id=#{news_id} and t_language = #{language} limit 0,1</script>")
	Newsdogsdte getNewsById(@Param("news_id") String news_id,@Param("language") String language);
	
	@Insert("<script>"
			+ "INSERT INTO t_newsdog "
			+ "(news_id,title,content,top_image,top_image_width,top_image_height,related_images,related_image_width,related_image_height,source,type,published_at,source_url,t_language,clicktime,t_location) "
			+ "VALUES "
			+ "(#{news_id},#{title},#{content},#{top_image},#{top_image_width},#{top_image_height},#{related_images},#{related_image_width},#{related_image_height},#{source},#{type},#{published_at},#{source_url},#{t_language},now(),#{t_location});"
			+ "</script>")
	int insertnews(Newsdogsdte news);
	
	@Update("<script>UPDATE t_newsdog set "
			+ "news_id = #{news_id},title = #{title},content = #{content},top_image = #{top_image},top_image_width = #{top_image_width},"
			+ "top_image_height = #{top_image_height},related_images = #{related_images},related_image_width = #{related_image_width},"
			+ "related_image_height = #{related_image_height},source = #{source},type = #{type},published_at = #{published_at},source_url = #{source_url},"
			+ "t_language = #{t_language},clicktime = now(),t_location = #{t_location}"
			+ "WHERE id = #{id};</script>")
	int updatenews(Newsdogsdte news);
}
