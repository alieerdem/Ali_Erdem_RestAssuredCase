package helper;

import models.request.pet.CategoryDto;
import models.request.pet.PetDto;
import models.request.pet.TagDto;

import java.util.List;

public class PetDtoBuilder {

    public static PetDto createPetBody(long id, String name) {
        return PetDto.builder()
                .id(id)
                .name(name)
                .status("available")
                .category(CategoryDto.builder()
                        .id(0)
                        .name("cat")
                        .build())
                .photoUrls(List.of("url"))
                .tags(List.of(TagDto.builder()
                        .id(0)
                        .name("ali")
                        .build()))
                .build();
    }

    public static PetDto createPetBodyWithStatusSold(long id, String name) {
        return PetDto.builder()
                .id(id)
                .name(name)
                .status("sold")
                .category(CategoryDto.builder()
                        .id(0)
                        .name("cat")
                        .build())
                .photoUrls(List.of("url"))
                .tags(List.of(TagDto.builder()
                        .id(0)
                        .name("ali")
                        .build()))
                .build();
    }
}
