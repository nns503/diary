package backend.diary.domain.follow.dto.response;

import backend.diary.domain.follow.dto.FollowingInfoDTO;
import backend.diary.global.common.SliceInfo;
import org.springframework.data.domain.Slice;

import java.util.List;

public record GetFollowingListResponse(
        List<FollowingInfoDTO> followingList,
        SliceInfo sliceInfo
) {
    public static GetFollowingListResponse of(Slice<FollowingInfoDTO> followingList) {
        List<FollowingInfoDTO> followingInfo = followingList.getContent();
        return new GetFollowingListResponse(followingInfo, SliceInfo.of(followingList));
    }
}
