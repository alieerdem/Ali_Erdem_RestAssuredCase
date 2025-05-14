package models.request.pet;

import lombok.*;

import java.util.List;

@Data
@Builder
public class PetDto {

    private long id;
    private CategoryDto category;
    private String name;
    private List<String> photoUrls;
    private List<TagDto> tags;
    private String status;
}

