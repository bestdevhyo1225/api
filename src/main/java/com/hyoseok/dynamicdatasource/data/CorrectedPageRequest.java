package com.hyoseok.dynamicdatasource.data;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class CorrectedPageRequest extends PageRequest {
    public CorrectedPageRequest(Pageable pageable, long totalCount) {
        super(getPageNumber(pageable, totalCount), pageable.getPageSize(), pageable.getSort());
    }

    private static int getPageNumber(Pageable pageable, long totalCount) {
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        long requestCount = (long)pageNumber * pageSize;

        // DB에 있는 전체 데이터 수가 요청한 데이터의 수보다 큰 경우, 그냥 반환한다.
        if (totalCount > requestCount) return pageNumber;

        /*
        * pageNumber : 0, 데이터 : 1 ~ 10번
        * pageNumber : 1, 데이터 : 11 ~ 20번
        * pageNumber : 2, 데이터 : 21 ~ 30번
        * pageNumber : 3, 데이터 : 31 ~ 40번
        * pageNumber : 4, 데이터 : 41 ~ 50번
        *
        * ex1) totalCount = 47, pageNumber = 7
        *
        * totalCount(47) % pageSize(10) = 7
        *
        * 40번 데이터까지는 모두 존재하고, 추가로 7개 데이터가 남아 있다. 그래서 pageNumber : 4를 반환해야 한다.
        *
        * ex2) totalCount = 40, pageNumber = 7
        *
        * totalCount(40) % pageSize(10) = 0
        *
        * 40번 데이터까지는 모두 존재하고, 추가로 남은 데이터는 없기 때문에 pageNumber - 1 값인 3을 반환해야 한다.
        * */
        int remainingBookCount = (int)((double) totalCount % pageSize);
        int lastPageNumber = (int)((double) totalCount / pageSize);

        if (remainingBookCount > 0) return lastPageNumber;

        return lastPageNumber - 1;
    }
}
