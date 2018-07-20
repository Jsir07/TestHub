package com.jxq.douban;

import com.jxq.common.HttpBase;
import com.jxq.douban.ISearch;
import com.jxq.douban.domain.MovieResponseVO;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

/**
 * @Auther: jx
 * @Date: 2018/7/13 17:47
 * @Description:
 */
public class HttpSearch extends HttpBase {

    private ISearch iSearch;

    public HttpSearch(String host) {
        super(host);
        iSearch = super.create(ISearch.class);
    }

    public Response<MovieResponseVO> searchTags(String type, String source) throws IOException {
        Call<MovieResponseVO> call = iSearch.searchTags(type, source);
        return call.execute();
    }

}
