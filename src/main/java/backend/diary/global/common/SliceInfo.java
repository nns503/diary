package backend.diary.global.common;

import org.springframework.data.domain.Slice;

public record SliceInfo(
        int page,
        int pageSize,
        int elements,
        boolean hasNext
) {
    public static SliceInfo of(Slice<?> slice) {
        return new SliceInfo(slice.getNumber() + 1, slice.getSize(), slice.getNumberOfElements(), slice.hasNext());
    }
}
