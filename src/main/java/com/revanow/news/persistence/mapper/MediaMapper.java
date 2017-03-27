package com.revanow.news.persistence.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.revanow.news.domain.dte.Mediadte;

public interface MediaMapper {

	@Select("<script>select * from t_media_list where source_id=#{source_id} limit 0,1</script>")
	public Mediadte getMediaBySourceId(@Param("source_id") String source_id);
	
	@Insert("<script>INSERT INTO t_media_list  VALUES (NULL, #{source_id}, #{site_url}, #{title}, #{language});</script>")
	public int insertMedia(Mediadte news);

	@Update("<script>UPDATE t_media_list SET  source_id=#{source_id}, site_url=#{site_url}, title=#{title}, language=#{language} WHERE id = #{id};</script>")
	public int updateMedia(Mediadte news);
}
