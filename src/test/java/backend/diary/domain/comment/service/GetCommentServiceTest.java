package backend.diary.domain.comment.service;

import backend.diary.domain.article.entity.Article;
import backend.diary.domain.article.entity.repository.ArticleRepository;
import backend.diary.domain.article.fixture.ArticleFixture;
import backend.diary.domain.comment.dto.response.GetCommentListResponse;
import backend.diary.domain.comment.entity.Comment;
import backend.diary.domain.comment.entity.repository.CommentRepository;
import backend.diary.domain.user.entity.User;
import backend.diary.fixture.CommonUserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
class GetCommentServiceTest {

    @InjectMocks
    private GetCommentService getCommentService;

    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private CommentRepository commentRepository;

    private final CommonUserFixture commonUserFixture = new CommonUserFixture();
    private final ArticleFixture articleFixture = new ArticleFixture();
    private Article article1;

    @BeforeEach
    void setUp(){
        User 일반회원1 = commonUserFixture.일반회원1;
        article1 = articleFixture.기본게시물1(일반회원1);
    }

    @ParameterizedTest()
    @MethodSource("providePageableParameters")
    void 댓글_목록_조회_성공(Pageable pageable) {
        List<Comment> comments = new ArrayList<>();
        for(long i=1; i<=33; i++){
            Comment comment = Comment.builder()
                    .id(i)
                    .build();
            comments.add(comment);
        }
        int totalElements = comments.size();
        int start = Math.min((int) pageable.getOffset(), totalElements);
        int end = Math.min(start + pageable.getPageSize(), totalElements);

        List<Comment> subList = comments.subList(start, end);
        PageImpl<Comment> pageComments = new PageImpl<>(subList, pageable, totalElements);
        given(articleRepository.findByIdAndIsDeletedFalse(any(Long.class)))
                .willReturn(Optional.ofNullable(article1));
        given(commentRepository.findAllByArticleAndIsDeletedFalse(any(Article.class), any(Pageable.class)))
                .willReturn(pageComments);

        GetCommentListResponse response = getCommentService.getCommentList(article1.getId(), pageable);
        int expectedSize = (totalElements > start) ? Math.min(pageable.getPageSize(), totalElements - start) : 0;

        assertThat(response.page()).isEqualTo(pageable.getPageNumber() + 1);
        assertThat(response.pageSize()).isEqualTo(pageable.getPageSize());
        assertThat(response.elements()).isEqualTo(expectedSize);
        assertThat(response.totalElements()).isEqualTo(comments.size());
        assertThat(response.totalPages()).isEqualTo((comments.size() + pageable.getPageSize() - 1) / pageable.getPageSize());

    }

    static Stream<Arguments> providePageableParameters() {
        return Stream.of(
                Arguments.of(PageRequest.of(0, 5)),
                Arguments.of(PageRequest.of(0, 10)),
                Arguments.of(PageRequest.of(0, 33)),
                Arguments.of(PageRequest.of(0, 32)),
                Arguments.of(PageRequest.of(0, 34)),
                Arguments.of(PageRequest.of(1, 5)),
                Arguments.of(PageRequest.of(3, 10)),
                Arguments.of(PageRequest.of(2, 100)) // 빈 페이지
        );
    }
}