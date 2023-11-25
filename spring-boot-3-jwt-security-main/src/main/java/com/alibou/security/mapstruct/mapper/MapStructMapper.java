package com.alibou.security.mapstruct.mapper;


import com.alibou.security.entity.ExerciseEntity;
import com.alibou.security.entity.UserEntity;
import com.alibou.security.mapstruct.dto.ExerciseDto;
import com.alibou.security.mapstruct.dto.UserDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring"
)
public interface MapStructMapper {

     MapStructMapper INSTANCE = Mappers.getMapper(MapStructMapper.class);


     UserDto userToUserDto(UserEntity user);

     ExerciseDto exerciseToExerciseDto(ExerciseEntity exercise);



     @AfterMapping
     default void afterExerciseToExerciseDtoMapping(@MappingTarget ExerciseDto exerciseDto, ExerciseEntity exercise) {
          exerciseDto.setId(exercise.getExercise_id());
          exerciseDto.setCategory(exercise.getCategory());
          exerciseDto.setLevel(exercise.getLevel());
          exerciseDto.setDescription(exercise.getDescription());
          exerciseDto.setAverageRating(exercise.getAverageRating());
     }

     @AfterMapping
     default void afterUSerToUserDtoMapping(@MappingTarget UserDto userDto, UserEntity userEntity) {
          userDto.setId(userEntity.getId());
          userDto.setFirstname(userEntity.getFirstname());
          userDto.setLastname(userEntity.getLastname());
          userDto.setEmail(userEntity.getEmail());
          userDto.setMfaEnabled(userEntity.isMfaEnabled());
          userDto.setCreateDate(userEntity.getCreateDate());
          userDto.setLastModified(userEntity.getLastModified());
          userDto.setRole(userEntity.getRole());
     }

}
