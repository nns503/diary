package backend.diary.domain.follow.service;

import backend.diary.domain.follow.dto.FollowingInfoDTO;
import backend.diary.domain.follow.dto.response.GetFollowingListResponse;
import backend.diary.domain.follow.entity.repository.FollowRepository;
import backend.diary.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GetFollowService {

    private final FollowRepository followRepository;

    public GetFollowingListResponse getFollowingUser(User user, Long findUserId, Pageable pageable){
        Slice<FollowingInfoDTO> followingList = followRepository.findByFollowingList(user.getId(), findUserId, pageable);

        return GetFollowingListResponse.of(followingList);
    }
}

