package chuansong.me.chuansongmen.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by HongDanyang on 16/1/22.
 */
@Setter
@Getter
public class News {

    private String nextPage;

    private List<NewslistEntity> newslist;

    @Getter
    @Setter
    public static class NewslistEntity {

        private String title;

        private String author;

        private String cover;

        private String authorID;

        private String date;

        private String id;
    }
}
