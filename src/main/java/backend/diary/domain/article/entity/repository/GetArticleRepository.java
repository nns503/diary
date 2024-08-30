package backend.diary.domain.article.entity.repository;

import backend.diary.domain.article.dto.response.GetArticleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetArticleRepository {
    Page<GetArticleDTO> findByArticleList(Pageable pageable, String keywordType, String keyword, String sort);
}
