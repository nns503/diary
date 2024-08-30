package backend.diary.domain.follow.entity.repository;

import backend.diary.domain.follow.dto.FollowingInfoDTO;
import backend.diary.domain.follow.entity.Follow;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByFollowerIdAndFollowingId(Long followerId, Long followingId);

    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);

    @Query(value = "select new backend.diary.domain.follow.dto.FollowingInfoDTO(f1.following.id, f1.following.nickname" +
            ", case when f2.id is not null then true else false end" +
            ", case when f1.id = :userId then true else false end)" +
            "from Follow f1 " +
            "left join Follow f2 on f1.following.id = f2.following.id and f2.follower.id =  :userId " +
            "where f1.follower.id = :findUserId")
    Slice<FollowingInfoDTO> findByFollowingList(Long userId, Long findUserId, Pageable pageable);
}
