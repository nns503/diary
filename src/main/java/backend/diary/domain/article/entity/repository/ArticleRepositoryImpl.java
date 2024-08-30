package backend.diary.domain.article.entity.repository;

import backend.diary.domain.article.dto.response.GetArticleDTO;
import backend.diary.domain.article.dto.response.QGetArticleDTO;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

import static backend.diary.domain.article.entity.QArticle.article;
import static backend.diary.domain.user.entity.QUser.user;

@RequiredArgsConstructor
@Transactional
@Repository
public class ArticleRepositoryImpl implements GetArticleRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<GetArticleDTO> findByArticleList(Pageable pageable, String keywordType, String keyword, String sort) {
        List<GetArticleDTO> query = queryFactory
                .select(new QGetArticleDTO(
                        article.id,
                        article.title,
                        user.nickname,
                        article.createdAt,
                        article.viewCount,
                        article.likeCount
                ))
                .from(article)
                .innerJoin(article.user, user)
                .where(
                        eqTitle(keywordType, keyword)
                )
                .orderBy(createOrderSpecifier(sort), article.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> total = queryFactory
                .select(article.count())
                .from(article)
                .innerJoin(article.user, user)
                .where(
                        eqTitle(keywordType, keyword)
                );

        return PageableExecutionUtils.getPage(query, pageable, total::fetchOne);
    }

    private BooleanExpression eqTitle(String keywordType, String keyword){
        if(StringUtils.hasText(keywordType)) {
            if (keywordType.equals("TITLE") && StringUtils.hasText(keyword)) {
                return article.title.like("%" + keyword + "%");
            }
            else if (keywordType.equals("NICKNAME") && StringUtils.hasText(keyword)) {
                return article.user.nickname.like("%" + keyword + "%");
            }

        }
        return null;
    }

    private OrderSpecifier<?> createOrderSpecifier(String sort) {
        if (sort == null) {
            return new OrderSpecifier<>(Order.DESC, article.id);
        }

        return switch (sort) {
            case "LIKE" -> new OrderSpecifier<>(Order.DESC, article.likeCount);
            case "VIEW" -> new OrderSpecifier<>(Order.DESC, article.viewCount);
            case "NEW" -> new OrderSpecifier<>(Order.DESC, article.id);
            default -> new OrderSpecifier<>(Order.DESC, article.id);  // == NEW
        };
    }
}

