package com.revanow.news.persistence.mapper;

import org.apache.ibatis.annotations.Insert;

import com.revanow.news.domain.dte.Usersubdte;

public interface SubscribeMapper {

	@Insert("<script>INSERT INTO t_user_subscribe (id, site_url, did) VALUES (NULL, #{site_url}, #{did});</script>")
	int recordUserSubscribe(Usersubdte bean);
}
