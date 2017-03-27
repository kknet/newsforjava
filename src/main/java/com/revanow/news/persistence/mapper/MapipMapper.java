package com.revanow.news.persistence.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.revanow.news.domain.dte.Mapipdte;


public interface MapipMapper {

	
	@Select("<script>select * from map_ip where p_ip_from &lt;= #{ip} and p_ip_to &gt;= #{ip}</script>")
	Mapipdte getLocationForIp(@Param("ip") long ip);
}
