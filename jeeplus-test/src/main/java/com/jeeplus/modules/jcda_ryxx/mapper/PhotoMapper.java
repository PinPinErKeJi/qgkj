package com.jeeplus.modules.jcda_ryxx.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.jcda_ryxx.entity.Photo;

@Mapper
@Repository
public interface PhotoMapper extends BaseMapper<Photo>{

	List<Photo> findByPerson(@Param("personId")String personId);
	
	@Select("select path from jcda_registerphoto where face_id = #{faceId} limit 0,1")
	String isExist(@Param("faceId")String faceId);
	
	@Delete("delete from jcda_registerphoto where face_id = #{faceId}")
	void deleteByFaceId(@Param("faceId")String faceId);
}
