package com.example.news.infraestructure.mappers;

import com.example.news.domain.data.NewDetailDto;
import com.example.news.domain.data.NewShortDto;
import com.example.news.infraestructure.entity.New;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface NewMapper {
    NewMapper INSTANCE = Mappers.getMapper(NewMapper.class);

    @Mapping(target = "idAuthor", ignore = true)
    NewDetailDto newToNewDetailDto(New newItem);

    List<NewShortDto> newListToNewShortDtoList(List<New> newList);
}
