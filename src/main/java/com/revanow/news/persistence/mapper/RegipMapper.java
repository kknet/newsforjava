package com.revanow.news.persistence.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.revanow.news.domain.dte.Regipdte;

public interface RegipMapper {

	@Select("<script>select * from ipv4_reg where ip_from &lt;= #{ip} and ip_to &gt;= #{ip}</script>")
	Regipdte getRegForIp(@Param("ip") long ip);
}
