package com.winter.px.bean;

import java.util.List;

/**
 * Created by peiyangyang on 2016/9/5.
 */
public class ListNews {

    /**
     * title : 4K摄像新选择 佳能CX15将于本月上市发售
     * pic_url : http://img2.fengniao.com/product/157_160x120/690/cewHLhxKGclA.jpg
     * date : 2016-09-05 06:00:00
     * author : 房时宇
     * doc_url : http://api.fengniao.com/app_ipad/news_doc.php?docid=5338563&isPad=1
     * doc_id : 5338563
     * web_url : http://qicai.fengniao.com/533/5338563.html
     * comment_page_num : 0
     * comments_num : 0
     * more_comment_url : http://api.fengniao.com/app_ipad/news_doc_comments.php?docid=5338563&isPad=1
     */

    private List<ListdataBean> listdata;

    public List<ListdataBean> getListdata() {
        return listdata;
    }

    public void setListdata(List<ListdataBean> listdata) {
        this.listdata = listdata;
    }

    public static class ListdataBean {
        private String title;
        private String pic_url;
        private String date;
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

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getWeb_url() {
            return web_url;
        }

        public void setWeb_url(String web_url) {
            this.web_url = web_url;
        }
    }
}
