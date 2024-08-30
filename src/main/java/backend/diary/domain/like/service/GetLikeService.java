package backend.diary.domain.like.service;

import backend.diary.domain.article.entity.Article;
import backend.diary.domain.like.dto.response.GetLikeArticleListResponse;
import backend.diary.domain.like.entity.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GetLikeService {

    private final LikeRepository likeRepository;
    public GetLikeArticleListResponse getLikeArticles(Pageable pageable, Long userId){
        Slice<Article> likeArticles = likeRepository.findLikeArticle(pageable, userId);
        return GetLikeArticleListResponse.of(likeArticles);
    }
}
