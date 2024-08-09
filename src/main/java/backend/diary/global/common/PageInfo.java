package backend.diary.global.common;

import org.springframework.data.domain.Page;

public record PageInfo(
        int page,
        int pageSize,
        int elements,
        Long totalElements,
        int totalPages
){
    public static PageInfo of(Page<?>  list){
        return new PageInfo(list.getNumber() + 1, list.getSize(), list.getNumberOfElements(), list.getTotalElements(), list.getTotalPages());
    }
}
