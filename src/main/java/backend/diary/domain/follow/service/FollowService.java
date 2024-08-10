package backend.diary.domain.follow.service;

import backend.diary.domain.follow.entity.Follow;
import backend.diary.domain.follow.entity.repository.FollowRepository;
import backend.diary.domain.follow.exception.AlreadyFollowException;
import backend.diary.domain.follow.exception.NotFoundFollowException;
import backend.diary.domain.follow.exception.SelfFollowException;
import backend.diary.domain.user.entity.User;
import backend.diary.domain.user.entity.repository.UserRepository;
import backend.diary.domain.user.exception.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addFollow(User user, Long followingId){
        Long userId = user.getId();
        validateSelfFollow(userId, followingId);
        User followingUser = userRepository.findById(followingId)
                .orElseThrow(NotFoundUserException::new);

        if(followRepository.existsByFollowerIdAndFollowingId(userId, followingId)){
            throw new AlreadyFollowException();
        }

        Follow follow = Follow.builder()
                .follower(user)
                .following(followingUser)
                .build();

        followRepository.save(follow);
    }

    @Transactional
    public void deleteFollow(User user, Long followingId){
        Long userId = user.getId();

        validateSelfFollow(userId, followingId);
        if(!userRepository.existsById(followingId)){
            throw new NotFoundUserException();
        }

        Follow follow = followRepository.findByFollowerIdAndFollowingId(userId, followingId)
                .orElseThrow(NotFoundFollowException::new);

        followRepository.delete(follow);
    }

    private void validateSelfFollow(Long user, Long followingId) {
        if (user.equals(followingId)) {
            throw new SelfFollowException();
        }
    }
}
