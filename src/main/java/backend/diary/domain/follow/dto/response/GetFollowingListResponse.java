package backend.diary.domain.follow.dto.response;

import backend.diary.domain.follow.dto.FollowUserInfoDTO;
import backend.diary.global.common.SliceInfo;
import org.springframework.data.domain.Slice;

import java.util.List;

public record GetFollowingListResponse(
        List<FollowUserInfoDTO> followingList,
        SliceInfo sliceInfo
) {
    public static GetFollowingListResponse of(Slice<FollowUserInfoDTO> followingList) {
        List<FollowUserInfoDTO> followingInfo = followingList.getContent();
        return new GetFollowingListResponse(followingInfo, SliceInfo.of(followingList));
    }
}
