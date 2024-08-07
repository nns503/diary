package backend.diary.domain.article.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record GetArticleListRequest(
        @NotNull(message = "페이지 번호을 작성해주세요")
        @Min(value = 1, message = "페이지 번호는 1 이상이어야 합니다.")
        int page,
        @NotNull(message = "페이지 사이즈를 작성해주세요")
        @Min(value = 1, message = "페이지 사이즈는 1 이상이어야 합니다.")
        int size
)
{ }
