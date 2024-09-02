package backend.diary.domain.follow.dto.response;

import backend.diary.domain.follow.dto.FollowUserInfoDTO;
import backend.diary.global.common.SliceInfo;
import org.springframework.data.domain.Slice;

import java.util.List;

public record GetFollowerListResponse(
        List<FollowUserInfoDTO> followerList,
        SliceInfo sliceInfo
) {
    public static GetFollowerListResponse of(Slice<FollowUserInfoDTO> followerList) {
        List<FollowUserInfoDTO> followerInfo = followerList.getContent();
        return new GetFollowerListResponse(followerInfo, SliceInfo.of(followerList));
    }
}
