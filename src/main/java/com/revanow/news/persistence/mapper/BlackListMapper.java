package com.revanow.news.persistence.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.revanow.news.domain.dte.BlackListdte;

public interface BlackListMapper {

	@Select("<script>select * from t_blacklist where blackcode=#{blackcode} </script>")
	BlackListdte checkBlackList(@Param("blackcode") String blackcode);
}
