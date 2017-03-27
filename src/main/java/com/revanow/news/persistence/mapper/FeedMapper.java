package com.revanow.news.persistence.mapper;

import org.apache.ibatis.annotations.Insert;

import com.revanow.news.domain.dte.Feedbackdte;

public interface FeedMapper {

	@Insert("<script>INSERT INTO t_feedback (id, type, email, question, subtime) VALUES (null, #{type}, #{email}, #{question}, now());</script>")
	int insertFeedBack(Feedbackdte feedback);
}
