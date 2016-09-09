package com.winter.px.bean;

import java.util.List;

/**
 * Created by peiyangyang on 2016/9/5.
 */
public class GridNews {

    /**
     * title : 专业电影变焦头 蔡司LWZ.3 预计明年发售
     * pic_url : http://img2.fengniao.com/product/157_280x280/636/ce1Rlt9GpUJXw.jpg
     * date : 2016-09-05 06:00:00
     * doc_url : http://api.fengniao.com/app_ipad/news_doc.php?docid=5338549&isPad=1
     * doc_id : 5338549
     * web_url : http://qicai.fengniao.com/533/5338549.html
     * comment_page_num : 0
     * comments_num : 0
     * more_comment_url : http://api.fengniao.com/app_ipad/news_doc_comments.php?docid=5338549&isPad=1
     */

    private List<GriddataBean> griddata;

    public List<GriddataBean> getGriddata() {
        return griddata;
    }

    public void setGriddata(List<GriddataBean> griddata) {
        this.griddata = griddata;
    }

    public static class GriddataBean {
        private String title;
        private String pic_url;
        private String web_url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public String getWeb_url() {
            return web_url;
        }

        public void setWeb_url(String web_url) {
            this.web_url = web_url;
        }
    }
}
