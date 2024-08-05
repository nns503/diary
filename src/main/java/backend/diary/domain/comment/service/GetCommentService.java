package backend.diary.domain.comment.service;

import backend.diary.domain.article.entity.Article;
import backend.diary.domain.article.entity.repository.ArticleRepository;
import backend.diary.domain.article.exception.NotFoundArticleException;
import backend.diary.domain.comment.dto.response.GetCommentListResponse;
import backend.diary.domain.comment.dto.response.GetCommentResponse;
import backend.diary.domain.comment.entity.Comment;
import backend.diary.domain.comment.entity.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GetCommentService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    public GetCommentListResponse getCommentList(Long articleId, Pageable pageable) {
        Article findArticle = articleRepository.findByIdAndIsDeletedFalse(articleId)
                .orElseThrow(NotFoundArticleException::new);

        Page<Comment> comments = commentRepository.findAllByArticleAndIsDeletedFalse(findArticle, pageable);
        List<GetCommentResponse> getCommentList = comments.getContent().stream()
                .map(GetCommentResponse::convertTo)
                .toList();

        int page = comments.getNumber() + 1;
        int pageSize = comments.getSize();
        int elements = comments.getNumberOfElements();
        long totalElements = comments.getTotalElements();
        int totalPages = comments.getTotalPages();

        return new GetCommentListResponse(getCommentList, page, pageSize, elements, totalElements, totalPages);
    }
}
